package jp.korenani.korenani.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.korenani.korenani.entities.DraftContent;


@Repository
@Transactional
public interface DraftContentRepository extends JpaRepository<DraftContent,Integer> 
{

	List<DraftContent> findByUsername(String username);
	Optional<DraftContent> findByCid(int i);

	@Query(value="select * from draft_content where id = :id", nativeQuery = true)
	public DraftContent getDataByIdQuery(@Param("id") Integer id);	
	
	@Query(value="select topic,description,id from draft_content where username = :username", nativeQuery = true)
	public List<DraftContent> getDraftByUsername(@Param("username") String username);	
	
	@Transactional
	@Modifying
	@Query(value="update draft_content set id = :id , data = :data, description= :description ,keywords=:keywords ,level= :level , topic = :topic, username = :username, cid = :cid where id = :id", nativeQuery = true)
	public void updateDraft(@Param("id") int id, @Param("data") String data, @Param("description") String description, @Param("keywords") String keywords, @Param("level") String level, @Param("topic") String topic, @Param("username") String username,@Param("cid") int cid);

	
	
//	@Modifying		// THIS IS IMPORTANT FOR INSERT QUERY
//	@Transactional  // THIS IS IMPORTANT FOR INSERT QUERY
//	@Query(value="insert into korenani.create_content (data, description, keywords, level, topic, username) values(:data, :description, :keywords, :level, :topic, :username)", nativeQuery = true) 
// 	void saveDetails(@Param("data") JSONObject data, @Param("description") String  description, 
//			  @Param("keywords") String keywords,@Param("level") String level, @Param("topic") String topic ,@Param("username") String username);
	
}
