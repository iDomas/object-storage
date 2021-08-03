package tech.domas.objectstorage.httpserver.utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AbstractParameterWrapper {

    private String parameterValue;
    private boolean ok;
    private String errorMessage;

}
