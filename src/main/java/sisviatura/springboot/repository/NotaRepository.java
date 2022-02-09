package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Nota;



@Repository
@Transactional
public interface NotaRepository extends JpaRepository<Nota, Long> {
  
	@Query("select n from Nota n where n.id = ?1")
	Nota findNotaByid(Long id);
	
	@Query("select n from Nota n where n.manutencao.id = ?1 order by n.id asc")
	List<Nota> findNotaId(Long id);		
	
	@Query("select count(n) from Nota n")
	Long totalNotas();
			
}
