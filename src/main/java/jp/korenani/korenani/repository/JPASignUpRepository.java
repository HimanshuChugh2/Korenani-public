package jp.korenani.korenani.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.SignUpUserDetails;

 
@Repository
@Transactional
public interface JPASignUpRepository extends JpaRepository<SignUpUserDetails,Integer> {
	
	Optional<SignUpUserDetails> findByUsername(String username);
	
	
	@Query(value="select exists (select user_profile_name from korenani.sign_up where user_profile_name= :user_profile_name);" ,nativeQuery = true)
	public Integer isUserProfileNameExisting(@Param("user_profile_name") String user_profile_name);
	 
	@Query(value = "select user_profile_name from korenani.sign_up where username = :username", nativeQuery = true)
	public Optional<String> getUserProfileNameByUsername(@Param("username") String username);
	
 
	
	@Transactional
	@Modifying
	@Query(value = "update sign_up set password= :password where username= :username",nativeQuery = true)
	public void updateUsernameByUsername(@Param("password") String password, @Param("username") String username);

	

	@Transactional
	@Modifying
	@Query(value = "update sign_up set user_profile_name = :user_profile_name where id= :id",nativeQuery = true)
	public void updateUserProfileNameById(@Param("user_profile_name") String user_profile_name, @Param("id") int id);

	
	//if username that i have created by removing letters after@ already exists then use the id of user as his default username
	
	
		@Query(value = "select * from sign_up where user_profile_name = :user_profile_name",nativeQuery = true)
		public SignUpUserDetails getUserByUsername(@Param("user_profile_name") String user_profile_name);
	
	
	@Transactional
	@Modifying
	@Query(value = "update sign_up set user_profile_name = :user_profile_name, name= :name where username= :email",nativeQuery = true)
	public void updateUserDetailsByEmail(@Param("user_profile_name") String user_profile_name,@Param("name")  String name, @Param("email") String email );
	
	
	@Transactional
	@Modifying
	@Query(value = "update sign_up set user_profile_name = :user_profile_name, name= :name, userhomepagedata= :userhomepagedata where username= :email",nativeQuery = true)
	public void updateAllUserDetailsByEmail(@Param("user_profile_name") String user_profile_name,@Param("name")  String name,@Param("userhomepagedata") String userhomepagedata, @Param("email") String email );
	 
	
	

}
