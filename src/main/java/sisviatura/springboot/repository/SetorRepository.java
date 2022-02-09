package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Setor;
import sisviatura.springboot.model.Usuarios;


@Repository
@Transactional
public interface SetorRepository extends JpaRepository<Setor, Long> { 
	
	
	@Query("select s from Setor s where s.id = ?1")
	Setor findSetorByid(Long id);
	
	@Query("select s from Setor s where s.id = ?1 ")
	List<Setor> findVerifSetorByid(Long id);
	
	@Query("select s from Setor s where s.nome like %?1%")
	List<Setor> findSetorByNome(String nome);
	
	@Query("select s from Setor s  order by s.nome asc")
	List<Setor> findSetores();
	
	@Query("select s from Setor s where s.id <> 145 order by s.nome asc")
	List<Setor> findSetoresAtualizarInfo();
	
	@Query("select s from Setor s where s.deletado = 'FALSE'")
	Page<Setor> findSetoByid(Pageable pageable);
	
	@Query("select s from Setor s where s.nome like %?1% OR lower(s.nome) like lower(concat('%', ?1,'%')) ")
	Page<Setor> findSetoName(String nome, Pageable pageable);
	
}
