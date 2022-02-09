package sisviatura.springboot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.LogVistoria;



@Repository
@Transactional
public interface logVistoriaRepository extends JpaRepository<LogVistoria, Long> {
  
	@Query("select l from LogVistoria l where l.vistoria.id = ?1 order by l.dataAlteracao asc")
	List<LogVistoria> findLogByData(Long id);
}
