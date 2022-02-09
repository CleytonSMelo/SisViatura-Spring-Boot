package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Cor;


@Repository
@Transactional
public interface CorRepository extends CrudRepository<Cor, Long> {
  
	
	
	@Query("select c from Cor c where c.id = ?1")
	Cor findCorByid(Long id);
	
	@Query("select c from Cor c where c.deletado = 'FALSE'")
	List<Cor> findCores();
	
}