package com.sap.tcl.avalon.anuta.inbound.resolvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.TypedValue;

public class PrimeDeviceInterfaceResolver implements MethodResolver, MethodExecutor {

    static final String METHOD_NAME = "determineInterFaceList";

    @Override
    public MethodExecutor resolve(EvaluationContext ec, Object targetObject, String method,
            List<TypeDescriptor> argumentTypes) throws AccessException {
        if (METHOD_NAME.equals(method) && argumentTypes != null && argumentTypes.size() == 1) {
            return this;
        } else {
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public TypedValue execute(final EvaluationContext context, final Object target, final Object... arguments)
            throws AccessException {
        assert arguments.length == 1 && (arguments[0] == null
                || arguments[0] instanceof String) : "resolve() should make sure that the only argument is either null or String: "
                        + Arrays.deepToString(arguments);
        final String input = (String) arguments[0];
        List<String> interFaceList = new ArrayList<>();

        String[] inputarray = input.split(",");
        if (inputarray != null) {

            for (int i = 0; i < inputarray.length; i++) {
                interFaceList.add(inputarray[i]);
            }
        }
        return new TypedValue((List) interFaceList);
    }
}
