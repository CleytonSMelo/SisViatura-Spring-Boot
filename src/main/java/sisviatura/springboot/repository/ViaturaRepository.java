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
import sisviatura.springboot.model.Viatura;


@Repository
@Transactional
public interface ViaturaRepository extends JpaRepository<Viatura, Long> {
 
	@Query("select v from Viatura v where v.id = ?1")
    Viatura findViaturaByid(Long id);
	
	@Query("select v from Viatura v where v.deletado = 'FALSE'")
	List<Viatura> findViaturas();
	
	@Query("select v from Viatura v where v.placaoficial = ?1 AND v.deletado = 'FALSE'")
    Viatura findViaturaByPlaca(String placa);
	
}