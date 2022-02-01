package com.demo3.task3;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("webapi")
public class MyAppReg extends ResourceConfig {
	
	public MyAppReg() {
		register(SecurityFilterBasic.class);
		register(SecurityFilter.class);
		register(RolesAllowedDynamicFeature.class);
	}
}