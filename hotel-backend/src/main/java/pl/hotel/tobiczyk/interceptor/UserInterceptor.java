package pl.hotel.tobiczyk.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor extends HandlerInterceptorAdapter {
  @Value("${admin.name}")
  private static String ADMIN_NAME;
  private static final String ADMIN_PANEL_URL = "panel/admin";

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object) throws Exception {
    if(!SecurityContextHolder.getContext()
        .getAuthentication().getName().equals(ADMIN_NAME) &&
        request.getRequestURL().toString().contains(ADMIN_PANEL_URL)) {
      response.sendRedirect("/");
    }
    return true;
  }
}
