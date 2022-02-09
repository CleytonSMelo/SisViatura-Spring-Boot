package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Vistoria;


@Repository
@Transactional
public interface VistoriaRepository extends JpaRepository<Vistoria, Long> {
 
	@Query("select v from Vistoria v where v.id = ?1")
    Vistoria findVistoriaByid(Long id);
	
	@Query("select v from Vistoria v where v.deletado = 'FALSE'")
	List<Vistoria> findVistorias();
	
	@Query("select count(v) from Vistoria v")
	Long totalvistorias();
	
	@Query("SELECT COUNT(v) FROM Vistoria v WHERE v.deletado = 'FALSE' ")
    Long contarVistoriasSolicitadas();       
    
    @Query("SELECT COUNT(v) FROM Vistoria v WHERE (v.statusvistoria = 'CONCLUIDO') AND v.deletado = 'FALSE' ")
    Long contarVistoriasConcluidas();
    
    @Query("SELECT COUNT(v) FROM Vistoria v"+
            " WHERE EXTRACT(YEAR FROM v.datacadastro) = DATE_PART('YEAR', CURRENT_TIMESTAMP) AND v.deletado = 'FALSE'")
    Long contarTotalVistoriasAnual();
		
    @Query("SELECT COUNT(v) FROM Vistoria v"+
            " WHERE EXTRACT(YEAR FROM v.datacadastro)= ?1 AND v.deletado = 'FALSE'")
	Long contarTotalVistoriasSelectAnual(Integer data);
	
}