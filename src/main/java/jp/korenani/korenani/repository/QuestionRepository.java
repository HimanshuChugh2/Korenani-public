package jp.korenani.korenani.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.Question;
import jp.korenani.korenani.wrappers.QuestionPagintationWrapperInterface;

@Repository
@Transactional
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	@Query(value = "select * from question where id = :id", nativeQuery = true)
	public Question getById(@Param("id") int id);
	
	
	@Query(value="select title, date, id from question where userprofilename = :userprofilename ORDER BY date desc limit 2 ", nativeQuery = true)
	public List<QuestionPagintationWrapperInterface> getTwoQuestions(@Param("userprofilename") String userprofilename);
	

	@Query(value="select title, date, id from question where userprofilename = :userprofilename ORDER BY date desc limit 5 offset :offset", nativeQuery = true)
	public List<QuestionPagintationWrapperInterface> getXQuestions(@Param("userprofilename") String userprofilename, @Param("offset") int offset);
	
	
}
