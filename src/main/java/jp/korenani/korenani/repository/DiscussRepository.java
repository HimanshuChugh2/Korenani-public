package jp.korenani.korenani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.korenani.korenani.entities.Question;

@Repository
public interface DiscussRepository extends JpaRepository<Question, Integer> {

	@Query(value="select id,title,tags,date,userprofilename from question",nativeQuery = true)
	public List<Object[]> getalldata();
	
}
