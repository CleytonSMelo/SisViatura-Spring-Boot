package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Cor;
import sisviatura.springboot.model.Cota;


@Repository
@Transactional
public interface CotaRepository extends JpaRepository<Cota, Long> {
  
	
	
	@Query("select c from Cota c where c.id = ?1")
	Cota findCotaByid(Long id);
	
	@Query("select c from Cota c where c.deletado = 'FALSE'")
	List<Cota> findCotas();
	
	@Query("SELECT COUNT(c) FROM Cota c WHERE c.deletado = 'FALSE' ")
    Long contarCotasSolicitados();       
    
    @Query("SELECT COUNT(c) FROM Cota c WHERE (c.statuscota = 'APROVADA') AND c.deletado = 'FALSE' ")
    Long contarCotasAprovadas();
    
    @Query("SELECT COUNT(c) FROM Cota c"+
            " WHERE EXTRACT(YEAR FROM c.datacadastro) = DATE_PART('YEAR', CURRENT_TIMESTAMP) AND c.deletado = 'FALSE'")
    Long contarTotalCotasAnual();
		
    @Query("SELECT COUNT(c) FROM Cota c"+
            " WHERE EXTRACT(YEAR FROM c.datacadastro)= ?1 AND c.deletado = 'FALSE'")
	Long contarTotalCotasSelectAnual(Integer data);
	
}