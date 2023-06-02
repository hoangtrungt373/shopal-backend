package vn.group24.shopalbackend.service.impl;

import static vn.group24.shopalbackend.util.VnPayConfig.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import vn.group24.shopalbackend.service.VnPayService;
import vn.group24.shopalbackend.util.VnPayConfig;

/**
 *
 * @author ttg
 */
@Service
public class VnPayServiceImpl implements VnPayService {

    @Override
    public String createOrder(int total, String orderInfo, String urlReturn) {

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put(VNP_VERSION_STR, VNP_VERSION);
        vnpParams.put(VNP_COMMAND_STR, VNP_COMMAND);
        vnpParams.put(VNP_TMNCODE_STR, VNP_TMNCODE);
        vnpParams.put(VNP_AMOUNT_STR, String.valueOf(total * 100));
        vnpParams.put(VNP_CURRCODE_STR, "VND");

        vnpParams.put(VNP_TXNREF_STR, VNP_TXNREF);
        vnpParams.put(VNP_ORDERINFO_STR, orderInfo);

        String locate = "vn";
        vnpParams.put(VNP_LOCALE_STR, locate);

        urlReturn += VnPayConfig.VNP_RETURNURL;
        vnpParams.put(VNP_RETURNURL_STR, urlReturn);
        vnpParams.put(VNP_IPADDR_STR, VNP_IDADDR);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());
        vnpParams.put(VNP_CREATEDATE_STR, vnpCreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(cld.getTime());
        vnpParams.put(VNP_EXPIREDATE_STR, vnpExpireDate);

        List<String> fieldNames = new ArrayList(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnpSecureHash = VnPayConfig.hmacSHA512(VnPayConfig.VNP_HASHSECRET, hashData.toString());
        queryUrl += "&" + VNP_SECUREHASH_STR + "=" + vnpSecureHash;
        return VnPayConfig.VNP_PAYURL + "?" + queryUrl;
    }

    @Override
    public int orderReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnpSecureHash = request.getParameter(VNP_SECUREHASH_STR);
        fields.remove(VNP_SECUREHASHTYPE_STR);
        fields.remove(VNP_SECUREHASH_STR);
        String signValue = VnPayConfig.hashAllFields(fields);
        if (signValue.equals(vnpSecureHash)) {
            if ("00".equals(request.getParameter(VNP_TRANSACTIONSTATUS_STR))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
}
