package com.sap.tcl.avalon.anuta.inbound.conversion.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.sap.tcl.avalon.anuta.inbound.constants.AnutaIntegrationConstants;
import com.sap.tcl.avalon.anuta.inbound.dtos.AllDevicesOption;
import com.sap.tcl.avalon.anuta.inbound.dtos.AllDevicesRoute;
import com.sap.tcl.avalon.anuta.inbound.dtos.ClassMap;
import com.sap.tcl.avalon.anuta.inbound.dtos.ClassMatchCondition;
import com.sap.tcl.avalon.anuta.inbound.dtos.ControllerDevicesResponse;
import com.sap.tcl.avalon.anuta.inbound.dtos.Cpe;
import com.sap.tcl.avalon.anuta.inbound.dtos.Customer;
import com.sap.tcl.avalon.anuta.inbound.dtos.DCMDevice;
import com.sap.tcl.avalon.anuta.inbound.dtos.Device;
import com.sap.tcl.avalon.anuta.inbound.dtos.DeviceOwner;
import com.sap.tcl.avalon.anuta.inbound.dtos.L3featuresRoutes;
import com.sap.tcl.avalon.anuta.inbound.dtos.Site;
import com.sap.tcl.avalon.anuta.inbound.dtos.SiteServices;

public class AnutaConversionUtill {

    private AnutaConversionUtill() {

    }

    public static void getAllDevices(ControllerDevicesResponse updatedControllerDevicesResponse, String dirId,
            Set<String> organizations, Map<String, String> deviceIds, Map<String, String> interfaces,
            List<Map<String, String>> result) {

        DeviceOwner deviceOwner = null;
        String interStr = null;
        for (Device device : updatedControllerDevicesResponse != null
                ? updatedControllerDevicesResponse.getControllerDevices().getDevice() : new ArrayList<Device>()) {
            Map<String, String> lastKeys = new HashMap<>();

            lastKeys.put("id", device.getId());
            lastKeys.put("os-version", device.getOsVersion());
            lastKeys.put("serial-number", device.getSerialNumber());
            lastKeys.put("status", device.getStatus());
            lastKeys.put("device-type", device.getDeviceType());
            lastKeys.put("service-provisioning-status", device.getServiceProvisioningStatus());
            lastKeys.put(AnutaIntegrationConstants.DIRID, dirId);
            deviceOwner = device.getDeviceOwner();
            if (null != deviceOwner && StringUtils.isNotEmpty(deviceOwner.getOwner())) {
                lastKeys.put("@_owner", deviceOwner.getOwner());
                organizations.add(deviceOwner.getOwner());
                lastKeys.put(AnutaIntegrationConstants.UNIQUEID,
                        dirId + "_" + deviceOwner.getOwner() + "_" + device.getId());
                deviceIds.put(device.getId(), deviceOwner.getOwner());
            } else {
                continue;
            }
            interStr = interfaces != null ? interfaces.get(device.getId()) : null;
            interStr = interStr != null && (StringUtils.isNotBlank(interStr) && interStr.endsWith(","))
                    ? interStr.substring(0, interStr.length() - 1) : interStr;
            lastKeys.put("interfaces", interStr);
            lastKeys.put(AnutaIntegrationConstants.POOL_NAME, dirId + AnutaIntegrationConstants.POOL);

            result.add(lastKeys);
        }

    }

    /**
     * Method to get device routes
     * 
     * @param routes
     * @param deviceIds
     * @param result
     * @param dirId
     */

    public static void getDeviceRoutes(Map<String, L3featuresRoutes> routes, Map<String, String> deviceIds,
            List<Map<String, String>> result, String dirId) {

        for (Entry<String, L3featuresRoutes> dd : routes.entrySet()) {
            if (deviceIds.containsKey(dd.getKey())) {
                getDeviceRoutesData( dirId , deviceIds, dd, result);
            }
        }
    }

    /**
     * Methods to get all sites
     * 
     * @param customer
     * @param response
     * @param dirId
     */
    public static void mapAllSitesData(Customer customer, List<Map<String, String>> response, String dirId) {
        Map<String, String> eachSite = null;
        Site site = null;

        site = customer.getDualCpeSite();
        if (null != site && !CollectionUtils.isEmpty(site.getDualCpeSiteServices())) {
            for (SiteServices siteServices : site.getDualCpeSiteServices()) {
                eachSite = new LinkedHashMap<>();
                mapEachSitesData(customer.getName(), "DUAL_CPE_SITE", siteServices, eachSite, dirId);
                response.add(eachSite);
            }
        }
        site = customer.getSingleCpeSite();
        if (null != site && !CollectionUtils.isEmpty(site.getSingleCpeSiteServices())) {
            for (SiteServices siteServices : site.getSingleCpeSiteServices()) {
                eachSite = new LinkedHashMap<>();
                mapEachSitesData(customer.getName(), "SINGLE_CPE_SITE", siteServices, eachSite, dirId);
                response.add(eachSite);
            }
        }
        site = customer.getSingleCpeDualWanSite();
        if (null != site && !CollectionUtils.isEmpty(site.getSingleCpeDualWanSiteServices())) {
            for (SiteServices siteServices : site.getSingleCpeDualWanSiteServices()) {
                eachSite = new LinkedHashMap<>();
                mapEachSitesData(customer.getName(), "SINGLE_CPE_DUAL_WAN_SITE", siteServices, eachSite, dirId);
                response.add(eachSite);
            }
        }
    }

    /**
     * Method to get each site data
     * 
     * @param orgName
     * @param siteType
     * @param siteService
     * @param eachSite
     * @param dirId
     */
    protected static void mapEachSitesData(String orgName, String siteType, SiteServices siteService,
            Map<String, String> eachSite, String dirId) {
        eachSite.put(AnutaIntegrationConstants.UNIQUEID,
                dirId + "_" + orgName + "_" + siteService.getSiteName() + "_" + siteType);
        eachSite.put(AnutaIntegrationConstants.ORGNAME, orgName);
        eachSite.put("site-type", siteType);
        eachSite.put(AnutaIntegrationConstants.POOL_NAME, dirId + AnutaIntegrationConstants.POOL);
        eachSite.put("site-name", siteService.getSiteName());
        eachSite.put("zero-touch-prov", String.valueOf(siteService.isZeroTouchProv()));
        eachSite.put(AnutaIntegrationConstants.DIRID, dirId);
        Cpe cpe = null;
        cpe = siteService.getCpe();
        if (null != cpe) {
            eachSite.put("cpe-primary", cpe.getDeviceIp());
        }
        cpe = siteService.getCpePrimary();
        if (null != cpe) {
            eachSite.put("cpe-primary", cpe.getDeviceIp());
        }
        cpe = siteService.getCpeSecondary();
        if (null != cpe) {
            eachSite.put("cpe-secondary", cpe.getDeviceIp());
        }
    }

    /**
     * Method to get Device Qos policies
     * 
     * @param dcmDevice
     * @param owner
     * @param preDefinedapplications
     * @param response
     * @param qosPolicies
     * @param dirId
     */
    public static void getDeviceQosPolicies(DCMDevice dcmDevice, String owner,
            Map<String, String> preDefinedapplications, List<Map<String, String>> response,
            List<Map<String, String>> qosPolicies, String dirId) {

        for (ClassMap clsMap : dcmDevice.getQosClassMaps().getClassMap()) {

            if (clsMap != null && !CollectionUtils.isEmpty(clsMap.getClassMatchCondition())) {

                getQosPolicies( clsMap, dirId, dcmDevice, owner, preDefinedapplications,
                         response, qosPolicies); 
            }
        }
    }

    private  static void getDeviceRoutesData(String dirId , Map<String, String> deviceIds, Entry<String, L3featuresRoutes> dd,List<Map<String, String>> result){
        
        Map<String,String> lastKeys = null;
        AllDevicesOption allDevicesOption;
        for (AllDevicesRoute routr : dd.getValue().getRoute()) {
            lastKeys = new HashMap<>();
            lastKeys.put("device-id", dirId + "_" + deviceIds.get(dd.getKey()) + "_" + dd.getKey());
            lastKeys.put(AnutaIntegrationConstants.POOL_NAME, dirId + AnutaIntegrationConstants.POOL);
            lastKeys.put("dest-mask", routr.getDestMask());
            lastKeys.put("dest-ip-address", routr.getDestIpAddress());
            if (!CollectionUtils.isEmpty(routr.getOptions())) {
                allDevicesOption = routr.getOptions().iterator().next();
                lastKeys.put("options_id", allDevicesOption.getId());
                lastKeys.put("options_name", allDevicesOption.getName());
                lastKeys.put("options_next-hop-ip", allDevicesOption.getNextHopIp());
                lastKeys.put("options_metric", allDevicesOption.getMetric());
                lastKeys.put("options_interface-name", allDevicesOption.getInterfaceName());
                lastKeys.put("options_tag", allDevicesOption.getTag());
                lastKeys.put("deviceRouteUniqueId",
                        dd.getKey() + allDevicesOption.getInterfaceName() + routr.getDestIpAddress());
                lastKeys.put(AnutaIntegrationConstants.UNIQUEID, dirId + "_" + deviceIds.get(dd.getKey()) + "_"
                        + dd.getKey() + "_" + allDevicesOption.getId());
                lastKeys.put(AnutaIntegrationConstants.DIRID, dirId);
            } else {
                continue;
            }
            result.add(lastKeys);
        }
        
    }
    
    private static void  getQosPolicies(ClassMap clsMap,String dirId,DCMDevice dcmDevice,String owner, Map<String, String> preDefinedapplications,
            List<Map<String, String>> response,List<Map<String, String>> qosPolicies){
        
        Map<String, String> resultMap = null;
        Map<String, String> qosPolicy = null;
        
        for (ClassMatchCondition matchCondition : clsMap.getClassMatchCondition()) {
            resultMap = new LinkedHashMap<>();
            resultMap.put("id", dirId + "_" + owner + "_" + dcmDevice.getId());
            resultMap.put(AnutaIntegrationConstants.POOL_NAME, dirId + AnutaIntegrationConstants.POOL);
            resultMap.put("name", clsMap.getName() + "_" + dirId);

            if (preDefinedapplications.containsKey(matchCondition.getMatchValue())) {
                resultMap.put("match-value", dirId + "_" + matchCondition.getMatchValue());
            } else {
                resultMap.put("match-value", dirId + "_" + owner + "_" + matchCondition.getMatchValue());
            }

            resultMap.put("uniqueId",
                    dirId + "_" + owner + "_" + dcmDevice.getId() + matchCondition.getMatchValue());
            resultMap.put(AnutaIntegrationConstants.DIRID, dirId);
            resultMap.put("device-name", dcmDevice.getId());
            resultMap.put(AnutaIntegrationConstants.APP_NAME, matchCondition.getMatchValue());
            response.add(resultMap);

            qosPolicy = new LinkedHashMap<>();
            qosPolicy.put("policyName", clsMap.getName());
            qosPolicy.put(AnutaIntegrationConstants.DIRID, dirId);
            qosPolicy.put(AnutaIntegrationConstants.POOL_NAME, dirId + AnutaIntegrationConstants.POOL);
            qosPolicies.add(qosPolicy);
        }
    }
}
