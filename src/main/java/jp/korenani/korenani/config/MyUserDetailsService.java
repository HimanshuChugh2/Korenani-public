package jp.korenani.korenani.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.korenani.korenani.entities.SignUpUserDetails;
 import jp.korenani.korenani.repository.JPASignUpRepository;

 @Service
   public class MyUserDetailsService implements UserDetailsService {

 	@Autowired
 	JPASignUpRepository jpaSignUpRepository;
  	
 	
	@Override
     // whatever we pass in the username field in login page, will come in this userName argument
     public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
   		System.out.println(" 0 0 0 0  L O A D B Y u S E R NA ME ");
     	Optional<SignUpUserDetails> user = jpaSignUpRepository.findByUsername(userName);
      	System.out.println(userName);
          // user will contain object of class user and we have to put it in UserDetail
          user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
         
 //here we are calling that constructer method to get the values here (MyUserDetails class is same as SignUp entity) this constructor method will convert the object to appropriate UserDetails object
      

         return user.map(SignUpUserDetails::new).get();
     }
 	
	/*
	 * @Bean public PasswordEncoder passwordencoder() {return NoOpPasswordEncoder.
	 * getInstance();
	 * 
	 * }
	 */
     
  
	
    @Bean 
 	public PasswordEncoder passwordencoder()	{return NoOpPasswordEncoder.	getInstance();
 	
 	}
 
 }

	
	
	 
 