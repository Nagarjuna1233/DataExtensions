package com.sap.tcl.avalon.versa.inbound.resolvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.TypedValue;
import org.springframework.util.CollectionUtils;

import com.hybris.datahub.domain.CompositionStatusType;
import com.hybris.datahub.model.CanonicalItem;
import com.hybris.datahub.paging.DataHubIdBasedPageable;
import com.hybris.datahub.paging.DataHubPage;
import com.hybris.datahub.paging.DefaultDataHubIdBasedPageRequest;
import com.hybris.datahub.runtime.domain.DataHubPool;
import com.hybris.datahub.service.CanonicalItemService;
import com.sap.tcl.avalon.utils.AvalonUtils;

public class DeviceTemplateResolver implements MethodResolver, MethodExecutor {

    static final String METHOD_NAME = "determineTemplateForDevice";
    private CanonicalItemService canonicalItemService;

    @Override
    public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
            throws AccessException {

        assert arguments.length == 3 && (arguments[0] != null
                && arguments[1] != null) : "resolve() should make sure that the only argument is either null or String: "
                        + Arrays.deepToString(arguments);

        final String inputDevice = (String) arguments[0];
        final String poolName = (String) arguments[1];
        final String serialNumber = (String) arguments[2];
        CanonicalItem item = (CanonicalItem) target;
        DataHubPool pool = item.getDataPool();
        pool.setPoolName(poolName + "_POOL");
        String result = getResultDeviceName("CanonicalDeviceGroup", inputDevice, pool, "inventoryName", "name",
                serialNumber);
        result = getResultTemplate("CanonicalDeviceGrpandTempMap", result, pool, "deviceGroups", "templateName");
        return new TypedValue(result);
    }

    private String getResultTemplate(String canonicalItemName, String input, DataHubPool pool, String inputAttribute,
            String outputAttribute) {

        int pageSize = 100;
        String result = "";
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, 0L);
        DataHubPage<CanonicalItem> canonicalpagableitems = this.canonicalItemService.findCanonicalItems(pool,
                canonicalItemName, defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> canonicalItems = canonicalpagableitems.getContent();
        if (!CollectionUtils.isEmpty(canonicalItems)) {
            result = getTemplate(canonicalItems, input, inputAttribute, outputAttribute, pool);
            while (!CollectionUtils.isEmpty(canonicalItems)) {
                defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, canonicalpagableitems.getContent()
                        .get(canonicalpagableitems.getNumberOfElements() - 1).getId());
                canonicalpagableitems = this.canonicalItemService.findCanonicalItems(pool, canonicalItemName,
                        defaultPage, CompositionStatusType.SUCCESS);
                canonicalItems = canonicalpagableitems.getContent();
                if (!CollectionUtils.isEmpty(canonicalItems)) {
                    result = getTemplate(canonicalItems, input, inputAttribute, outputAttribute, pool);
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param canonicalItemName
     * @param input
     * @param pool
     * @param inputAttribute
     * @param outputAttribute
     * @param serialNumber
     * @return
     */
    private String getResultDeviceName(String canonicalItemName, String input, DataHubPool pool, String inputAttribute,
            String outputAttribute, String serialNumber) {
        int pageSize = 100;
        String result = "";
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, 0L);
        DataHubPage<CanonicalItem> canonicalpagableitems = this.canonicalItemService.findCanonicalItems(pool,
                canonicalItemName, defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> canonicalItems = canonicalpagableitems.getContent();
        if (!CollectionUtils.isEmpty(canonicalItems)) {
            result = getDeviceGroupName(canonicalItems, input, inputAttribute, outputAttribute, serialNumber);
            while (!CollectionUtils.isEmpty(canonicalItems)) {
                defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, canonicalpagableitems.getContent()
                        .get(canonicalpagableitems.getNumberOfElements() - 1).getId());
                canonicalpagableitems = this.canonicalItemService.findCanonicalItems(pool, canonicalItemName,
                        defaultPage, CompositionStatusType.SUCCESS);
                canonicalItems = canonicalpagableitems.getContent();
                if (!CollectionUtils.isEmpty(canonicalItems)) {
                    result = getDeviceGroupName(canonicalItems, input, inputAttribute, outputAttribute, serialNumber);
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param pool
     * @param input
     * @return
     */
    private boolean checkTemplateExist(DataHubPool pool, String input) {
        boolean status = false;
        int pageSize = 100;
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, 0L);
        DataHubPage<CanonicalItem> canonicalpagableitems = this.canonicalItemService.findCanonicalItems(pool,
                "CanonicalVersaTemplate", defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> canonicalItems = canonicalpagableitems.getContent();
        if (!CollectionUtils.isEmpty(canonicalItems)) {
            
            status = getTemplateStatus( canonicalItems, input);
            
            while (!CollectionUtils.isEmpty(canonicalItems)) {
                defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, canonicalpagableitems.getContent()
                        .get(canonicalpagableitems.getNumberOfElements() - 1).getId());
                canonicalpagableitems = this.canonicalItemService.findCanonicalItems(pool, "CanonicalVersaTemplate",
                        defaultPage, CompositionStatusType.SUCCESS);
                canonicalItems = canonicalpagableitems.getContent();
                status = getTemplateStatus( canonicalItems, input);
            }
        }
        return status;
    }

    @Override
    public MethodExecutor resolve(EvaluationContext ec, Object targetObject, String method,
            List<TypeDescriptor> argumentTypes) throws AccessException {
        if (METHOD_NAME.equals(method) && argumentTypes != null && argumentTypes.size() == 3) {
            return this;
        } else {
            return null;
        }
    }

    @Required
    public void setCanonicalItemService(CanonicalItemService canonicalItemService) {
        this.canonicalItemService = canonicalItemService;
    }

    /**
     * 
     * @param canonicalItems
     * @param input
     * @param inputAttribute
     * @param outputAttribute
     * @param serialNumber
     * @return
     */
    private static String getDeviceGroupName(List<CanonicalItem> canonicalItems, String input, String inputAttribute,
            String outputAttribute, String serialNumber) {

        String result = "";
        for (CanonicalItem colItem : canonicalItems) {
            String val = (String) colItem.getField(inputAttribute);
            String[] valueArray = val.split(",");
            List<String> valueList = new ArrayList<>();
            for (int i = 0; i < valueArray.length; i++) {
                valueList.add((String) colItem.getField("poolName") + "_" + (String) colItem.getField("legalEntity")
                        + "_" + valueArray[i] + "_" + serialNumber);
            }
            if (!CollectionUtils.isEmpty(valueList) && valueList.contains(input)) {
                result = (String) colItem.getField("poolName") + "," + (String) colItem.getField("legalEntity") + ","
                        + (String) colItem.getField(outputAttribute);
                canonicalItems.clear();
                break;
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    private String getTemplate(List<CanonicalItem> canonicalItems, String input, String inputAttribute,
            String outputAttribute, DataHubPool pool) {

        String result = "";
        for (CanonicalItem colItem : canonicalItems) {
            String val = (String) colItem.getField(inputAttribute);

            List<String> valueList = AvalonUtils.getListValue(val);
            List<String> newList = new ArrayList<>();
            for (String devicegROUP : valueList) {
                newList.add(input.split(",")[0] + "_" + input.split(",")[1] + "_" + devicegROUP);
            }
            String newInput = input;
            if (!CollectionUtils.isEmpty(newList) && newList.contains(newInput.replaceAll(",", "_"))
                    && checkTemplateExist(pool, input.split(",")[0] + "_" + input.split(",")[1] + "_"
                            + (String) colItem.getField(outputAttribute))) {
                result = input.split(",")[0] + "_" + input.split(",")[1] + "_"
                        + (String) colItem.getField(outputAttribute) + ":tclavalonProductCatalog:Staged";
                canonicalItems.clear();
                break;
            }
        }
        return result;
    }
    
    private static boolean getTemplateStatus(List<CanonicalItem> canonicalItems,String input){
        boolean status = false;
        for (CanonicalItem colItem : canonicalItems) {
            if (input.equals((String) colItem.getField("uid"))) {
                status = true;
                canonicalItems.clear();
                break;
            }
        }
        return status;
    }
    
}
