package jp.korenani.korenani;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
	
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KorenaniApplication {

	public static void main(String[] args) {
		SpringApplication.run(KorenaniApplication.class, args);
	}
	
	// this is used for recaptcha
	@Bean 
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
//	@Bean  
//    public TomcatServletWebServerFactory tomcatEmbedded() {  
//
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();  
//
//        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {  
//            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {  
//                //-1 means unlimited  
//                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(100000000);  
//                
//                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSavePostSize(100000000);
//            }  
//        });  
//
//        return tomcat;  
//    }
//	
	
	
}
//let the user create a username so that the username is shown instead of email, because email can be exploited by other users
// ASK USER to enter a password after oauth because having a password in necessary
// if he has logged in using oauth then no need to varify his email, if not then email needs to be verified to check if it is a real email or not