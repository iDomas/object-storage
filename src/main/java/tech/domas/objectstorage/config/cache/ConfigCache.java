package tech.domas.objectstorage.config.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


public enum ConfigCache {
    INSTANCE;

    private CacheLoader<String, String> configCacheLoader = new CacheLoader<String, String>() {
        @Override
        public String load(String s) {
            return s.toUpperCase();
        }
    };

    public LoadingCache<String, String> configCache = CacheBuilder.newBuilder().build(configCacheLoader);

}
