package com.gaotianchi.resourceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ArticleCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementLikeNumber(Long articleId) {
        String key = String.format("like:article:%d", articleId);
        redisTemplate.opsForValue().increment(key);
    }

    public void incrementDislikeNumber(Long articleId) {
        String key = String.format("dislike:article:%d", articleId);
        redisTemplate.opsForValue().increment(key);
    }

    public Integer getNumberOfArticleLike(Long articleId) {
        String key = String.format("like:article:%d", articleId);
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    public Integer getNumberOfArticleDislike(Long articleId) {
        String key = String.format("dislike:article:%d", articleId);
        return (Integer) redisTemplate.opsForValue().get(key);
    }
}
