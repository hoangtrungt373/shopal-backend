package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import vn.group24.shopalbackend.controller.request.AccountingCalculateRequest;
import vn.group24.shopalbackend.controller.request.AccountingSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.AccountingDto;
import vn.group24.shopalbackend.domain.dto.VnPayPaymentResult;
import vn.group24.shopalbackend.service.AccountingService;

/**
 *
 * @author ttg
 */

@RestController
@RequestMapping("/api/accounting")
public class AccountingController extends AbstractController {

    @Autowired
    private AccountingService accountingService;

    @PostMapping("/get-by-criteria")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<AccountingDto>> getAccountingByCriteria(@RequestBody AccountingSearchCriteriaRequest criteria) {
        return ResponseEntity.ok().body(accountingService.getAccountingByCriteria(criteria));
    }

    @PostMapping("/calculate-accounting")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> calculateAccounting(@RequestBody AccountingCalculateRequest request) {
        return ResponseEntity.ok().body(accountingService.calculateAccounting(request));
    }

    @PostMapping("/get-payment-url")
    public ResponseEntity<String> getPaymentUrl(@RequestBody AccountingDto accountingDto,
                                                HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok().body(accountingService.getVnPayPaymentUrl(accountingDto, request));
    }

    @GetMapping("/vnpay-payment")
    public String returnOrderSuccess(HttpServletRequest request) throws JsonProcessingException {
        VnPayPaymentResult result = accountingService.saveVnPayPaymentResult(request);

        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
                "    <title>Payment success</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<!-- back to top -->\n" +
                "\n" +
                "<!-- start body -->\n" +
                "<div class=\"body py-5\">\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"w-50 m-auto\" style=\"display: flex; flex-direction: column; align-items: center; gap: 24px\">\n" +
                "            <h2 class=\"my-3 text-success text-center\">Process payment successfully</h2>\n" +
                "            <img style=\"width: 400px\"\n" +
                "                 src=\"https://img.freepik.com/free-vector/mobile-online-service-payment-concept-mobile-payments-online-payment-app-smartphone-has-security-protection-contactless-payment-business-finance-pay-transaction-online_1150-56215.jpg?w=1060&t=st=1685637602~exp=1685638202~hmac=b6b7d07e221fe6cb9f7c843a33038cc63f24d372fb6f6b096952337f4e19c4cb\"/>\n" +
                "            <a href=\"http://localhost:3000/enterprise/dashboard/accounting\" class=\"btn btn-primary\">Click here to see\n" +
                "                the payment detail</a>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<!-- end body -->\n" +
                "\n" +
                "\n" +
                "<!-- start footer -->\n" +
                "\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\"\n" +
                "        integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\"\n" +
                "        crossorigin=\"anonymous\"></script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
}
