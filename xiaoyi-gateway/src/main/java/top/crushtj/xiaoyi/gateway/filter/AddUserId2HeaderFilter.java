package top.crushtj.xiaoyi.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ayi
 * @version V1.0
 * @title AddUserId2HeaderFilter
 * @date 2026/2/4 15:28
 * @description 添加用户ID到请求头过滤器
 */

@Slf4j
@Component
public class AddUserId2HeaderFilter implements GlobalFilter {

    private static final String HEADER_USER_ID = "userId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Long userId = null;
        try {
            userId = StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            return chain.filter(exchange);
        }
        Long finalUserId = userId;
        ServerWebExchange newExchange = exchange.mutate()
            .request(builder -> builder.header(HEADER_USER_ID, String.valueOf(finalUserId)))
            .build();

        return chain.filter(newExchange);
    }
}
