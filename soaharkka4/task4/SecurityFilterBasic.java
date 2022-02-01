package com.demo3.task3;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityFilterBasic implements ContainerRequestFilter {
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	//private static final String SECURED_URL_PREFIX = "secured";
	
	@Context
    private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (/*(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
				||*/ (requestContext.getMethod().equals("DELETE"))) {
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if (authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString = new String(Base64.getDecoder().decode(authToken));
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				if ("username".equals(username) && "password".equals(password)) {
					return;
				}
			}
			ErrorMessage errorMessage = new ErrorMessage("User cannot access the resource.", 401, "http://myDocs.org");
			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
			requestContext.abortWith(unauthorizedStatus);
		}
	}
}