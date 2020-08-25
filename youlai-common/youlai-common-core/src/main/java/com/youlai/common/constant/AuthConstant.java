package com.youlai.common.constant;

public interface AuthConstant {

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";


    /**
     * 用户信息HTTP请求头
     */
    String USER_TOKEN_HEADER = "user";


    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-client";


    /**
     * 前台client_id
     */
    String PORTAL_CLIENT_ID="portal-client";


    /**
     * 小程序client_id
     */
    String MP_CLIENT_ID="mp-client";


    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN="/youlai-admin/**";

}
