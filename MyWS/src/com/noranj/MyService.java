package com.noranj;

import javax.jws.WebService;

@WebService(targetNamespace = "http://noranj.com/", endpointInterface = "com.noranj.MyServiceSEI", portName = "MyServicePort", serviceName = "MyServiceService")
public class MyService implements MyServiceSEI {
	public String greeting(String who){
		return " greet " + who ; 
	}
}
