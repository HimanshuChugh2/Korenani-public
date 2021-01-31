package jp.korenani.korenani.repository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jp.korenani.korenani.entities.AnswerClapCountAndAidWrapper;
import jp.korenani.korenani.entities.AnswerDatetimeAndAidWrapperInterface;
import jp.korenani.korenani.entities.ClapsOfAnswer;

public interface ClapsAnswerRepository extends JpaRepository<ClapsOfAnswer, Integer>  {

	@Query(value="select exists (select * from korenani.claps_of_answer  where  aid=:aid);",nativeQuery = true)
	public Integer isClapsAlreayExistingForAnswer(@Param("aid") Integer aid);
	
	@Query(value = "update korenani.claps_of_answer set claps = claps+1 where aid= :aid" ,nativeQuery = true)
	@Transactional
	@Modifying
	public void updateClapsByAid(@Param("aid") Integer aid);
	
	@Query(value="select *from korenani.claps_of_answer where aid=:aid",nativeQuery = true)
	public List<ClapsOfAnswer> getByAid(@Param("aid") Integer aid);
	
	
	
	@Query(value = "update korenani.claps_of_answer set claps = claps+1, datetime= :datetime where aid= :aid and userprofilename= :userprofilename" ,nativeQuery = true)
	@Transactional
	@Modifying
	public void updateClapsByAid(@Param("aid") Integer qid, @Param("userprofilename") String userprofilename, @Param("datetime") String datetime);
	
	
	@Query(value="select datetime from korenani.claps_of_answer where aid= :aid and userprofilename= :userprofilename",nativeQuery = true)
	public String getDatetimeOfAnswerClapByUserProfilenameAndAid(@Param("aid") Integer aid, @Param("userprofilename") String userprofilename);
	
	
	@Query(value="	select sum(claps) as clapcount, aid from korenani.claps_of_answer 	where aid in :listofaid group by aid",nativeQuery = true)
	public List<AnswerClapCountAndAidWrapper> getClapCountAidByAid(@Param("listofaid") List<Integer> aid);
	
	
	@Query(value="	select datetime,aid from korenani.claps_of_answer where aid in :listofaid and userprofilename = :userprofilename",nativeQuery = true)
	public List<AnswerDatetimeAndAidWrapperInterface> getDatetimeAndAidByAidListAndUserProfilename(@Param("listofaid") List<Integer> aid, @Param("userprofilename") String userprofilename);
	
	
}