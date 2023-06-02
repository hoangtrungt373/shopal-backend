package vn.group24.shopalbackend.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class VnPayPaymentResult {
    private String vnpVersion;
    private String vnpCommand;
    private String vnpTmnCode;
    private String vnpAmount;
    private String vnpCurrCode;
    private String vnpTxnRef;
    private String vnpOrderInfo;
    private String vnpLocale;
    private String vnpReturnUrl;
    private String vnpIpAddr;
    private String vnpCreateDate;
    private String vnpExpireDate;
    private String vnpSecureHash;
    private String vnpSecureHashType;
    private String vnpTransactionStatus;
    private String vnpPayDate;
    private String vnpTransactionNo;
}
