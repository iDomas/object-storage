package tech.domas.objectstorage.httpserver;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;
import tech.domas.objectstorage.httpserver.endpointhandlers.HomeHandler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class NanoServer extends NanoHTTPD {
    private static final Logger LOGGER = LoggerFactory.getLogger(NanoServer.class.getName());

    public NanoServer() throws IOException, ExecutionException {
        super(Integer.parseInt(ConfigCache.INSTANCE.configCache.get(Config.NANO_HTTPD_SERVER_PORT)));
        final boolean isDaemon = Boolean.parseBoolean(ConfigCache.INSTANCE.configCache.get(Config.NANO_HTTPD_SERVER_DAEMON));
        final int socketReadTimeout = Integer.parseInt(ConfigCache.INSTANCE.configCache.get(Config.NANO_HTTPD_SERVER_SOCKET_READ_TIMEOUT));
        start(socketReadTimeout, isDaemon);
        LOGGER.info("Nano HTTPD is running on port: " + Integer.parseInt(ConfigCache.INSTANCE.configCache.get(Config.NANO_HTTPD_SERVER_PORT)));
    }

    @Override
    public Response serve(IHTTPSession session) {
        switch (session.getUri()) {
            case Endpoint.HOME:
                return HomeHandler.handleHome(session);
            default:
                return newFixedLengthResponse("Nothing here...");
        }
    }
}
