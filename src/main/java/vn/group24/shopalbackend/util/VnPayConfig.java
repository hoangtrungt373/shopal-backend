package vn.group24.shopalbackend.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author ttg
 */
@Component
public class VnPayConfig {

    public static final String VNP_PAYURL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VNP_RETURNURL = "/api/accounting/vnpay-payment";
    public static final String VNP_TMNCODE = "AOP3IAL2";
    public static final String VNP_HASHSECRET = "TLJOJHLXAHJLRKQUYLEYTKUJTIEVMQZP";
    public static final String VNP_APIURL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    public static final String VNP_VERSION = "2.1.0";
    public static final String VNP_COMMAND = "pay";
    public static final String VNP_TXNREF = VnPayConfig.getRandomNumber(8);
    public static final String VNP_IDADDR = "127.0.0.1";

    public static final String VNP_VERSION_STR = "vnp_Version";
    public static final String VNP_COMMAND_STR = "vnp_Command";
    public static final String VNP_TMNCODE_STR = "vnp_TmnCode";
    public static final String VNP_AMOUNT_STR = "vnp_Amount";
    public static final String VNP_CURRCODE_STR = "vnp_CurrCode";
    public static final String VNP_TXNREF_STR = "vnp_TxnRef";
    public static final String VNP_ORDERINFO_STR = "vnp_OrderInfo";
    public static final String VNP_LOCALE_STR = "vnp_Locale";
    public static final String VNP_RETURNURL_STR = "vnp_ReturnUrl";
    public static final String VNP_IPADDR_STR = "vnp_IpAddr";
    public static final String VNP_CREATEDATE_STR = "vnp_CreateDate";
    public static final String VNP_EXPIREDATE_STR = "vnp_ExpireDate";
    public static final String VNP_SECUREHASH_STR = "vnp_SecureHash";
    public static final String VNP_SECUREHASHTYPE_STR = "vnp_SecureHashType";
    public static final String VNP_TRANSACTIONSTATUS_STR = "vnp_TransactionStatus";
    public static final String VNP_TRANSACTIONNO_STR = "vnp_TransactionNo";
    public static final String VNP_PAYDATE_STR = "vnp_payDate";

    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    //Util for VNPAY
    public static String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(VNP_HASHSECRET, sb.toString());
    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getLocalAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
