package com.sap.tcl.avalon.anuta.inbound.resolvers;

import java.util.Arrays;
import java.util.List;

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

public class PrimDeviceCpeServiceResolver implements MethodResolver {

    static final String METHOD_NAME = "determineSiteType";
    private CanonicalItemService canonicalItemService;
    PrimeDeviceSiteTypeExecutor executor;

    @Override
    public MethodExecutor resolve(EvaluationContext arg0, Object arg1, String name, List<TypeDescriptor> argumentTypes)
            throws AccessException {
        executor = new PrimeDeviceSiteTypeExecutor(canonicalItemService);
        return isApplicable(name, argumentTypes) ? executor : null;

    }

    //
    private static boolean isApplicable(final String method, final List<TypeDescriptor> argTypes) {
        return METHOD_NAME.equals(method) && argTypes.size() == 3;
    }

    private static class PrimeDeviceSiteTypeExecutor implements MethodExecutor {

        private CanonicalItemService canonicalItemService;

        private static final String ITEM_TYPE = "CanonicalManagedCpeServices";

        public PrimeDeviceSiteTypeExecutor(CanonicalItemService canonicalItemService) {
            this.canonicalItemService = canonicalItemService;
        }

        @Override
        public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
                throws AccessException {

            assert arguments.length == 3 : "resolve() should make sure that the only argument is either null or String: "
                    + Arrays.deepToString(arguments);

            final String input = (String) arguments[0];
            final String outputAttribute = (String) arguments[1];
            final String poolName = (String) arguments[2];
            CanonicalItem item = (CanonicalItem) target;
            DataHubPool pool = item.getDataPool();
            pool.setPoolName(poolName);
            int pageSize = 100;

            String result = "";

            DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, 0L);
            DataHubPage<CanonicalItem> canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool,
                    ITEM_TYPE, defaultPage, CompositionStatusType.SUCCESS);
            List<CanonicalItem> intSize = canonicalPolicyRule.getContent();
            if (!CollectionUtils.isEmpty(intSize)) {
                result = getFinalResult(intSize, input, outputAttribute);
                while (!CollectionUtils.isEmpty(intSize)) {
                    defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize,
                            (canonicalPolicyRule.getContent().get(canonicalPolicyRule.getNumberOfElements() - 1))
                                    .getId());
                    canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool, ITEM_TYPE, defaultPage,
                            CompositionStatusType.SUCCESS);
                    intSize = canonicalPolicyRule.getContent();

                    if (!CollectionUtils.isEmpty(intSize)) {

                        result = getFinalResult(intSize, input, outputAttribute);
                    }
                }
            }

            return new TypedValue(result);
        }

        private static String getFinalResult(List<CanonicalItem> intSize, String input, String outputAttribute) {
            String result = "";

            for (CanonicalItem colItem : intSize) {
                String priDevice = (String) colItem.getField("primaryDevice") != null
                        ? (String) colItem.getField("primaryDevice") : " ";
                String secDevice = (String) colItem.getField("secondaryDevice") != null
                        ? (String) colItem.getField("secondaryDevice") : "";

                if (priDevice.equalsIgnoreCase(input) || secDevice.equalsIgnoreCase(input)) {
                    result = (String) colItem.getField(outputAttribute);
                    intSize.clear();
                    break;
                }
            }
            return result;
        }
    }

    public void setCanonicalItemService(CanonicalItemService canonicalItemService) {
        this.canonicalItemService = canonicalItemService;
    }

    public CanonicalItemService getCanonicalItemService() {
        return canonicalItemService;
    }
}
