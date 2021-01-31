package jp.korenani.korenani.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 import jp.korenani.korenani.entities.CreatedFolder;
 
@Repository
public interface FolderRepository extends JpaRepository<CreatedFolder, Integer> {

	@Query(value="select DISTINCT foldername from created_folder   where  username = :username" ,nativeQuery = true)
	public List<Object[]> getAllFoldersByUsername (@Param("username") String username);
	
	
	
	@Query(value="select contentid from created_folder where foldername = :foldername and username = :username", nativeQuery = true)
	public List<Integer> getContentIdByFoldername(@Param("foldername") String foldername,@Param("username") String username);
	
	@Query(value="select * from created_folder where contentid in :list and foldername= :foldername ", nativeQuery = true)
	public List<CreatedFolder> getCCdataSubQuery(@Param("list") List<Integer> list, @Param("foldername") String foldername);
	
	@Transactional
	@Modifying
	@Query(value="delete from created_folder where contentid in :array", nativeQuery = true)
	public void deleteExistingContentByArray(@Param("array") Integer[] array);
	
	
	
	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(
	 * value="insert into korenani.created_folder (contentid,foldername,username) values(:contentid, :foldername , :username)"
	 * ,nativeQuery = true) public void
	 * insertNewContentidInFolder(@Param("contentid") int
	 * contentid, @Param("foldername") String foldername, @Param("username") String
	 * username );
	 */
	
	// now i am getting the content id associated with respective folder, need to get the data from cc table with multiple contentid which is equal to id of cc
 
	
	
}
