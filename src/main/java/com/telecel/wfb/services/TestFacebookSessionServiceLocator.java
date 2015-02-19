/**
 * TestFacebookSessionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.telecel.wfb.services;

public class TestFacebookSessionServiceLocator extends org.apache.axis.client.Service implements com.telecel.wfb.services.TestFacebookSessionService {

    public TestFacebookSessionServiceLocator() {
    }


    public TestFacebookSessionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TestFacebookSessionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TestFacebookSessionPort
    private java.lang.String TestFacebookSessionPort_address = "http://192.1.1.55:8080/TestFacebookSessionService/TestFacebookSession";

    public java.lang.String getTestFacebookSessionPortAddress() {
        return TestFacebookSessionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TestFacebookSessionPortWSDDServiceName = "TestFacebookSessionPort";

    public java.lang.String getTestFacebookSessionPortWSDDServiceName() {
        return TestFacebookSessionPortWSDDServiceName;
    }

    public void setTestFacebookSessionPortWSDDServiceName(java.lang.String name) {
        TestFacebookSessionPortWSDDServiceName = name;
    }

    public com.telecel.wfb.services.TestFacebookSession getTestFacebookSessionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TestFacebookSessionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTestFacebookSessionPort(endpoint);
    }

    public com.telecel.wfb.services.TestFacebookSession getTestFacebookSessionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.telecel.wfb.services.TestFacebookSessionPortBindingStub _stub = new com.telecel.wfb.services.TestFacebookSessionPortBindingStub(portAddress, this);
            _stub.setPortName(getTestFacebookSessionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTestFacebookSessionPortEndpointAddress(java.lang.String address) {
        TestFacebookSessionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.telecel.wfb.services.TestFacebookSession.class.isAssignableFrom(serviceEndpointInterface)) {
                com.telecel.wfb.services.TestFacebookSessionPortBindingStub _stub = new com.telecel.wfb.services.TestFacebookSessionPortBindingStub(new java.net.URL(TestFacebookSessionPort_address), this);
                _stub.setPortName(getTestFacebookSessionPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TestFacebookSessionPort".equals(inputPortName)) {
            return getTestFacebookSessionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.wfb.telecel.com/", "TestFacebookSessionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.wfb.telecel.com/", "TestFacebookSessionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TestFacebookSessionPort".equals(portName)) {
            setTestFacebookSessionPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
