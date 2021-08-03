package tech.domas.objectstorage.httpserver.endpointhandlers;

import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.lang3.StringUtils;
import tech.domas.objectstorage.httpserver.utils.AbstractParameterWrapper;

import java.util.List;
import java.util.Map;

class AbstractParameterHandler {

    public static AbstractParameterWrapper getParameter(NanoHTTPD.IHTTPSession session, String targetParameter, String missingParameterMessage,
                                      String parameterNoValue, String toMuchValues) {
        Map<String, List<String>> parameters = session.getParameters();

        if (!parameters.containsKey(targetParameter)) {
            return AbstractParameterWrapper.builder()
                    .errorMessage(missingParameterMessage)
                    .ok(false)
                    .build();
        }

        if (StringUtils.isBlank(parameters.get(targetParameter).get(0))) {
            return AbstractParameterWrapper.builder()
                    .errorMessage(missingParameterMessage)
                    .ok(false)
                    .build();
        }

        if (parameters.get(targetParameter).size() > 1) {
            return AbstractParameterWrapper.builder()
                    .errorMessage(missingParameterMessage)
                    .ok(false)
                    .build();
        }

        return AbstractParameterWrapper.builder()
                .parameterValue(parameters.get(targetParameter).get(0))
                .ok(true)
                .build();
    }

}
