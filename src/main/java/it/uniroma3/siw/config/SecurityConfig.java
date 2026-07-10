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

        http
            .authorizeHttpRequests(auth -> auth

                /*
                 * AREA AMMINISTRATIVA
                 */

                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                /*
                 * API RECENSIONI
                 *
                 * GET: pubblica, perché tutti possono leggere le recensioni.
                 * POST: riservata agli utenti autenticati.
                 */

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/books/*/reviews"
                )
                    .permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/books/*/reviews"
                )
                    .authenticated()

                /*
                 * FUNZIONI RISERVATE A UTENTI AUTENTICATI
                 */

                .requestMatchers(
                    "/books/*/loans/new",
                    "/books/*/loans",
                    "/loans/my"
                )
                    .authenticated()

                /*
                 * PAGINE PUBBLICHE
                 */

                .requestMatchers(
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
                )
                    .permitAll()

                /*
                 * Tutto ciò che non è stato dichiarato sopra
                 * richiede l'autenticazione.
                 */

                .anyRequest()
                    .authenticated()
            )

            .formLogin(login -> login
                .loginPage("/login")

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
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}