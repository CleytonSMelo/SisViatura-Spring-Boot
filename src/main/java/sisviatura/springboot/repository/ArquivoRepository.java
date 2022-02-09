package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Arquivo;
import sisviatura.springboot.model.Cor;



@Repository
@Transactional
public interface ArquivoRepository extends CrudRepository<Arquivo, Long> {
  
	
	
	@Query("select a from Arquivo a where a.id = ?1")
	Arquivo findArquivossByid(Long id);
	
	@Query("select a from Arquivo a")
	List<Arquivo> findArquivos();
	
}