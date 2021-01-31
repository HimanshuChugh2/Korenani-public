package jp.korenani.korenani.repository;

 

 
 import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.SignUpUserDetails;

@Repository
@Transactional
public interface PasswordReposoitory extends JpaRepository<SignUpUserDetails,Integer>  {

	@Query(value="select password from sign_up where username = :username", nativeQuery = true)
	public String isPasswordValid(@Param("username") String password);	
	
	
	
	
}
