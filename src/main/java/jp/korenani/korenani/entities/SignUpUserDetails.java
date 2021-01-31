package jp.korenani.korenani.entities;

 

 import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Email;
 import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

 
 
@Entity
@Table(name= "sign_up")
public class SignUpUserDetails implements UserDetails {
	 
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;

//  		@Pattern(regexp= "/[一-龠]+|[ぁ-ゔ]+|[ァ-ヴー]+|[a-zA-Z ]+|[ａ-ｚＡ-Ｚ ]+|[々〆〤]+/u",message = "-> It must be an appropriate name. "  )
		//i have added whitespace afterA-Z to let it take white space between words

		@Size(min = 1,max = 50,message= "-> User Profile Name must be greater than 1 character")
  		@Pattern(regexp= "^[\\$#\\+{}:\\?\\.\\_,~\"a-zA-Z0-9]+$",message = "-> It must be an appropriate name. \n\n <br/> <hr/> It should not contain whitespace.\r\n Special charaters like 'period' and 'underscore' are only allowed")
		private String userProfileName;
		 
		private String name;
		
		@Lob
		@Type(type = "org.hibernate.type.TextType")
		private String userhomepagedata;
		
		
		
 		 

		public String getUserhomepagedata() {
			return userhomepagedata;
		}

		public void setUserhomepagedata(String userhomepagedata) {
			this.userhomepagedata = userhomepagedata;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

  
		public String getUserProfileName() {
			return userProfileName;
		}

		public void setUserProfileName(String userProfileName) {
			this.userProfileName = userProfileName;
		}

 
		public SignUpUserDetails(int id,
				@Size(min = 1, max = 50, message = "-> User Profile Name must be greater than 1 character") @Pattern(regexp = "^[\\$#\\+{}:\\?\\.\\_,~\"a-zA-Z0-9]+$", message = "-> It must be an appropriate name. \n\n <br/> <hr/> It should not contain whitespace.\r\n Special charaters like 'period' and 'underscore' are only allowed") String userProfileName,
				String name, String userhomepagedata, @Size(min = 3, max = 30) String password,
				@NotBlank @Size(min = 5, max = 50, message = "-> Name must be greater than 3 characters ") @Email String username,
				boolean emailverified) {
			super();
			this.id = id;
			this.userProfileName = userProfileName;
			this.name = name;
			this.userhomepagedata = userhomepagedata;
			this.password = password;
			this.username = username;
			this.emailverified = emailverified;
		}


		@Size(min = 3,max = 30)
	    private String password;
 
 		@NotBlank 
 		@Size(min = 5,max = 50,message= "-> Name must be greater than 3 characters ")
  		@Email
  		private String username;
 		
 		
 		private boolean emailverified;
 		
 	 
 		
		public boolean isEmailverified() {
			return emailverified;
		}

		public void setEmailverified(boolean emailverified) {
			this.emailverified = emailverified;
		}

 

		public void setFullname(String userProfileName) {
			this.userProfileName = userProfileName;
		}




 
		
		
		
	/*
	 * private int workingHours; private int commuteTime; private int workoutTime;
	 * private int mealTime;
	 * 
	 * private int phoneTime; private int sleepTime;
	 */
 	    
 	    
 	   public SignUpUserDetails() {
 			super();
  		}
 	    
 		
 	    
 			
		 

		public int getId() {
			
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
 
		 
		public void setPassword(String password) {
			this.password = password;
		}
		 
		 
		public void setUsername(String username) {
			this.username = username;
		}
		
		
		 
	    
	    
		public SignUpUserDetails(SignUpUserDetails signUpUserDetails) {
	        this.username = signUpUserDetails.getUsername();
	        this.password = signUpUserDetails.getPassword();
	        this.userProfileName=signUpUserDetails.getUserProfileName();
	        
	       // this.active = user.isActive();
		/*
		 * this.authorities = Arrays.stream(user.getRoles().split(","))
		 * .map(SimpleGrantedAuthority::new) .collect(Collectors.toList());
		 */
	    }
	    
	    
	    
	    
	    
	    
	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() { //
	 * TODO Auto-generated method stub return authorities; }
	 */



@Override
public String getPassword() {
	// TODO Auto-generated method stub
	return password;
}
@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return username;
}
@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}





@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return null;
}
	    
	    
		
		
		}