package com.sap.tcl.avalon.versa.inbound.resolvers;

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

public class DirectorResolver implements MethodResolver, MethodExecutor {

    static final String METHOD_NAME = "determineDirector";

    private CanonicalItemService canonicalItemService;

    @Override
    public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
            throws AccessException {
        assert arguments.length == 1 && ((arguments[0] == null)
                || (arguments[0] instanceof String)) : "resolve() should make sure that the only argument is either null or String: "
                        + Arrays.deepToString(arguments);
        final String input = (String) arguments[0];
        CanonicalItem item = (CanonicalItem) target;
        DataHubPool pool = item.getDataPool();
        pool.setPoolName(input);
        int pageSize = 100;
        String result = "";
        DataHubIdBasedPageable defaultPage = new DefaultDataHubIdBasedPageRequest(pageSize, 0L);
        DataHubPage<CanonicalItem> canonicalPolicyRule = this.canonicalItemService.findCanonicalItems(pool,
                "CanonicalDirectoryPollTime", defaultPage, CompositionStatusType.SUCCESS);
        List<CanonicalItem> intSize = canonicalPolicyRule.getContent();
        if (!CollectionUtils.isEmpty(intSize)) {
            result = (String) intSize.get(0).getField("dirName");
        }
        return new TypedValue(result);
    }

    @Override
    public MethodExecutor resolve(EvaluationContext ec, Object targetObject, String method,
            List<TypeDescriptor> argumentTypes) throws AccessException {
        if (METHOD_NAME.equals(method) && argumentTypes != null && argumentTypes.size() == 1) {
            return this;
        } else {
            return null;
        }
    }

    public void setCanonicalItemService(CanonicalItemService canonicalItemService) {
        this.canonicalItemService = canonicalItemService;
    }
}
