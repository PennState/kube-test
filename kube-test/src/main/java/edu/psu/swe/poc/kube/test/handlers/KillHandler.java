/**
 * 
 */
package edu.psu.swe.poc.kube.test.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Provides a handler for any path starting with "/kill" that terminates the
 * server process.
 * 
 * @author Steve Moyer &lt;smoyer@psu.edu&gt;
 */
public class KillHandler extends AbstractHandler {

  Server server;

  public KillHandler(Server server) {
    this.server = server;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
   * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    if (target.startsWith("/kill")) {
      try {
//        server.stop();
        server.destroy();
      } catch (Exception e) {
        throw new ServletException(e);
      }
    }
  }

}
