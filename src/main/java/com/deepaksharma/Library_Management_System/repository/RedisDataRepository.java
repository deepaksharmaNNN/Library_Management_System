package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisDataRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String AUTHOR_KEY = "author:";

    public void saveAuthorToRedis(Author author) {
        redisTemplate.opsForValue().set(AUTHOR_KEY.concat(author.getEmail()), author);
    }

    public Author getAuthorFromRedis(String email) {
        return (Author) redisTemplate.opsForValue().get(AUTHOR_KEY.concat(email));
    }

}
