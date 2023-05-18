package citsk.ru.vipnet.config;

import citsk.ru.vipnet.security.AuthenticationManager;
import citsk.ru.vipnet.security.BearerTokenAuthenticationConverter;
import citsk.ru.vipnet.security.JwtHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    private final String[] publicRoutes = {"/api/v1/auth/login", "/api/v1" +
            "/auth/register"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) {
        return http.csrf().disable()
                   .authorizeExchange()
                   .pathMatchers(HttpMethod.OPTIONS)
                   .permitAll()
                   .pathMatchers(publicRoutes)
                   .permitAll()
                   .anyExchange()
                   .authenticated()
                   .and()
                   .exceptionHandling()
                   .authenticationEntryPoint((exchange, exception) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                   .accessDeniedHandler(((exchange, denied) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN))))
                   .and()
                   .addFilterAt(bearerAuthenticationWebFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private AuthenticationWebFilter bearerAuthenticationWebFilter(AuthenticationManager authenticationManager) {
        var bearerAuthenticationFilter =
                new AuthenticationWebFilter(authenticationManager);

        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenAuthenticationConverter(new JwtHandler(secret)));
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

        return bearerAuthenticationFilter;
    }
}
