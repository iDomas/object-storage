package tech.domas.objectstorage.httpserver;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;
import tech.domas.objectstorage.httpserver.endpointhandlers.ErrorHandler;
import tech.domas.objectstorage.httpserver.endpointhandlers.HomeHandler;
import tech.domas.objectstorage.httpserver.endpointhandlers.SaveFileHandler;
import tech.domas.objectstorage.httpserver.utils.ErrorResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class NanoServer extends NanoHTTPD {
    private static final Logger LOGGER = LoggerFactory.getLogger(NanoServer.class.getName());

    private static final String BASIC_404 = "404...";

    public NanoServer() throws IOException, ExecutionException {
        super(Integer.parseInt(ConfigCache.configCache.get(Config.NANO_HTTPD_SERVER_PORT)));
        final boolean isDaemon = Boolean.parseBoolean(ConfigCache.configCache.get(Config.NANO_HTTPD_SERVER_DAEMON));
        final int socketReadTimeout = Integer.parseInt(ConfigCache.configCache.get(Config.NANO_HTTPD_SERVER_SOCKET_READ_TIMEOUT));
        start(socketReadTimeout, isDaemon);
        LOGGER.info("Nano HTTPD is running on port: " + Integer.parseInt(ConfigCache.configCache.get(Config.NANO_HTTPD_SERVER_PORT)));
    }

    @Override
    public Response serve(IHTTPSession session) {
        switch (session.getUri()) {
            case Endpoint.HOME:
                if (Method.GET.equals(session.getMethod())) {
                    return HomeHandler.handleHome(session);
                }
                return ErrorHandler.handlerError(session, ErrorResponse.toJSON(BASIC_404));
            case Endpoint.SAVE_FILE:
                if (Method.POST.equals(session.getMethod())) {
                    return SaveFileHandler.handleSaveFile(session);
                }
                return ErrorHandler.handlerError(session, ErrorResponse.toJSON(BASIC_404));
            default:
                return ErrorHandler.handlerError(session, ErrorResponse.toJSON(BASIC_404));
        }
    }
}
