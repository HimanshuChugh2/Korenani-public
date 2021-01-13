package jp.korenani.korenani.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.korenani.korenani.entities.AnswerClapCountAndAidWrapper;
import jp.korenani.korenani.entities.AnswerDatetimeAndAidWrapperInterface;
import jp.korenani.korenani.entities.AnswerDislikesCountAndAidWrapper;
import jp.korenani.korenani.entities.ClapsOfAnswer;
import jp.korenani.korenani.entities.DislikesDatetimeAndAidWrapperInterface;
import jp.korenani.korenani.entities.DislikesOfAnswer;

@Repository
public interface DislikesAnswersRepository extends JpaRepository<DislikesOfAnswer, Integer>{

	@Query(value="select exists (select * from korenani.dislikes_of_answer  where  aid=:aid);",nativeQuery = true)
	public Integer isDislikesAlreayExistingForAnswer(@Param("aid") Integer aid);
	
	@Query(value = "update korenani.dislikes_of_answer set dislikes = dislikes+1 where aid= :aid" ,nativeQuery = true)
	@Transactional
	@Modifying
	public void updateDislikesByAid(@Param("aid") Integer aid);
	
	@Query(value="select *from korenani.dislikes_of_answer where aid=:aid",nativeQuery = true)
	public List<DislikesOfAnswer> getByAid(@Param("aid") Integer aid);  
	
	@Query(value = "update korenani.dislikes_of_answer set dislikes = dislikes+1, datetime= :datetime where aid= :aid and userprofilename= :userprofilename" ,nativeQuery = true)
	@Transactional
	@Modifying
	public void updateDislikesByAid(@Param("aid") Integer qid, @Param("userprofilename") String userprofilename, @Param("datetime") String datetime);
	
	
	@Query(value="select datetime from korenani.dislikes_of_answer where aid= :aid and userprofilename= :userprofilename",nativeQuery = true)
	public String getDatetimeOfAnswerDislikesByUserPrifileNameAndAid(@Param("aid") Integer aid, @Param("userprofilename") String userprofilename);
	
	
	@Query(value="	select sum(dislikes) as dislikescount, aid from korenani.dislikes_of_answer where aid in :listofaid group by aid",nativeQuery = true)
	public List<AnswerDislikesCountAndAidWrapper> getDislikesCountAidByAid(@Param("listofaid") List<Integer> aid);
	
	
	@Query(value="	select datetime,aid from korenani.dislikes_of_answer where aid in :listofaid and userprofilename = :userprofilename",nativeQuery = true)
	public List<AnswerDatetimeAndAidWrapperInterface> getDatetimeAndAidByAidListAndUserProfileName(@Param("listofaid") List<Integer> aid, @Param("userprofilename") String userprofilename);
	
	
	
}
