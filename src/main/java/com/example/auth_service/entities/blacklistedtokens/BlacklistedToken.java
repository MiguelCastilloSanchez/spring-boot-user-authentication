package com.example.auth_service.entities.blacklistedtokens;

import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
import org.springframework.data.annotation.Id; 
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
  
@Data 
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "blacklisted_tokens")  
public class BlacklistedToken { 
  
    @Id
    private String token;
    
    @TimeToLive
    private Long expiration;

    public BlacklistedToken(String token) {
        this.token = token;
        this.expiration = 15 * 60L;
    }
}
