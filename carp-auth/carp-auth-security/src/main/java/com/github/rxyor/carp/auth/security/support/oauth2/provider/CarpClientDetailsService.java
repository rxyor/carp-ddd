package com.github.rxyor.carp.auth.security.support.oauth2.provider;

import com.github.rxyor.spring.boot.cacheablettl.CacheableTtl;
import javax.sql.DataSource;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/3/1 Fri 18:24:00
 * @since 1.0.0
 */
public class CarpClientDetailsService extends JdbcClientDetailsService {

    private RedissonClient redissonClient;

    public CarpClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    public CarpClientDetailsService(DataSource dataSource, RedissonClient redissonClient) {
        super(dataSource);
        this.redissonClient = redissonClient;
    }

    /**
     *重写使其支持redis cache
     *
     * @author liuyang
     * @date 2019-03-01 Fri 18:38:59
     * @param clientId 客户端ID
     * @return ClientDetails
     */
    @CacheableTtl(cacheNames = "CarpClientDetailsService", key = "#clientId", ttl = 86400, unless = "#result==null")
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }

    @CacheEvict(cacheNames = "CarpClientDetailsService", key = "#clientDetails.clientId")
    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
    }

    @CacheEvict(cacheNames = "CarpClientDetailsService", key = "#clientId")
    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
    }

    @CacheEvict(cacheNames = "CarpClientDetailsService", key = "#clientId")
    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
    }
}
