package tech.domas.objectstorage.httpserver.endpointhandlers;

import fi.iki.elonen.NanoHTTPD;

public class ErrorHandler {

    public static NanoHTTPD.Response handlerError(NanoHTTPD.IHTTPSession session, String errorMessage) {
        return NanoHTTPD.newFixedLengthResponse(errorMessage);
    }

}
