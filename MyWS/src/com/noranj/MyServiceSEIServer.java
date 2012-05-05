
package com.noranj;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2012-05-04T22:12:57.212-07:00
 * Generated source version: 2.6.0
 * 
 */
 
public class MyServiceSEIServer{

    protected MyServiceSEIServer() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new com.noranj.MyService();
        String address = "http://localhost:8080/MyServicePort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new MyServiceSEIServer();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
 
 