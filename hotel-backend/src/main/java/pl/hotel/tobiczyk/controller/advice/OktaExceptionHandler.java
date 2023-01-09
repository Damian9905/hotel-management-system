package pl.hotel.tobiczyk.controller.advice;

import com.okta.sdk.error.ResourceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OktaExceptionHandler {
  static final String CHANGE_PASSWORD_TEMPLATE = "changePassword";

  @ExceptionHandler(ResourceException.class)
  public String handle(ResourceException ex, final Model model) {
    var message = ex.getMessage();
    model.addAttribute("exception", message.substring(message.indexOf("'")+1, message.indexOf("}")));
    return CHANGE_PASSWORD_TEMPLATE;
  }
}
