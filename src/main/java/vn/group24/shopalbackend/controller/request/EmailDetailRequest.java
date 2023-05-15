package vn.group24.shopalbackend.controller.request;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EmailDetailRequest {

    private String recipient;
    private String msgBody;
    private String subject;
    private String text;
    private String template;
    private String attachment;
    private Map<String, Object> properties;
}
