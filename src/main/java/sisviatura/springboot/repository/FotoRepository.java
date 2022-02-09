package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Cor;
import sisviatura.springboot.model.Fotos;


@Repository
@Transactional
public interface FotoRepository extends CrudRepository<Fotos, Long> {
  
	
	
	@Query("select f from Fotos f where f.id = ?1")
	Fotos findFotosByid(Long id);
	
	@Query("select f from Fotos f")
	List<Fotos> findFotos();
	
}