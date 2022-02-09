package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Modelo;
import sisviatura.springboot.model.Usuarios;


@Repository
@Transactional
public interface ModeloRepository extends CrudRepository<Modelo, Long> {
  
		
	@Query("select m from Modelo m where m.id = ?1")
	Modelo findModeloByid(Long id);
	
	@Query("select m from Modelo m where m.deletado = 'FALSE' ")
	List<Modelo> findModelos();
	
}
