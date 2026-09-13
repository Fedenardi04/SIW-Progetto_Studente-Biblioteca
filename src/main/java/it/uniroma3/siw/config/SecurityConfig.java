package it.uniroma3.siw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers(
                    HttpMethod.GET,
                    "/api/books/*/reviews"
            )
            .permitAll()

            .requestMatchers(
                HttpMethod.POST,
                "/api/books/*/reviews"
            )
            .authenticated().requestMatchers(
                    "/books/*/loans/new",
                    "/books/*/loans",
                    "/loans/my"
                ).authenticated().requestMatchers(
                    "/",
                    "/books",
                    "/books/**",
                    "/authors",
                    "/authors/**",
                    "/login",
                    "/register",
                    "/js/**",
                    "/css/**",
                    "/images/**",
                    "/favicon.ico",
                    "/error"
                ).permitAll().anyRequest().authenticated()).formLogin(login -> login.loginPage("/login")
                .successHandler((request, response, authentication) -> {

                    boolean isAdmin = authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                            authority.getAuthority().equals("ROLE_ADMIN")
                        );

                    if (isAdmin) {
                        response.sendRedirect("/admin");
                    } else {
                        response.sendRedirect("/");
                    }
                })

                .failureUrl("/login?error")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}