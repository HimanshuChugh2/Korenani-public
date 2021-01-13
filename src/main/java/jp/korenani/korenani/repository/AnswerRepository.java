package jp.korenani.korenani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.Answer;
import jp.korenani.korenani.wrappers.AnswerPagintationWrapperInterface;
import jp.korenani.korenani.wrappers.QuestionPagintationWrapperInterface;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	
	@Query(value="select * from answer where qid= :qid",nativeQuery = true)
	public List<Answer> GetByQid(@Param("qid") int qid);
	


	@Query(value="select qid, date, id from korenani.answer where userprofilename = :userprofilename ORDER BY date desc limit 2", nativeQuery = true)
	public List<AnswerPagintationWrapperInterface> getTwoAnswers(@Param("userprofilename") String userprofilename);
	
	 

}
