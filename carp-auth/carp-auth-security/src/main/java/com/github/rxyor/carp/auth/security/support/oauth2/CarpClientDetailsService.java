package com.github.rxyor.carp.auth.security.support.oauth2;

import javax.sql.DataSource;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
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

    private static final String CLIENT_TABLE_NAME = "oauth_client_details";

    private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
        + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
        + "refresh_token_validity, additional_information, autoapprove";

    private static final String CLIENT_FIELDS =
        "CONCAT('{noop}',client_secret) as client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
        + " from " + CLIENT_TABLE_NAME;

    public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    public static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    public static final String DEFAULT_INSERT_STATEMENT = "insert into " + CLIENT_TABLE_NAME + " (" + CLIENT_FIELDS
        + ", client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String DEFAULT_UPDATE_STATEMENT = "update " + CLIENT_TABLE_NAME + " " + "set "
        + CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

    public static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update " + CLIENT_TABLE_NAME + " "
        + "set client_secret = ? where client_id = ?";

    public static final String DEFAULT_DELETE_STATEMENT = "delete from " + CLIENT_TABLE_NAME + " where client_id = ?";

    public CarpClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     *重写使其支持redis cache
     *
     * @author liuyang
     * @date 2019-03-01 Fri 18:38:59
     * @param clientId 客户端ID
     * @return ClientDetails
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }
}
