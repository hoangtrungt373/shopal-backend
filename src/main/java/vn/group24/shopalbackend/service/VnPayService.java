package vn.group24.shopalbackend.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author ttg
 */
public interface VnPayService {

    int orderReturn(HttpServletRequest request);

    String createOrder(int total, String orderInfo, String urlReturn);
}
