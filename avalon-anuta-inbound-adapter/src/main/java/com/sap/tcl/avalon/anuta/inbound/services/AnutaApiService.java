package com.sap.tcl.avalon.anuta.inbound.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sap.tcl.avalon.anuta.inbound.dtos.ControllerDevices;
import com.sap.tcl.avalon.anuta.inbound.dtos.ControllerDevicesResponse;
import com.sap.tcl.avalon.anuta.inbound.dtos.Device;
import com.sap.tcl.avalon.anuta.inbound.dtos.DeviceInterface;
import com.sap.tcl.avalon.anuta.inbound.dtos.InterfaceInterfaces;
import com.sap.tcl.avalon.anuta.inbound.dtos.L3featuresRoutes;

import reactor.util.CollectionUtils;

/**
 * 
 * @author TO-OW-16
 *
 */
public class AnutaApiService {

    /**
     * 
     * @param cDevices
     * @return result
     */
    public Object[] getControllerDevicesInterFaceRoutes(ControllerDevicesResponse cDevices) {

        ControllerDevices devices = null == cDevices ? null
                : (cDevices.getControllerDevices() == null ? null : cDevices.getControllerDevices());
        if (null != devices) {
            Map<String, L3featuresRoutes> routes = new LinkedHashMap<String, L3featuresRoutes>();
            Map<String, String> interfaces = new LinkedHashMap<String, String>();
            Object[] result = new Object[3];
            List<Device> updatedList = new ArrayList<Device>();
            InterfaceInterfaces interfaceInterfaces = null;
            L3featuresRoutes l3featuresRoutes = null;
            StringBuilder interFNames = null;
            for (Device device : devices.getDevice()) {
                if (null != device.getL3featuresRoutes()
                        && !CollectionUtils.isEmpty(device.getL3featuresRoutes().getRoute())) {
                    l3featuresRoutes = new L3featuresRoutes();
                    l3featuresRoutes.setRoute(device.getL3featuresRoutes().getRoute());
                    routes.put(device.getId(), l3featuresRoutes);
                }
                interfaceInterfaces = device.getInterfaceInterfaces();
                interFNames = new StringBuilder("");
                for (DeviceInterface interf : (null != interfaceInterfaces
                        && !CollectionUtils.isEmpty(interfaceInterfaces.getDeviceInterface()))
                                ? interfaceInterfaces.getDeviceInterface() : new ArrayList<DeviceInterface>()) {
                    interFNames.append(interf.getName());
                    interFNames.append(",");
                }
                interfaces.put(device.getId(), interFNames.toString());
                device.setL3featuresRoutes(null);
                device.setInterfaceInterfaces(null);
                updatedList.add(device);
            }
            // Map<String, L3featuresRoutes>
            result[0] = routes;

            // Map<String,String> interfaces
            result[1] = interfaces;

            // List<Device> updatedList
            devices.setDevice(updatedList);
            cDevices.setControllerDevices(devices);
            result[2] = cDevices;
            return result;
        }
        return new Object[] {};

    }
}
