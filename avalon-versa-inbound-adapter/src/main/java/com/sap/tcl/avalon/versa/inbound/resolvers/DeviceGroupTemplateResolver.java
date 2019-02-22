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
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;

public class DeviceGroupTemplateResolver implements MethodResolver, MethodExecutor {

    static final String METHOD_NAME = "determineDeviceGroupTemplate";
    private CanonicalItemService canonicalItemService;

    @Override
    public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
            throws AccessException {
        assert arguments.length == 3 && (arguments[0] == null
                || arguments[0] instanceof String) : "resolve() should make sure that the only argument is either null or String: "
                        + Arrays.deepToString(arguments);
        final String input = (String) arguments[0];
        final String dirId = (String) arguments[1];
        final String legalEntity = (String) arguments[2];
        CanonicalItem item = (CanonicalItem) target;
        DataHubPool pool = item.getDataPool();
        pool.setPoolName(dirId + VersaIntegrationConstants.POOL);
        List<String> resultList = new ArrayList<>();
        long lastProcess = 0L;
        int pageSize = 100;
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, lastProcess);
        DataHubPage<CanonicalItem> canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool,
                "CanonicalDeviceGrpandTempMap", defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> intSize = canonicalPolicyRule.getContent();
        if (!CollectionUtils.isEmpty(intSize)) {
            getResult(intSize, input, legalEntity, dirId, pool, resultList);
            while (!CollectionUtils.isEmpty(intSize)) {
                defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize,
                        (canonicalPolicyRule.getContent().get(canonicalPolicyRule.getNumberOfElements() - 1)).getId());
                canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool, "CanonicalDeviceGrpandTempMap",
                        defaultPage, CompositionStatusType.SUCCESS);
                intSize = canonicalPolicyRule.getContent();
                if (!CollectionUtils.isEmpty(intSize)) {
                    getResult(intSize, input, legalEntity, dirId, pool, resultList);
                }
            }
        }
        return new TypedValue(resultList);
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

    /**
     * Method to get template name
     * 
     * @param intSize
     * @param input
     * @param legalEntity
     * @param dirId
     * @param pool
     * @param resultList
     */
    private void getResult(List<CanonicalItem> intSize, String input, String legalEntity, String dirId,
            DataHubPool pool, List<String> resultList) {
        for (CanonicalItem colItem : intSize) {
            String val = (String) colItem.getField("deviceGroups");
            List<String> valueList = AvalonUtils.getListValue(val);
            if (valueList.contains(input) && checkTemplateExist(pool, dirId + "_" + legalEntity + "_" + (String) colItem.getField("templateName"))) {
                resultList.add(dirId + "_" + legalEntity + "_" + (String) colItem.getField("templateName")
                        + ":tclavalonProductCatalog:Staged");
            }
        }
    }

    /**
     * Method to check template existance
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

                if (!CollectionUtils.isEmpty(canonicalItems)) {
                    
                    status =   getTemplateStatus( canonicalItems, input);
                }
            }
        }
        return status;
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

    @Required
    public void setCanonicalItemService(CanonicalItemService canonicalItemService) {
        this.canonicalItemService = canonicalItemService;
    }
}
