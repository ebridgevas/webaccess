/**
 * TestFacebookSession.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.telecel.wfb.services;

public interface TestFacebookSession extends java.rmi.Remote {
    public java.lang.String optOut(java.lang.String mobileNumber, java.lang.String bundleType, java.lang.Double bundleAmount) throws java.rmi.RemoteException;
    public java.lang.String purchaseBundle(java.lang.String mobileNumber, java.lang.String bundleType, java.lang.Double bundleAmount) throws java.rmi.RemoteException;
    public java.lang.String bonusBalanceEnquiry(java.lang.String mobileNumber, java.lang.String bundleType) throws java.rmi.RemoteException, com.telecel.wfb.services.Exception;
    public java.lang.String getPcrfWalletName(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String migrate(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String migrateTest() throws java.rmi.RemoteException;
    public java.lang.String creditPCRFNewSubscriber(java.lang.String msisdn, java.lang.String walletName, java.lang.Double amountValue) throws java.rmi.RemoteException;
    public java.lang.String creditPCRFNewSubscriberTest(java.lang.String arg0, java.lang.String arg1, java.lang.Double arg2) throws java.rmi.RemoteException;
    public java.lang.String creditPCRF(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public java.lang.String creditPCRFPostPaid(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
}
