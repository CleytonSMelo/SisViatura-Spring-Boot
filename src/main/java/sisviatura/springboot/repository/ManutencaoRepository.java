package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Manutencao;



@Repository
@Transactional
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
 
	@Query("select m from Manutencao m where m.id = ?1")
    Manutencao findManutencaoByid(Long id);
	
	@Query("select m from Manutencao m where m.deletado = 'FALSE'")
	List<Manutencao> findManutencoes();
	
	@Query("select count(m) from Manutencao m")
	Long totalmanutencoes();
	
	
	@Query("SELECT COUNT(m) FROM Manutencao m WHERE m.deletado = 'FALSE' ")
    Long contarManutencaoSolicitadas();       
    
    @Query("SELECT COUNT(m) FROM Manutencao m WHERE (m.statusmanutencao = 'CONCLUIDO') AND m.deletado = 'FALSE' ")
    Long contarManutencaoConcluidas();
    
    @Query("SELECT COUNT(m) FROM Manutencao m"+
            " WHERE EXTRACT(YEAR FROM m.dataabertura) = DATE_PART('YEAR', CURRENT_TIMESTAMP) AND m.deletado = 'FALSE'")
    Long contarTotalManutencaoAnual();
		
    @Query("SELECT COUNT(m) FROM Manutencao m"+
            " WHERE EXTRACT(YEAR FROM m.dataabertura)= ?1 AND m.deletado = 'FALSE'")
	Long contarTotalServicosSelectAnual(Integer data);
	
}