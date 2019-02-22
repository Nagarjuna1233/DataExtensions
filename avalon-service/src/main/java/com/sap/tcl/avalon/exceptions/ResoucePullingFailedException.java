package com.sap.tcl.avalon.exceptions;

public class ResoucePullingFailedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3176132804215470364L;

    private final String interfaceName;
    private final String interfaceUrl;

   
    public ResoucePullingFailedException(String interfaceName, String interfaceUrl, String message) {
        super(message);
        this.interfaceName = interfaceName;
        this.interfaceUrl = interfaceUrl;

    }

    public ResoucePullingFailedException(String interfaceName, String interfaceUrl, String message, Throwable thr) {
        super(message, thr);
        this.interfaceName = interfaceName;
        this.interfaceUrl = interfaceUrl;

    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

}
