package com.mindhub.homebanking.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebAuthorization   {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(AntPathRequestMatcher.antMatcher("/index.html")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/web/**")).hasAuthority("CLIENT")
                .requestMatchers(AntPathRequestMatcher.antMatcher("/rest/**")).hasAuthority("ADMIN")
                .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).hasAuthority("ADMIN")
        );

        // Me lo toma como error: cannot find symbol
        // .antMatchers("/index.html", "/", "/web/css/**", "/web/js/**" ).permitAll();
        // .antMatchers("/web/**").hasAuthority("CLIENT");
        // .antMatchers("/rest/**").hasAuthority("ADMIN");
        //  .antMatchers("/**").hasAuthority("ADMIN");

        http.formLogin()
                .usernameParameter("mail")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").logoutSuccessHandler((req,res,auth) -> res.setStatus(HttpServletResponse.SC_OK));

        // turn off checking for CSRF tokens
        http.csrf().disable();
        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        return http.build();
    }


    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
