package vn.group24.shopalbackend.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import vn.group24.shopalbackend.controller.request.AccountingCalculateRequest;
import vn.group24.shopalbackend.controller.request.AccountingSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.AccountingDto;
import vn.group24.shopalbackend.domain.dto.VnPayPaymentResult;

/**
 * @author ttg
 */
public interface AccountingService {

    List<AccountingDto> getAccountingByCriteria(AccountingSearchCriteriaRequest criteria);

    String calculateAccounting(AccountingCalculateRequest request);

    String getVnPayPaymentUrl(AccountingDto accountingDto, HttpServletRequest request) throws JsonProcessingException;

    VnPayPaymentResult saveVnPayPaymentResult(HttpServletRequest request) throws JsonProcessingException;
}
