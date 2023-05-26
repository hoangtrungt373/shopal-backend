package vn.group24.shopalbackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.enums.ContractStatus;

/**
 *
 * @author ttg
 */
@Repository
public interface CooperationContractRepository extends JpaRepository<CooperationContract, Integer> {
    List<CooperationContract> getByStartDateAfterAndEndDateBefore(LocalDate startDate, LocalDate endDate);

    List<CooperationContract> getByEnterpriseId(Integer enterpriseId);

    CooperationContract getByEnterpriseIdAndContractStatus(Integer enterpriseId, ContractStatus contractStatus);
}
