package myServlets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class RunServlets {
	
public static void main(String[] args) throws Exception{
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(SearchPrices.class, "/searchPrices");


        Server server = new Server(1235);		
		server.setHandler(handler);
		server.start();
		server.dumpStdErr();
		server.join();
	
	}

}