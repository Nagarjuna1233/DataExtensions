package com.sap.tcl.avalon.versa.inbound.services;

import java.util.ArrayList;
import java.util.List;

import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.pojos.VersaQueryObject;

public class VesraTestDataUtils {

    public static final String CAUSE = "Cause is {}";
    
    public static List<Template> getTemplates(){
        List<Template> templates = new ArrayList<Template>();
        Template template = new Template();
        template.setName("IND_NGP_Template");
        template.setOrganization("SDWAN_DEMO_DEV");
        templates.add(template);
        return templates;
    }
    
    public static List<VersaQueryObject> getVersaQueryData(){
        
        List<VersaQueryObject> templates = new ArrayList<VersaQueryObject>();
        VersaQueryObject versaQueryObject = new VersaQueryObject();
        versaQueryObject.setTemplateName("IND_NGP_Template");
        versaQueryObject.setOrganizationName("SDWAN_DEMO_DEV");
        versaQueryObject.setPolicyGroupName("Default-Policy");
        templates.add(versaQueryObject);
        return templates;
    }
    
}
