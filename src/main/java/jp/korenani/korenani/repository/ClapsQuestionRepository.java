package jp.korenani.korenani.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.ClapsOfQuestion;

@Repository
public interface ClapsQuestionRepository  extends JpaRepository<ClapsOfQuestion, Integer> {

	@Query(value="select exists (select * from korenani.claps_of_question  where  qid=:qid and userprofilename= :userprofilename);",nativeQuery = true)
	public Integer isClapsAlreayExistingForQuestion(@Param("qid") Integer qid, @Param("userprofilename") String userprofilename);
	
	
	@Query(value = "update korenani.claps_of_question set claps = claps+1, datetime= :datetime where qid= :qid and userprofilename= :userprofilename" ,nativeQuery = true)
	@Transactional
	@Modifying
	public void updateClapsByQid(@Param("qid") Integer qid, @Param("userprofilename") String userprofilename, @Param("datetime") String datetime);
	
	
	@Query(value="select sum(claps) from korenani.claps_of_question where qid= :qid",nativeQuery = true)
	public Integer getClapCountByQid(@Param("qid") Integer qid);
	
	
	@Query(value="select datetime from korenani.claps_of_question where qid= :qid and userprofilename= :userprofilename",nativeQuery = true)
	public String getDatetimeOfQuestionClapByUserProfileNameAndQid(@Param("qid") Integer qid, @Param("userprofilename") String userprofilename);
	
	
 
}
