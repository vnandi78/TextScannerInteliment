package com.inteliment.security;


//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
public class SecurityConfig{} 


/*extends WebSecurityConfigurerAdapter{
	
	private static String USER = "USER";
	private static String ADMIN = "ADMIN";
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.authorizeRequests()
					.antMatchers("/counter-api/search/*").hasRole(USER)
					.antMatchers("/counter-api/top/*").hasRole(USER)
					.and()
					.formLogin();
					
		
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authbuilder) throws Exception{
		
		authbuilder.inMemoryAuthentication()
					.withUser("vnandi").password("password").roles(USER)
					.and()
					.withUser("henry").password("password").roles("USER", ADMIN);
	}

}
*/