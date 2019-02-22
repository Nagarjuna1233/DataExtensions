package com.sap.tcl.avalon.versa.inbound.resolvers;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class SelectDeviceStatusResolver implements MethodResolver, MethodExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(SelectDeviceStatusResolver.class);
    static final String METHOD_NAME = "determineDeviceStatus";
    private CanonicalItemService canonicalItemService;

    @Override
    public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
            throws AccessException {
        assert arguments.length == 2 && (arguments[0] == null
                || arguments[0] instanceof String) : "resolve() should make sure that the only argument is either null or String: "
                        + Arrays.deepToString(arguments);
        final String input = (String) arguments[0];
        final String poolName = (String) arguments[1];
        CanonicalItem item = (CanonicalItem) target;
        DataHubPool pool = item.getDataPool();
        pool.setPoolName(poolName);
        long lastProcess = 0L;
        int pageSize = 100;
        String result = "";
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, lastProcess);
        DataHubPage<CanonicalItem> canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool,
                "CanonicalDeviceStatus", defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> intSize = canonicalPolicyRule.getContent();
        if (!CollectionUtils.isEmpty(intSize)) {
            result = getResult(intSize, input);
            while (!CollectionUtils.isEmpty(intSize)) {
                defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize,
                        (canonicalPolicyRule.getContent().get(canonicalPolicyRule.getNumberOfElements() - 1)).getId());
                canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool, "CanonicalDeviceStatus",
                        defaultPage, CompositionStatusType.SUCCESS);
                intSize = canonicalPolicyRule.getContent();
                if (!CollectionUtils.isEmpty(intSize)) {
                    result = getResult(intSize, input);
                }
            }
        }
        return new TypedValue(result);
    }

    @Override
    public MethodExecutor resolve(EvaluationContext ec, Object targetObject, String method,
            List<TypeDescriptor> argumentTypes) throws AccessException {
        if (METHOD_NAME.equals(method) && argumentTypes != null && argumentTypes.size() == 2) {
            return this;
        } else {
            return null;
        }
    }

    private static String getResult(List<CanonicalItem> intSize, String input) {
        String result = "";
        for (CanonicalItem colItem : intSize) {
            String val = (String) colItem.getField("deviceName");
            if (!StringUtils.isEmpty(val) && val.equalsIgnoreCase(input)) {
                result = (String) colItem.getField("deviceStatus");
                LOG.info("result for input {} is {}", input, result);
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
