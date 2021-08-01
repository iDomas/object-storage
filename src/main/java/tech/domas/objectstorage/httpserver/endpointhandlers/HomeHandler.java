package tech.domas.objectstorage.httpserver.endpointhandlers;

import fi.iki.elonen.NanoHTTPD;

public class HomeHandler {

    public static NanoHTTPD.Response handleHome(NanoHTTPD.IHTTPSession session) {
        return NanoHTTPD.newFixedLengthResponse("Hello from Home!");
    }

}
