package ink.aos.boot.ap.rest;

import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * All rights Reserved, Designed By www.aos.ink
 *
 * @author: lichaohn@163.com
 * @date: 2019-05-27 17:23
 * @copyright: 2019 www.aos.ink All rights reserved.
 */
@RestController
public class CacheResource {

    private final CacheManager cacheManager;

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheResource(CacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {
        this.cacheManager = cacheManager;
        this.redisTemplate = redisTemplate;
    }

    @Async
    @DeleteMapping("/cache/clean")
    public ResponseEntity<Void> clean() {
        cacheManager.getCacheNames().forEach(s -> {
            cacheManager.getCache(s).clear();
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping("/redis/clean")
    public ResponseEntity<Void> redisClean() {
        redisTemplate.keys("*").forEach(s -> {
            redisTemplate.delete(s);
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping("/redis/info")
    public Collection<String> info() {
        return redisTemplate.keys("*");
    }

    @GetMapping("/cache/test")
    public ResponseEntity<Void> test() {
        cacheManager.getCache("test").put("test1", "test2");
        return ResponseEntity.ok().build();
    }
}
