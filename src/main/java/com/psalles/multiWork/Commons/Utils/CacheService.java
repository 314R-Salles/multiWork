package com.psalles.multiWork.Commons.Utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    @CacheEvict(value = "user", key = "#cacheKey")
    public void evictUser(String cacheKey) {}

    @CacheEvict(value = "extensions", key = "#cacheKey")
    public void evictExtensions(String cacheKey) {}
}
