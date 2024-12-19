package com.innovation.warm.security.filter;

import com.innovation.warm.constant.Constants;
import com.innovation.warm.pojo.dto.SysUserDetails;
import com.innovation.warm.properties.JwtProperties;
import com.innovation.warm.util.SecurityUtils;
import com.innovation.warm.util.jwt.SystemUserTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ClassName: JwtAuthenticationTokenFilter
 * PackageName: com.innovation.warm.security.filter
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/16 下午7:55
 * @Version: 1.0
 */
// 配置 》 拦截器的配置
// 在 Spring Security 的配置中，如果你已经在 authorizeHttpRequests 中为某些路径配置了 permitAll()，那么这些路径将会被放行，无论你在自定义过滤器中是否做了其它的处理。
//
//具体来说，你的配置代码中明确指出了一些路径（例如 /member/auth/login、/admin/auth/login 等）是允许所有用户访问的。这意味着这些请求会被直接放行，跳过身份验证的步骤。
//
//即使在自定义的过滤器（如 JwtAuthenticationTokenFilter）中，你试图对这些请求进行处理，由于它们已经在安全配置中被标记为 permitAll()，Spring Security 会优先遵循这个配置，允许请求通过。
//
//简而言之，放行路径的配置会高于自定义过滤器的处理逻辑，所有已配置为 permitAll() 的请求都不会受到任何身份验证的限制。
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private SystemUserTokenUtil systemUserTokenUtil;
    // 前端传递的token的 key是 Authorization  但是value  要再后端返回的token的时候加上前缀
    // 如果是总的后台管理系统 就加上Sys_
    // 如果是商家后台管理系统就添加上Merchant_
    @Value("${warm.jwt.headTokenKey}")
    private  String headler;
    // 对于登录请求（如 /member/auth/login 和 /admin/auth/login），即使没有携带 token，这些请求也会被放行，因为它们被配置为 permitAll()。因此，自定义过滤器 JwtAuthenticationTokenFilter 会执行，但不会阻止这些请求。
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(headler);
        // 用请求头的token 来标识这个用户时那个 类型的用户 时总的后台用户 还是商家的后台用户
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_SYS_PREFIX)) {
            SysUserDetails sysUserDetails =  systemUserTokenUtil.getSysLoginUser(request);
            // 理论上这个不可能时null  从别的地方取过来的还是盘一下为好 但是为了健壮性 还是盘一下
            if (sysUserDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(sysUserDetails, null, sysUserDetails.getAuthorities());
                SecurityUtils.getSecurityHolder().setAuthentication(authenticationToken);
            }
        }
        // 标识这个时一个 商家登录的
        else if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_MEM_PREFIX)) {

        }
        // 后面还可以做 拓展

        //如果在您的自定义过滤器中没有对未携带 token 的请求进行处理并且直接调用了 chain.doFilter(request, response);，那么后续的 UsernamePasswordAuthenticationFilter 将会执行
        // 请求无 Token：如果请求不携带 token，自定义过滤器可以继续放行请求到 UsernamePasswordAuthenticationFilter。但由于请求没有有效的身份验证信息，这个过滤器会发现请求没有包含有效的身份验证信息，通常会返回一个 401 Unauthorized 的响应。
        // UsernamePasswordAuthenticationFilter 的逻辑：实际上，UsernamePasswordAuthenticationFilter 通常是用于处理基于用户名和密码的登录请求，如果请求未提供有效的 token 或者未通过身份验证(如果登录成功就会把 UsernamePasswordAuthenticationToken authenticationToken =
        //                        new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()); 标识用户有效的身份验证信息  再UsernamePasswordAuthenticationFilter会放行 但是如果时非法的请求token时空的话 没有这个有效的身份验证信息 抛出异常)，最终也会导致用户未被认证。
        // 保护路径：如果 UsernamePasswordAuthenticationFilter 的配置是针对特定路径（如登录路径），而非法请求当然不在这些路径内，它就会被保护，看起来像是认证不通过。
        // 放行 交给后面的UsernamePasswordAuthenticationFilter 处理  如果没有登录 就会杯拦截
        filterChain.doFilter(request, response);
    }

}
