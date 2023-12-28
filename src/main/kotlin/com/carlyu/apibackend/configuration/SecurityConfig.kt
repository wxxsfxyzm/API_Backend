package com.carlyu.apibackend.configuration

import com.carlyu.apibackend.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenService: TokenService,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Define public and private routes
        http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
            it.requestMatchers(HttpMethod.POST, "/api/register").permitAll()
            it.requestMatchers("/api/**").authenticated()
            it.anyRequest().permitAll() // In case you have a frontend
        }
        http.logout {
            it.logoutUrl("/api/logout")
            it.logoutSuccessUrl("/api/logout_success").permitAll()
            //TODO: Saved for further use
            //it.logoutSuccessUrl("/api/login").permitAll()
        }

        // Configure JWT
        http.oauth2ResourceServer {
            it.jwt { jwtConfigurer ->
                jwtConfigurer.authenticationManager { auth ->
                    val jwt = auth as BearerTokenAuthenticationToken
                    val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
                    UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority("USER")))

                }
            }
        }

        // Other configuration
        http.cors(withDefaults())
        http.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        }
        http.csrf { it.disable() }
        http.headers { it ->
            it.frameOptions {
                it.disable()

            }
            it.xssProtection {
                it.disable()
            }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}