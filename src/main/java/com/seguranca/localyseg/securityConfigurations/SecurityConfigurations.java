package com.seguranca.localyseg.securityConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.seguranca.localyseg.models.UserRole;

import jakarta.servlet.http.HttpServletResponse;




@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/usuario/teste").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/registro/aluno").permitAll()
						.requestMatchers(HttpMethod.POST, "/pessoa/salvar").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.POST, "/auth/registro/servidor").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.POST, "/auth/registro/adm").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.POST, "/auth/registro/all").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.GET, "/usuario/listServidor").hasAnyAuthority(UserRole.SERVIDOR.getNome())
						.requestMatchers(HttpMethod.GET, "/usuario/listAdm").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.GET, "/usuario/atualizar/adm").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.GET, "/usuario/atualizar/servidor").hasAnyAuthority(UserRole.ADMIN.getNome())
						.requestMatchers(HttpMethod.GET, "/usuario/atualizar/all").hasAnyAuthority(UserRole.ADMIN.getNome())
//						.requestMatchers(HttpMethod.POST, "/epaco/registro").hasAnyAuthority(UserRole.ADMIN.getNome())
						.anyRequest().authenticated()
				)
			  .exceptionHandling(exceptionHandling -> 
                exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                    // Configurando uma resposta JSON para o erro de acesso negado
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Acesso negado ou falha durante a execuçao\"}");
                })
             )
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
		
//		return httpSecurity.cors().and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
//				.requestMatchers(HttpMethod.POST, "/auth/registro").permitAll()
//				.requestMatchers(HttpMethod.POST, "/pessoa/salvar").hasRole(UserRole.ADMIN.getNome())
//                .anyRequest().authenticated().and()
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
////                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
////                .and()
//                .build();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
