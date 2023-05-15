package vn.group24.shopalbackend.service;

import vn.group24.shopalbackend.controller.request.EmailDetailRequest;

/**
 *
 * @author ttg
 */
public interface EmailService {

    String sendEmail(EmailDetailRequest request);
}
