package edu.psu.swe.poc.kube.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;

import edu.psu.swe.poc.kube.test.handlers.ErrorHandler;
import edu.psu.swe.poc.kube.test.handlers.KillHandler;
import edu.psu.swe.poc.kube.test.handlers.StatusHandler;

public class KubeTest {
  
  public static void main(String[] args) throws Exception {
    (new KubeTest()).run();
  }
  
  private void run() throws Exception {
    Server server = new Server(8080);
    
    HandlerList handlerList = new HandlerList();
    handlerList.addHandler(new KillHandler(server));
    handlerList.addHandler(new ErrorHandler());
    
    // This is the default handler and must go last
    handlerList.addHandler(new StatusHandler(server));
    
    server.setHandler(handlerList);
    server.start();
    server.join();
  }

}
