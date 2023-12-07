package com.grocery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@EnableWebSecurity
@Configuration
public class WebFilter {

	@Autowired
	private DefaultWebSecurityExpressionHandler webSecurityExpressionHandler;

//	@SuppressWarnings({ "removal", "deprecation" })
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().expressionHandler(webSecurityExpressionHandler)
				.requestMatchers(HttpMethod.POST, "/grocery/v1/add").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/grocery/v1/items").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/grocery/v1/remove/").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/grocery/v1/update").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/grocery/v1/inventory").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/grocery/v1/order").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/grocery/v1/items").hasRole("USER");
		return http.build();
	}
}
