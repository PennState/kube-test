package edu.psu.swe.poc.kube.test.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class StatusHandler extends AbstractHandler {
  
  static final String CONTENT_TYPE = "application/json);charset=UTF-8";
  static final int STATUS = HttpServletResponse.SC_OK;
  
  Server server;
  
  public StatusHandler(Server server) {
    this.server = server;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType(CONTENT_TYPE);
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter writer = response.getWriter();
    
    writer.println("{");
    writer.println("  \"path\": \"" + target + "\"");
    writer.println("  \"httpStatusCode\": \"" + HttpServletResponse.SC_OK + "\"");
    writer.println("  \"contentType\": \"" + CONTENT_TYPE + "\",");
    writer.println("  \"ipAddresses\": [");
    
    for(Connector connector: server.getConnectors()) {
      for(EndPoint endPoint: connector.getConnectedEndPoints()) {
        InetSocketAddress ip = endPoint.getLocalAddress();
        String ipAddress = ip.getAddress().toString().substring(1);
        writer.println("    {");
        writer.println("      \"ipAddress\": \"" + ipAddress + "\",");
        writer.println("      \"port\": \"" + ip.getPort() + "\"");
        writer.println("    },");
      }
    }
    
    writer.println("  ]");
    writer.println("}");
    
    baseRequest.setHandled(true);
  }

}
