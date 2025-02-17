package net.careerboard.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.careerboard.security.jwt.JwtAuthenticationFilter;
import net.careerboard.security.jwt.JwtUtil;
import net.careerboard.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Value("${production.domain}")
    private String productionDomain;

    @Value("${environment}")
    private String environment;

    // 1. Enable CSRF Protection with Cookie-based Token Storage
    @Bean
    public CookieCsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse(); // Allow Angular to read the token
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, customUserDetailService);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Enable CSRF protection using defaults
                .csrf(Customizer.withDefaults())

                // 3. Force session creation (so JSESSIONID is always available)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                // 4. Exception handling
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Unauthorized\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Access Denied: Insufficient permissions\"}");
                        })
                )

               
                .authorizeHttpRequests(auth -> {
                    if (environment.equalsIgnoreCase("DEV")) {
                        auth.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                                "/swagger-resources/**", "/webjars/**").permitAll();
                    }
                    auth
                            .requestMatchers(HttpMethod.POST, "/api/auth/register/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/auth/login/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/post/**").permitAll()
                           // .requestMatchers(HttpMethod.GET, "/api/post/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/auth/csrf/token/**").permitAll()
                            .anyRequest().authenticated();
                })

                // 6. ✅ Add CSRF token explicitly in response headers & cookies
                .addFilterAfter((request, response, chain) -> {
                    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                    if (csrfToken != null) {
                        HttpServletResponse httpServletResponse = (HttpServletResponse) response; // ✅ Cast response
                        httpServletResponse.setHeader("X-CSRF-TOKEN", csrfToken.getToken()); // ✅ Set CSRF token in headers

                        // ✅ Set CSRF token in cookies (so frontend can access it)
                        Cookie csrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
                        csrfCookie.setPath("/");
                        csrfCookie.setSecure(true); // Set to true if using HTTPS
                        csrfCookie.setHttpOnly(false); // Must be false so frontend can read it
                        csrfCookie.setMaxAge(60 * 60); // Expiration time (1 hour)
                        httpServletResponse.addCookie(csrfCookie);
                    }
                    chain.doFilter(request, response);
                }, UsernamePasswordAuthenticationFilter.class)

                // 7. Add JWT Authentication Filter (if using JWT)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                .build();
    }



    // 4. Update CORS Configuration to expose CSRF token
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowedOrigins = Arrays.asList(
                "http://localhost:4200",    // Angular dev server
                "http://localhost:8081",    // Add protocol if missing
                productionDomain
        );
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("XSRF-TOKEN")); // Expose CSRF token to Angular
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}