package com.sap.tcl.avalon.versa.inbound.pojos;

public class VersaQueryObject {

    private String templateName;
    private String organizationName;
    private String appsName;
    private String policyName;
    private String policyGroupName;
    private String userDefinedAppName;

    public String getUserDefinedAppName() {
        return userDefinedAppName;
    }

    public void setUserDefinedAppName(String userDefinedAppName) {
        this.userDefinedAppName = userDefinedAppName;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyGroupName() {
        return policyGroupName;
    }

    public void setPolicyGroupName(String policyGroupName) {
        this.policyGroupName = policyGroupName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAppsName() {
        return appsName;
    }

    public void setAppsName(String appsName) {
        this.appsName = appsName;
    }

}
