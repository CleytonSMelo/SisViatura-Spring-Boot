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

import sisviatura.springboot.model.LogViatura;



@Repository
@Transactional
public interface logViaturaRepository extends JpaRepository<LogViatura, Long> {
  
	@Query("select l from LogViatura l where l.viatura.id = ?1 order by l.dataAlteracao asc")
	List<LogViatura> findLogByData(Long id);
}
