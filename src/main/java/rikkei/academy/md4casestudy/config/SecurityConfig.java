package rikkei.academy.md4casestudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rikkei.academy.md4casestudy.security.jwt.JwtEntryPoint;
import rikkei.academy.md4casestudy.security.jwt.JwtTokenFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.*;
import static rikkei.academy.md4casestudy.model.RoleName.ADMIN;
import static rikkei.academy.md4casestudy.model.RoleName.PM;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEntryPoint jwtEntryPoint() {
        return new JwtEntryPoint();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))

                .authorizeRequests(auth -> auth
                        .antMatchers(HttpMethod.GET, "/api/manager/**").permitAll()
                        .antMatchers("/api/manager/**").hasAnyRole(ADMIN.name(), PM.name())
                        .anyRequest().permitAll()
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtEntryPoint()))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }


//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .antMatchers("/api/auth/**");
//    }

}
