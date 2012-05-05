package com.noranj;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "MyServiceSEI", targetNamespace = "http://noranj.com/")
public interface MyServiceSEI {

	@WebMethod(operationName = "greeting", action = "urn:Greeting")
	public String greeting(@WebParam(name = "arg0") String who);

}