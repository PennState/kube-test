/**
 * 
 */
package edu.psu.swe.poc.kube.test.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  
  static final String ERROR_CODE_PATTERN = "[^0-9]*([0-9]*)$";
  
  Pattern errorCodePattern = Pattern.compile(ERROR_CODE_PATTERN);

  /* (non-Javadoc)
   * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    if(target.startsWith("/error")) {
      Matcher matcher = errorCodePattern.matcher(target);
      int statusCode = HttpServletResponse.SC_BAD_REQUEST;
      
      if(matcher.find()) {
        try {
        statusCode = Integer.parseInt(matcher.group(1));
        } catch(NumberFormatException e) {
          // Leave the status code set to 400 - Bad Request
        }
      }
      
      response.setContentType("application/json);charset=UTF-8");
      response.setStatus(statusCode);
      
      PrintWriter writer = response.getWriter();
      writer.println("{");
      writer.println("  \"path\": \"" + target + "\"");
      writer.println("  \"httpStatusCode\": \"" + statusCode + "\"");
      writer.println("}");
      
      baseRequest.setHandled(true);
    }
  }

}
