package br.com.labhotel.hotel_api.config;

// Importações necessárias
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration; // Importe este
import org.springframework.web.cors.CorsConfigurationSource; // Importe este
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Importe este
import java.util.List; // Importe este

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                
                // *** ADICIONE ESTA LINHA PARA ATIVAR O CORS ***
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // Vamos também libertar todos os pedidos por agora
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todos os pedidos
                );
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Aceita de qualquer domínio
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Aceita estes métodos HTTP
        configuration.setAllowedHeaders(List.of("*")); // Aceita quaisquer cabeçalhos
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica estas regras a TODOS os endpoints
        return source;
    }
}