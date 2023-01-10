package pl.hotel.tobiczyk.controller.advice;

import com.okta.sdk.error.ResourceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.hotel.tobiczyk.domain.constants.TemplateConstants;

@ControllerAdvice
public class OktaExceptionHandler {


  @ExceptionHandler(ResourceException.class)
  public String handle(ResourceException ex, final Model model) {
    var message = ex.getMessage();
    model.addAttribute("exception", message.substring(message.indexOf("'")+1, message.indexOf("}")));
    return TemplateConstants.CHANGE_PASSWORD_TEMPLATE;
  }
}
