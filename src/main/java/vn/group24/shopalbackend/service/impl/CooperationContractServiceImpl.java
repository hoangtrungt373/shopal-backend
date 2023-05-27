package vn.group24.shopalbackend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.group24.shopalbackend.controller.CooperationContractDto;
import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.CooperationContractSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.domain.dto.AbstractAnn;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;
import vn.group24.shopalbackend.domain.dto.CreateOrUpdateContractRequestAnn;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;
import vn.group24.shopalbackend.domain.enums.ContractChangeRequestStatus;
import vn.group24.shopalbackend.domain.enums.ContractStatus;
import vn.group24.shopalbackend.mapper.AbstractMapper;
import vn.group24.shopalbackend.mapper.CooperationContractMapper;
import vn.group24.shopalbackend.repository.AnnouncementRepository;
import vn.group24.shopalbackend.repository.CooperationContractRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.repository.ProductPointRepository;
import vn.group24.shopalbackend.service.AnnouncementService;
import vn.group24.shopalbackend.service.CooperationContractService;
import vn.group24.shopalbackend.util.BigDecimalUtils;
import vn.group24.shopalbackend.util.Validator;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class CooperationContractServiceImpl implements CooperationContractService {

    @Autowired
    private CooperationContractRepository cooperationContractRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private ProductPointRepository productPointRepository;

    @Autowired
    private CooperationContractMapper cooperationContractMapper;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private ObjectMapper jsonObjectMapper;
    @Autowired
    private AbstractMapper abstractMapper;

    @Override
    public List<CooperationContractDto> getCooperationContractByCriteria(CooperationContractSearchCriteriaRequest criteria) {
        // TODO: change to fetch join with criteria;
        List<CooperationContract> cooperationContracts = cooperationContractRepository.findAll().stream()
                .filter(cc -> getCooperationContractPredicate(criteria).test(cc))
                .sorted(Comparator.comparing(CooperationContract::getEndDate).reversed()).toList();
        return cooperationContractMapper.mapToCooperationContractDtos(cooperationContracts);
    }

    @Override
    public String handleReceiveCreateOrUpdateContract(CooperationContractDto request) {
        if (request.getId() == null) {
            Validator validator = new Validator();
            Announcement existsAnnouncement = announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                    .enterpriseId(request.getEnterprise().getId())
                    .anInterface(AnnouncementInterface.CREATE_CONTRACT)
                    .type(AnnouncementType.RECEIVE)
                    .status(AnnouncementStatus.VALID)
                    .build()).stream().findFirst().orElseGet(() -> null);
            validator.throwIfFalse(existsAnnouncement == null, "Already exists a create contract request");

            validator.throwIfFalse(request.getStartDate() != null, "Start date must after current date");
            validator.throwIfFalse(request.getEndDate() != null && !request.getEndDate().isBefore(request.getStartDate()), "End date must after start date");
            validator.throwIfFalse(request.getCashPerPoint() != null && request.getCashPerPoint().compareTo(BigDecimal.ZERO) > 0, "Cash per point must larger than 0");
            validator.throwIfFalse(request.getCommissionRate() != null && request.getCommissionRate().compareTo(BigDecimal.ZERO) > 0, "Commission rate must larger than 0");

            CreateOrUpdateContractRequestAnn announcement = new CreateOrUpdateContractRequestAnn();
            announcement.setId(announcementService.getNextAnnouncementMessageIdSeq());
            announcement.setEnterpriseId(request.getEnterprise().getId());
            announcement.setStartDate(request.getStartDate());
            announcement.setEndDate(request.getEndDate());
            announcement.setCommissionRate(request.getCommissionRate());
            announcement.setCashPerPoint(request.getCashPerPoint());
            announcement.setContractStatus(request.getContractStatus());
            announcement.setContractChangeRequestStatus(ContractChangeRequestStatus.RECEIVED);
            announcement.setIsEdit(Boolean.FALSE);
            try {
                announcementService.createAnnouncement(AnnouncementInput.builder()
                        .anInterface(AnnouncementInterface.CREATE_CONTRACT)
                        .type(AnnouncementType.RECEIVE)
                        .status(AnnouncementStatus.VALID)
                        .message(jsonObjectMapper.writeValueAsString(announcement))
                        .build());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return "Received create request contract for enterprise " + request.getEnterprise().getEnterpriseName() + " successfully";
        } else {
            Validator validator = new Validator();
            Announcement existsAnnouncement = announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                    .enterpriseId(request.getEnterprise().getId())
                    .anInterface(AnnouncementInterface.UPDATE_CONTRACT)
                    .type(AnnouncementType.RECEIVE)
                    .status(AnnouncementStatus.VALID)
                    .build()).stream().findFirst().orElseGet(() -> null);
            validator.throwIfFalse(existsAnnouncement == null, "Already exists a change contract request");

            CooperationContract cooperationContract = cooperationContractRepository.getByEnterpriseIdAndContractStatus(request.getEnterprise().getId(), ContractStatus.ACTIVE);
            validator.throwIfFalse(request.getEndDate() != null && !request.getEndDate().isBefore(request.getStartDate()), "End date must after start date");
            validator.throwIfFalse(request.getCashPerPoint() != null && request.getCashPerPoint().compareTo(BigDecimal.ZERO) > 0, "Cash per point must larger than 0");
            validator.throwIfFalse(request.getCommissionRate() != null && request.getCommissionRate().compareTo(BigDecimal.ZERO) > 0, "Commission rate must larger than 0");

            CreateOrUpdateContractRequestAnn announcement = new CreateOrUpdateContractRequestAnn();
            announcement.setId(announcementService.getNextAnnouncementMessageIdSeq());
            announcement.setCooperationContractId(cooperationContract.getId());
            announcement.setEnterpriseId(request.getEnterprise().getId());
            announcement.setStartDate(cooperationContract.getStartDate());
            announcement.setEndDate(cooperationContract.getEndDate());
            announcement.setUpdateEndDate(request.getEndDate());
            announcement.setCommissionRate(cooperationContract.getCommissionRate());
            announcement.setUpdateCommissionRate(request.getCommissionRate());
            announcement.setCashPerPoint(cooperationContract.getCashPerPoint());
            announcement.setUpdateCashPerPoint(request.getCashPerPoint());
            announcement.setContractStatus(cooperationContract.getContractStatus());
            announcement.setUpdateContractStatus(request.getContractStatus());
            announcement.setIsEdit(Boolean.TRUE);
            announcement.setContractChangeRequestStatus(ContractChangeRequestStatus.RECEIVED);
            try {
                announcementService.createAnnouncement(AnnouncementInput.builder()
                        .anInterface(AnnouncementInterface.UPDATE_CONTRACT)
                        .type(AnnouncementType.RECEIVE)
                        .status(AnnouncementStatus.VALID)
                        .message(jsonObjectMapper.writeValueAsString(announcement))
                        .build());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return "Received change request contract for enterprise " + request.getEnterprise().getEnterpriseName() + " successfully";
        }
    }

    @Override
    public List<CreateOrUpdateContractRequestAnn> getAllCreateOrUpdateContractAnn() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.addAll(announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                .anInterface(AnnouncementInterface.CREATE_CONTRACT)
                .type(AnnouncementType.RECEIVE)
                .build()));
        announcements.addAll(announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                .anInterface(AnnouncementInterface.UPDATE_CONTRACT)
                .type(AnnouncementType.RECEIVE)
                .build()));

        Map<Announcement, CreateOrUpdateContractRequestAnn> announcementMap = new HashMap<>();
        announcements.forEach(announcement -> {
            try {
                CreateOrUpdateContractRequestAnn messageDto =
                        jsonObjectMapper.readValue(announcement.getMessage(), CreateOrUpdateContractRequestAnn.class);
                announcementMap.put(announcement, messageDto);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(
                        "Unable to deserialize the message for CreateOrUpdateContractRequestAnn");
            }
        });

        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(announcementMap.values().stream().map(AbstractAnn::getEnterpriseId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));

        announcementMap.forEach((announcement, messageDto) -> {
            messageDto.setAnnouncementId(announcement.getId());
            messageDto.setDate(announcement.getDate());
            messageDto.setContractStatusDescription(abstractMapper.getTextForCurrentLan(messageDto.getContractStatus()));
            messageDto.setUpdateContractStatusDescription(abstractMapper.getTextForCurrentLan(messageDto.getUpdateContractStatus()));
            messageDto.setEnterprise(Optional.ofNullable(enterpriseMap.get(messageDto.getEnterpriseId()))
                    .map(enterprise -> {
                        EnterpriseDto enterpriseDto = new EnterpriseDto();
                        enterpriseDto.setId(enterprise.getId());
                        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
                        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
                        return enterpriseDto;
                    }).orElseGet(() -> null));
        });

        return new ArrayList<>(announcementMap.values());
    }

    public String handleAcceptCreateOrUpdateContractRequest(CreateOrUpdateContractRequestAnn createOrUpdateContractRequestAnn) {
        if (BooleanUtils.isTrue(createOrUpdateContractRequestAnn.getIsEdit())) {
            CooperationContract oldGeContract = cooperationContractRepository.findById(createOrUpdateContractRequestAnn.getCooperationContractId()).orElseGet(() -> null);
            Validate.isTrue(oldGeContract != null, "Can not found Cooperation Contract with id = %s", createOrUpdateContractRequestAnn.getCooperationContractId());

            CooperationContract newGeContract = oldGeContract.copy();
            newGeContract.setEndDate(createOrUpdateContractRequestAnn.getUpdateEndDate());
            newGeContract.setCommissionRate(createOrUpdateContractRequestAnn.getUpdateCommissionRate());
            newGeContract.setCashPerPoint(createOrUpdateContractRequestAnn.getUpdateCashPerPoint());
            newGeContract.setContractStatus(createOrUpdateContractRequestAnn.getUpdateContractStatus());

            oldGeContract.setContractStatus(ContractStatus.INACTIVE);

            List<ProductPoint> oldGeProductPoints = productPointRepository.getByEnterpriseIdAndActiveIsTrue(createOrUpdateContractRequestAnn.getEnterpriseId());
            List<ProductPoint> newGeProductPoints = oldGeProductPoints.stream().map(pp -> {
                ProductPoint newProductPoint = pp.copy();
                newProductPoint.setPointExchange(BigDecimalUtils.divide(pp.getProduct().getInitialCash(), newGeContract.getCashPerPoint(), 0, 2));
                return newProductPoint;
            }).toList();
            oldGeProductPoints.forEach(pp -> pp.setActive(Boolean.FALSE));

            cooperationContractRepository.save(oldGeContract);
            cooperationContractRepository.save(newGeContract);

            productPointRepository.saveAll(oldGeProductPoints);
            productPointRepository.saveAll(newGeProductPoints);

            createOrUpdateContractRequestAnn.setContractChangeRequestStatus(ContractChangeRequestStatus.ACCEPT);

            Announcement announcement = announcementRepository.findById(createOrUpdateContractRequestAnn.getAnnouncementId()).orElseGet(() -> null);
            Validate.isTrue(announcement != null, "Can not found Announcement with id = %s", createOrUpdateContractRequestAnn.getAnnouncementId());

            announcement.setStatus(AnnouncementStatus.TRANSMITTED);
            try {
                announcement.setMessage(jsonObjectMapper.writeValueAsString(createOrUpdateContractRequestAnn));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            announcementRepository.save(announcement);

            return "Update contract successfully";
        } else {

            Enterprise enterprise = enterpriseRepository.findById(createOrUpdateContractRequestAnn.getEnterpriseId()).orElseGet(() -> null);
            Validate.isTrue(enterprise != null, "Can not found Enterprise with id = %s", createOrUpdateContractRequestAnn.getEnterpriseId());

            CooperationContract newGeContract = new CooperationContract();
            newGeContract.setEnterprise(enterprise);
            newGeContract.setStartDate(createOrUpdateContractRequestAnn.getStartDate());
            newGeContract.setEndDate(createOrUpdateContractRequestAnn.getEndDate());
            newGeContract.setCommissionRate(createOrUpdateContractRequestAnn.getCommissionRate());
            newGeContract.setCashPerPoint(createOrUpdateContractRequestAnn.getCashPerPoint());
            newGeContract.setContractStatus(createOrUpdateContractRequestAnn.getContractStatus());

            cooperationContractRepository.save(newGeContract);

            createOrUpdateContractRequestAnn.setContractChangeRequestStatus(ContractChangeRequestStatus.ACCEPT);

            Announcement announcement = announcementRepository.findById(createOrUpdateContractRequestAnn.getAnnouncementId()).orElseGet(() -> null);
            Validate.isTrue(announcement != null, "Can not found Announcement with id = %s", createOrUpdateContractRequestAnn.getAnnouncementId());

            announcement.setStatus(AnnouncementStatus.TRANSMITTED);
            try {
                announcement.setMessage(jsonObjectMapper.writeValueAsString(createOrUpdateContractRequestAnn));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            announcementRepository.save(announcement);

            return "Create contract successfully";
        }
    }

    private Predicate<CooperationContract> getCooperationContractPredicate(CooperationContractSearchCriteriaRequest criteria) {
        if (criteria.getStartDate() == null) {
            criteria.setStartDate(LocalDate.MIN);
        }
        if (criteria.getEndDate() == null) {
            criteria.setEndDate(LocalDate.MAX);
        }
        Predicate<CooperationContract> predicate = cc -> !cc.getStartDate().isBefore(criteria.getStartDate()) &&
                !cc.getEndDate().isAfter(criteria.getEndDate());
        if (criteria.getContractStatus() != null) {
            predicate = predicate.and(cc -> criteria.getContractStatus() == cc.getContractStatus());
        }
        if (criteria.getEnterpriseId() != null) {
            predicate = predicate.and(cc -> cc.getEnterprise().getId().equals(criteria.getEnterpriseId()));
        }
        return predicate;
    }
}
