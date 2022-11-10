package com.noithat.config;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.noithat.entity.Account;
import com.noithat.service.AccountService;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AccountService accountService;
	

//	@Autowired
//	BCryptPasswordEncoder pe;

	// Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Account user = accountService.findByUsername(username);
				
				String password = user.getPassword();
//				String password = pe.encode(user.getPassword());
				String[] roles = user.getAuthorities().stream()
						.map(er -> er.getRole().getId())
						.collect(Collectors.toList())
						.toArray(new String[0]);
				return User.withUsername(username).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + "not found");
			}
		});
	}

	// Phân quyền sử dụng
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable();

		http.authorizeRequests()
			.antMatchers("/checkout/**").authenticated()
			.antMatchers("/order/**").authenticated()
			.antMatchers("/assets/admin/**").hasAnyRole("ADMIN", "STAF")
			.antMatchers("/user/**").hasAnyRole("ADMIN", "STAF","USER")
			.antMatchers("/rest/roles").hasRole("ADMIN")
			.anyRequest().permitAll();

		http.formLogin().loginPage("/security/login")
			.loginProcessingUrl("/security/login")
				.defaultSuccessUrl("/security/login/success", false)
				.failureUrl("/security/login/error");

		http.rememberMe().tokenValiditySeconds(86400);

		http.exceptionHandling().accessDeniedPage("/security/unauthoried");

		http.logout()
		.logoutUrl("/security/logoff")
		.logoutSuccessUrl("/security/logoff/success");
		
	
		// Oauth2
		http.oauth2Login()
		    .loginPage("/security/login")
		    .defaultSuccessUrl("/oauth2/login/success", true)
		    .failureUrl("/security/login/error")
		    .authorizationEndpoint()
		    .baseUri("/oauth2/authorization");
	}

	// Cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
