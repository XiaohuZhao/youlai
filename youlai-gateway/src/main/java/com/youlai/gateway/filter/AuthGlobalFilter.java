package com.youlai.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSObject;
import com.youlai.common.constant.AuthConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 将登录用户的JWT转换成用户信息过滤器
 */
@ComponentScan
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            return chain.filter(exchange);
        }
        token = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        JWSObject jwsObject = JWSObject.parse(token);
        String userStr = jwsObject.getPayload().toString();
        log.info("AuthGlobalFilter#filter，user:{}", userStr);
        ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstant.USER_TOKEN_HEADER, userStr).build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
