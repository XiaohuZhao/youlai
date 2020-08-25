package com.youlai.gateway.filter;

import com.youlai.common.constant.AuthConstant;
import com.youlai.gateway.config.WhiteUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 白名单路径移除JWT请求头
 */
@Component
public class WhiteUrlsRemoveJwtFilter implements WebFilter {

    @Autowired
    private WhiteUrlsConfig whiteUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
        URI uri=request.getURI();
        PathMatcher pathMatcher=new AntPathMatcher();

        //白名单路径移除JWT请求头
        List<String> ignoreUrls = whiteUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                request = exchange.getRequest().mutate().header(AuthConstant.JWT_TOKEN_HEADER, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
