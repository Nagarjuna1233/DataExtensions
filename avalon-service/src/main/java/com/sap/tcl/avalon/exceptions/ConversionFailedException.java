package com.sap.tcl.avalon.exceptions;

public class ConversionFailedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3176132804215470364L;

    private final String sourceName;
    private final String targetName;

    public ConversionFailedException(String sourceName, String targetName, String message) {
        super(message);
        this.sourceName = sourceName;
        this.targetName = targetName;
    }

    public ConversionFailedException(String sourceName, String targetName, String message, Throwable thr) {
        super(message, thr);
        this.sourceName = sourceName;
        this.targetName = targetName;

    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

}
