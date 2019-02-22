package com.sap.tcl.avalon.versa.inbound.resolvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;

public class SelectDeviceTemplateResolver implements MethodResolver, MethodExecutor {

    static final String METHOD_NAME = "determineSelectDeviceGroup";
    private CanonicalItemService canonicalItemService;

    @Override
    public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
            throws AccessException {
        assert arguments.length == 4 && (arguments[0] == null
                || arguments[0] instanceof String) : "resolve() should make sure that the only argument is either null or String: "
                        + Arrays.deepToString(arguments);
        final String input = (String) arguments[0];
        final String poolName = (String) arguments[1];
        final String serialNumber = (String) arguments[2];
        final String outPutAttribute = (String) arguments[3];
        CanonicalItem item = (CanonicalItem) target;
        DataHubPool pool = item.getDataPool();
        pool.setPoolName(poolName + VersaIntegrationConstants.POOL);
        long lastProcess = 0L;
        int pageSize = 100;
        String result = "";
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, lastProcess);
        DataHubPage<CanonicalItem> canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool,
                "CanonicalDeviceGroup", defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> intSize = canonicalPolicyRule.getContent();
        if (!CollectionUtils.isEmpty(intSize)) {
            result = getDeviceGroupResult(intSize, input, outPutAttribute, serialNumber);
            while (!CollectionUtils.isEmpty(intSize)) {
                defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize,
                        (canonicalPolicyRule.getContent().get(canonicalPolicyRule.getNumberOfElements() - 1)).getId());
                canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool, "CanonicalDeviceGroup",
                        defaultPage, CompositionStatusType.SUCCESS);
                intSize = canonicalPolicyRule.getContent();
                if (!CollectionUtils.isEmpty(intSize)) {
                    result = getDeviceGroupResult(intSize, input, outPutAttribute, serialNumber);
                }
            }
        }
        return new TypedValue(result);
    }

    @Override
    public MethodExecutor resolve(EvaluationContext ec, Object targetObject, String method,
            List<TypeDescriptor> argumentTypes) throws AccessException {
        if (METHOD_NAME.equals(method) && argumentTypes != null && argumentTypes.size() == 4) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Method to get Device Group Name
     * 
     * @param intSize
     * @param input
     * @param outPutAttribute
     * @param serialNumber
     * @return
     */
    private static String getDeviceGroupResult(List<CanonicalItem> intSize, String input, String outPutAttribute,
            String serialNumber) {
        String result = "";
        for (CanonicalItem colItem : intSize) {
            String val = (String) colItem.getField("inventoryName");
            List<String> valueList = null;
            if (!StringUtils.isEmpty(val)) {
                valueList = new ArrayList<>();
                String[] valueArray = val.split(",");
                for (int i = 0; i < valueArray.length; i++) {
                    valueList.add((String) colItem.getField("poolName") + "_" + (String) colItem.getField("legalEntity")
                            + "_" + valueArray[i] + "_" + serialNumber);
                }
            }
            if (valueList != null && !CollectionUtils.isEmpty(valueList) && valueList.contains(input)) {
                result = (String) colItem.getField(outPutAttribute);
                intSize.clear();
                break;
            }
        }
        return result;
    }

    public void setCanonicalItemService(CanonicalItemService canonicalItemService) {
        this.canonicalItemService = canonicalItemService;
    }

}
