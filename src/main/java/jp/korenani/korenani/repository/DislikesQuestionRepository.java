package jp.korenani.korenani.repository; 
import javax.transaction.Transactional; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.DislikesOfQuestion;

@Repository
public interface DislikesQuestionRepository extends JpaRepository<DislikesOfQuestion, Integer> {
	
	@Query(value="select exists (select * from korenani.dislikes_of_question  where  qid=:qid and userprofilename= :userprofilename);",nativeQuery = true)
	public Integer isDislikesAlreayExistingForQuestion(@Param("qid") Integer qid, @Param("userprofilename") String userprofilename);
	
	
	@Query(value = "update korenani.dislikes_of_question set dislikes = dislikes+1, datetime= :datetime where qid= :qid and userprofilename= :userprofilename" ,nativeQuery = true)
	@Transactional
	@Modifying
	public void updateDislikesByQid(@Param("qid") Integer qid, @Param("userprofilename") String userprofilename, @Param("datetime") String datetime);
	
	
	@Query(value="select sum(dislikes) from korenani.dislikes_of_question where qid= :qid",nativeQuery = true)
	public Integer getDislikesCountByQid(@Param("qid") Integer qid);
	
	
	@Query(value="select datetime from korenani.dislikes_of_question where qid= :qid and userprofilename= :userprofilename",nativeQuery = true)
	public String getDatetimeOfQuestionDislikesByUserPriflieNameAndQid(@Param("qid") Integer qid, @Param("userprofilename") String userprofilename);
	
}
