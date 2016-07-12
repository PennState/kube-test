/**
 * 
 */
package edu.psu.swe.poc.kube.test.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * @author swm16
 *
 */
public class ErrorHandler extends AbstractHandler {

  /* (non-Javadoc)
   * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    if(request.getPathInfo().startsWith("/error")) {
      response.setContentType("application/json);charset=UTF-8");
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().println("{\"error\":\"304\"}");
      baseRequest.setHandled(true);
    }
  }

}
