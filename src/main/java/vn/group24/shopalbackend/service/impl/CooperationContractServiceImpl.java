package vn.group24.shopalbackend.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.EnterpriseCooperationContractSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.mapper.CooperationContractMapper;
import vn.group24.shopalbackend.repository.CooperationContractRepository;
import vn.group24.shopalbackend.service.CooperationContractService;

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
    private CooperationContractMapper cooperationContractMapper;

    @Override
    public List<EnterpriseCooperationContractDto> getCooperationContractForEnterpriseByCriteria(Enterprise enterprise, EnterpriseCooperationContractSearchCriteriaRequest criteria) {
        List<CooperationContract> cooperationContracts = cooperationContractRepository.getByEnterpriseId(enterprise.getId()).stream()
                .filter(cc -> getCooperationContractPredicate(criteria).test(cc))
                .sorted(Comparator.comparing(CooperationContract::getEndDate).reversed()).toList();
        return cooperationContractMapper.mapToEnterpriseCooperationContractDto(cooperationContracts);
    }

    private Predicate<CooperationContract> getCooperationContractPredicate(EnterpriseCooperationContractSearchCriteriaRequest criteria) {
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
        return predicate;
    }
}
