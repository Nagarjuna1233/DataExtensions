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

public class UrlCategoryPolicyRuleResolver implements MethodResolver {

    static final String METHOD_NAME = "determineUrlCategoryForPolicyRule";
    static final String CATALOGVERSION = ":tclavalonProductCatalog:Staged";
    private CanonicalItemService canonicalItemService;

    private UrlCategoriesForPolicyRuleExcecutor executor;

    public CanonicalItemService getCanonicalItemService() {
        return canonicalItemService;
    }

    public void setCanonicalItemService(CanonicalItemService canonicalItemService) {
        this.canonicalItemService = canonicalItemService;
    }

    @Override
    public MethodExecutor resolve(final EvaluationContext context, final Object targetObject, final String name,
            final List<TypeDescriptor> argumentTypes) throws AccessException {
        executor = new UrlCategoriesForPolicyRuleExcecutor(canonicalItemService);
        return isApplicable(name, argumentTypes) ? executor : null;
    }

    private static boolean isApplicable(final String method, final List<TypeDescriptor> argTypes) {
        return METHOD_NAME.equals(method) && argTypes.size() == 6 && String.class == argTypes.get(0).getType();
    }

    private static class UrlCategoriesForPolicyRuleExcecutor implements MethodExecutor {
        private CanonicalItemService canonicalItemService;

        public UrlCategoriesForPolicyRuleExcecutor(CanonicalItemService canonicalItemService) {
            this.canonicalItemService = canonicalItemService;
        }

        @Override
        public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
                throws AccessException {
            final String[] input = ((String) arguments[0]).split(":");
            final String canonialItemName = (String) arguments[1];
            final String canonialItemColumn = (String) arguments[2];
            final String poolName = (String) arguments[3];
            final String organization = (String) arguments[4];
            final String template = (String) arguments[5];
            CanonicalItem item = (CanonicalItem) target;
            DataHubPool pool = item.getDataPool();
            pool.setPoolName(poolName + VersaIntegrationConstants.POOL);
            int pageSize = 100;
            String[] predefined = null;
            String[] userDefined = null;
            List<String> inputList = new ArrayList<>();

            predefined = input[0] != null && input[0].length() > 0 ? input[0].split(",") : null;
            userDefined = (input.length == 2 && input[1] != null && input[1].length() > 0) ? input[1].split(",") : null;

            getUrlList(predefined, userDefined, inputList, poolName, organization, template);

            ArrayList<String> result = new ArrayList<>();
            DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, 0L);
            DataHubPage<CanonicalItem> canonicalUrlCategory = this.canonicalItemService.findCanonicalItems(pool,
                    canonialItemName, defaultPage, CompositionStatusType.SUCCESS);
            List<CanonicalItem> intSize = canonicalUrlCategory.getContent();

            if (!CollectionUtils.isEmpty(intSize)) {
                getUrlApplicationList(intSize, inputList, canonialItemColumn, result);

                while (!CollectionUtils.isEmpty(intSize)) {
                    defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize,
                            (canonicalUrlCategory.getContent().get(canonicalUrlCategory.getNumberOfElements() - 1))
                                    .getId());
                    canonicalUrlCategory = this.canonicalItemService.findCanonicalItems(pool, canonialItemName,
                            defaultPage, CompositionStatusType.SUCCESS);
                    intSize = canonicalUrlCategory.getContent();
                    if (!CollectionUtils.isEmpty(intSize)) {
                        getUrlApplicationList(intSize, inputList, canonialItemColumn, result);
                    }
                }
            }
            return new TypedValue(result);
        }

        private static void getUrlApplicationList(List<CanonicalItem> intSize, List<String> inputList,
                String canonialItemColumn, ArrayList<String> result) {
            for (CanonicalItem colItem : intSize) {
                String val = (String) colItem.getField(canonialItemColumn);
                if (!StringUtils.isEmpty(val) && !CollectionUtils.isEmpty(inputList) && inputList.contains(val)) {
                    result.add(((String) colItem.getField(canonialItemColumn)) + CATALOGVERSION);
                }
            }
        }

        private static void getUrlList(String[] predefined, String[] userDefined, List<String> inputList,
                String poolName, String organization, String template) {
            if (predefined != null) {
                for (int i = 0; i < predefined.length; i++) {
                    inputList.add(poolName + "_" + predefined[i]);
                }
            }
            if (userDefined != null) {
                for (int i = 0; i < userDefined.length; i++) {
                    inputList.add(poolName + "_" + organization + "_" + template + "_" + userDefined[i]);
                }
            }
        }

    }
}
