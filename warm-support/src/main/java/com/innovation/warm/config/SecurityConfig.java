package com.innovation.warm.config;

import com.innovation.warm.security.filter.JwtAuthenticationTokenFilter;
import com.innovation.warm.service.impl.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * ClassName: SecurityConfig
 * PackageName: com.innovation.warm.config
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/13 下午11:13
 * @Version: 1.0
 */
@Configuration
public class SecurityConfig {
    @Autowired
    private SysUserDetailsService sysUserDetailsService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        // 禁用HTTP响应标头
        http.headers(header -> header.cacheControl(Customizer.withDefaults()).frameOptions(Customizer.withDefaults()));
        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers("/member/user/login", "/doc.html", "/sys/user/login").permitAll()
                    .anyRequest().permitAll();
        });
        // 基于token，所以不需要session
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 开启form认证
        // 主要作用就是为了把 UsernamePasswordAuthenticationFilter 注入到ioc中
        // Spring Security 配置中使用 formLogin 方法时，Spring Security 会自动将 UsernamePasswordAuthenticationFilter 注入到 IoC 容器中，并配置相应的过滤器链。这个过滤器负责处理表单登录请求，验证用户名和密码，并进行身份认证。 但是如果使用token 要自定拦截器
        // 如果你使用的是基于 token 的认证方式（如 JWT），通常不需要 UsernamePasswordAuthenticationFilter，而是使用自定义的过滤器来处理认证逻辑
        http.formLogin(Customizer.withDefaults());
        // 配置跨域
        http.cors(cors -> cors.configurationSource(configurationSource()));
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(sysUserDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 配置跨域，允许跨域 配置CorsConfigurationSource
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));

        // 创建 CorsConfigurationSource对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
