package jp.korenani.korenani.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
 

import jp.korenani.korenani.repository.JPASignUpRepository;


@Configuration
@EnableWebSecurity
@ComponentScan 
 public class ConfigurationClass extends WebSecurityConfigurerAdapter{
//	 @Autowired
//	    private MyUserDetailsService userService;
//	@Autowired
//	UserDetailsService userDetailsService;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception 	{
//	auth.userDetailsService(userDetailsService);
//	}
	
	
	@Autowired
	JPASignUpRepository jpasignuprepository;
	
	@Resource
	UserDetailsService userDetailsService;
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	// @formatter:off
 
		/**/
		 /*
				 * .csrf(c ->
				 * c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				 */
	        
	        
		 http.cors().and().csrf().disable()
	        
				/*
				 * .logout(l -> l .logoutSuccessUrl("/").permitAll() )
				 */
				/*
				 * .authorizeRequests(a -> a .antMatchers("/", "/error",
				 * "/webjars/**","/sign-up","/index","/registration").permitAll()
				 * 
				 * .anyRequest().authenticated() .and() ) .exceptionHandling(e -> e
				 * .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				 * )
				 */
	        
	        
	        .logout(l -> l
	                .logoutSuccessUrl("/").permitAll()
	             )
            .authorizeRequests(a -> a
            		.antMatchers("/ask-question").hasAnyRole("USER","ADMIN")
            		.antMatchers("/creators").hasAnyRole("ADMIN")
            		.antMatchers("/", "/error", "/webjars/**","/error-pages/**","/signup","/user", "/accessible","/enter-existing-password","/showit","/css/style.css","/index").permitAll()
                
                
                
            )
				/* 
				 * .exceptionHandling(e -> e .authenticationEntryPoint(new
				 * HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) )
				 */ 
				.formLogin().defaultSuccessUrl("/restricted").loginPage("/login")
			     .and()
				.rememberMe()
			      .key("rem-me-key")
			      .rememberMeParameter("remember") // it is name of checkbox at login page  
			      .rememberMeCookieName("rememberlogin") // it is name of the cookie  
			      .tokenValiditySeconds(1000000000) // remember for number of seconds  
				 .and().oauth2Login().
				 defaultSuccessUrl("/enter-password", true).loginPage("/login")
				 .permitAll()
				 .and()
		            .logout().deleteCookies("JSESSIONID")
		            .and()
		            .rememberMe().userDetailsService(userDetailsService);
		            //.tokenValiditySeconds(100) // remember for number of seconds 
				 ;
        // @formatter:on
    }
	        
	       /* .authorizeRequests()
	        .antMatchers("/login","/","/registration").permitAll()
	        .anyRequest().authenticated()
            .and()
            .oauth2Login().permitAll().defaultSuccessUrl("/restricted",true) .and().logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .permitAll();*/
	        
	 	 
					
	           
	        // @formatter:on
	    
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers("/resources/**", "/static/**", "/css/style.css", "/javascript/**", "/images/**","/js/**","/scss/**", "/sketch/**", "/vendors/**","/css/**","/fonts/**","/images/**","/icon/**,/upload/**,/anyfolder/**,/javascript/QuillContainer.js,/path/to/font-awesome/**");
	}
	
	/*
	 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 * 
	 * 
	 * 
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
	 * auth.setUserDetailsService(userService);
	 * auth.setPasswordEncoder(passwordEncoder()); return auth; }
	 * 
	 * 
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.authenticationProvider(authenticationProvider()); }
	 */
 
 


}
	

