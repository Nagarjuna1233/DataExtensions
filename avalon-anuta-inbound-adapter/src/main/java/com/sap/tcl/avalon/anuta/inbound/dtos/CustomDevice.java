package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.util.List;

public class CustomDevice {

    private String name;
    private List<AllDevicesRoute> allDevicesRoutes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AllDevicesRoute> getAllDevicesRoutes() {
        return allDevicesRoutes;
    }

    public void setAllDevicesRoutes(List<AllDevicesRoute> allDevicesRoutes) {
        this.allDevicesRoutes = allDevicesRoutes;
    }
}
