package jp.korenani.korenani.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.CreateContent;
import jp.korenani.korenani.entities.CreatedFolder;
import jp.korenani.korenani.entities.DraftContent;
@Repository
public interface CreateContentRepository extends JpaRepository<CreateContent,Integer> 
{

	List<CreateContent> findByUsername(String username);
	
	@Query(value="select * from create_content where id = :id", nativeQuery = true)
	public CreateContent getDataByIdQuery(@Param("id") Integer id);	
	
	@Query(value="select id from create_content where username = :username", nativeQuery = true)
	public List<Integer> getDataByUsername(@Param("username") String username);	
	
	@Query(value="select exists(select * from create_content where username = :username and topic = :topic)", nativeQuery = true)
	public int getTopicByUsername(@Param("username") String username, @Param("topic") String topic);

	@Transactional
	@Modifying
	@Query(value="update korenani.create_content set id = :id , data = :data, description= :description ,keywords=:keywords ,level= :level , topic = :topic, username = :username where id = :id", nativeQuery = true)
	public void updateContentById(@Param("id") int id, @Param("data") String data, @Param("description") String description, @Param("keywords") String keywords, @Param("level") String level, @Param("topic") String topic, @Param("username") String username);
	
	
	@Query(value="select topic, id from korenani.create_content where id in :list", nativeQuery = true)
	public List<Object[]> getTopicById(@Param("list") List<Integer> list);
	
	
	@Query(value="select topic, id from create_content where username = :username", nativeQuery = true)
 	public List<Object[]> getTopicAndIdByUsername(@Param ("username") String username);
 
}
