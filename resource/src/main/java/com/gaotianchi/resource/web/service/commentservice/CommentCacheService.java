package com.gaotianchi.resource.web.service.commentservice;

import org.springframework.data.redis.core.RedisTemplate;

//@Service
public class CommentCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    //    @Autowired
    public CommentCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementLikeNumber(Long commentId) {
        String key = String.format("like:comment:%d", commentId);
        redisTemplate.opsForValue().increment(key);
    }

    public void incrementDislikeNumber(Long commentId) {
        String key = String.format("dislike:comment:%d", commentId);
        redisTemplate.opsForValue().increment(key);
    }

    public Integer getNumberOfCommentLike(Long commentId) {
        String key = String.format("like:comment:%d", commentId);
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    public Integer getNumberOfCommentDislike(Long commentId) {
        String key = String.format("dislike:comment:%d", commentId);
        return (Integer) redisTemplate.opsForValue().get(key);
    }

}
