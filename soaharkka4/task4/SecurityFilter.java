package com.demo3.task3;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	
	@Context
    private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();
	    //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class)) {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class)) {
            	ErrorMessage errorMessage = new ErrorMessage("Access blocked for all users!", 401, "http://myDocs.org");
            	requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                         .entity(errorMessage).build());
                return;
            }
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
              
            //If no authorization information present; block access
            if(authHeader == null || authHeader.isEmpty()) {
            	ErrorMessage errorMessage = new ErrorMessage("User cannot access the resource.", 401, "http://myDocs.org");
    			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
    			requestContext.abortWith(unauthorizedStatus);
                return;
            }
            if(authHeader.get(0).contains("Basic")) {
            	String authToken = authHeader.get(0);
    			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
    			String decodedString = new String(Base64.getDecoder().decode(authToken));
    			try {
    				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
    				String username = tokenizer.nextToken();
    				String password = tokenizer.nextToken();  
    				//Verify user access
    				if(method.isAnnotationPresent(RolesAllowed.class)) {
                    	RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    	Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                      
                    	//Is user valid?
                    	if( !isUserAllowed(username, password, rolesSet)) {
                    		ErrorMessage errorMessage = new ErrorMessage("You cannot access this resource", 401, "http://myDocs.org");
                    		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build());
                        	return;
                    	}
                	}
    			} 
                catch(NoSuchElementException exception){
    				ErrorMessage errorMessage = new ErrorMessage("User cannot access the resource.", 401, "http://myDocs.org");
        			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
        			requestContext.abortWith(unauthorizedStatus);
                    return;
    			}
            }else {
            	String authToken = authHeader.get(0);
            	authToken = authToken.replaceFirst("Digest", "");
            	authToken = authToken.replaceAll("\"", "");
            	List<String> tokens = Arrays.asList(authToken.split(","));
            	
            	String username = getToken(tokens, " username=");
            	String realm = getToken(tokens, " realm=");
            	String nonce = getToken(tokens, " nonce=");
            	String qop = getToken(tokens, " qop=");
            	String nc = getToken(tokens, " nc=");
            	String cnonce = getToken(tokens, " cnonce=");
            	String response = getToken(tokens, " response=");
            	//Probly not needed in this implementation
            	//String opaque = getToken(tokens, "opaque=");
            	//Add request string
            	String request = "GET:/task4/webapi/users";
            	if(request != null && username != null && realm != null && nonce != null && qop != null && nc != null && cnonce != null && response != null) {
            		UserService service = new UserService();
            		User chosen = service.findUser(username);
            		if(chosen != null) {
            			String password = chosen.getPassword();
            			String hash = md5ResponseChecker(username, realm, password, request, nonce, nc, cnonce, qop);
            			if(hash.equals(response)) {
            				if(method.isAnnotationPresent(RolesAllowed.class)) {
                            	RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                            	Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                              
                            	//Is user valid?
                            	if( !isUserAllowed(username, password, rolesSet)) {
                            		ErrorMessage errorMessage = new ErrorMessage("You cannot access this resource", 401, "http://myDocs.org");
                            		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build());
                                	return;
                            	}
                        	}
            			}else { // poista myöhemmin
                			ErrorMessage errorMessage = new ErrorMessage("User cannot access the resource."+" "+hash+" "+response, 401, "http://myDocs.org"); //+" "+username+" "+realm+" "+password+" "+request+" "+nonce+" "+nc+" "+cnonce+" "+qop
                			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
                			requestContext.abortWith(unauthorizedStatus);
                            return;
                		}
            		}
            	}else {
            		ErrorMessage errorMessage = new ErrorMessage("User cannot access the resource. TODO: give nonce etc. information from here.", 401, "http://myDocs.org");
        			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
        			requestContext.abortWith(unauthorizedStatus);
                    return;
            	}
            	//TODO check if db has matching user. use that user's password to make hash value to check against response. make error return message have nonce etc. that are needed for authorization
            }
            
        }
    }
	
	
	private String getToken(List<String> listOfTokens, String tokenName) {
		for(String token : listOfTokens) {
			if(token.startsWith(tokenName)) {
				return token.substring(tokenName.length());
			}
		}
		return null;
	}
	
	//nc mby int? check incase of wrong response. md5ResponseChecker(username, realm, password, request, nonce, nc, cnonce, qop);
	private String md5ResponseChecker(String username, String realm, String password, String request, String nonce, String nc, String cnonce, String qop) {
		MessageDigest md = null;
		byte[] digest = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			digest = md.digest((username+":"+realm+":"+password).getBytes("iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//digest = md.digest((username+":"+realm+":"+password).getBytes());
		
		/*md.update((username+":"+realm+":"+password).getBytes());
		digest = md.digest();*/
		String h1 = convertToHex(digest);//DatatypeConverter.printHexBinary(digest);//Base64.getEncoder().encodeToString(digest);//new String(digest);//DatatypeConverter..printHexBinary(digest); //printHexBinary
		try {
			digest = md.digest((request).getBytes("iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String h2 = convertToHex(digest);
		
		try {
			digest = md.digest((h1+":"+nonce+":"+nc+":"+cnonce+":"+qop+":"+h2).getBytes("iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String h3 = convertToHex(digest);
		/*md.update((request).getBytes());
		digest = md.digest();
		String h2 = DatatypeConverter.printHexBinary(digest);
		md.update((h1+":"+nonce+":"+nc+":"+cnonce+":"+qop+":"+h2).getBytes());
		digest = md.digest();
		String h3 = DatatypeConverter.printHexBinary(digest);*/
		
		return h3;// h1+" "+h2;
	}
	
	private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
	
	private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;
        UserService service = new UserService();
        User user = service.checkCredentials(username, password);
        if(user != null) {
            if(user.hasRole(rolesSet)) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }
}
