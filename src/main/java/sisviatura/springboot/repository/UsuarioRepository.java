package sisviatura.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sisviatura.springboot.model.Usuarios;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
  
	@Query("select u from Usuarios u where u.deletado = 'FALSE' AND u.login = ?1")
	Usuarios findUserByLogin(String login);
	
	@Query("select u from Usuarios u where u.deletado = 'FALSE' AND u.nome = ?1")
	Usuarios findUserByName(String nome);
	
	@Query("select u from Usuarios u where u.id = ?1")
	Usuarios findUserByid(Long id);
	
	@Query("select u from Usuarios u where u.setor.id = ?1 AND u.deletado = 'FALSE' order by u.nome")
	List<Usuarios> findUsuariosPorSetorBy(Long id);
	
	@Query("select u from Usuarios u where u.deletado = 'FALSE' ")
	Page<Usuarios> findUseByid(Pageable pageable);
	
	@Query("select u from Usuarios u where u.deletado = 'FALSE' AND u.login like %?1%")
	List<Usuarios> findUsuariosByLogin(String login);
	
	@Query("select u from Usuarios u where u.deletado = 'FALSE' order by u.nome")
	List<Usuarios> findUsuarios();
	
	@Query("select u from Usuarios u where u.deletado = 'FALSE' AND u.roles != 'ROLE_CLI' order by u.id asc")
	List<Usuarios> findUsuariosAdmTec();
	
	@Query("select u from Usuarios u where u.id = ?1 AND u.deletado = 'FALSE' AND u.roles != 'ROLE_CLI' order by u.id asc")
	Usuarios findUsuariosAdminTec(Long id);
	
	@Query("select u from Usuarios u join Setor s on u.setor.id = s.id where u.deletado = 'FALSE' AND u.nome like %?1% or lower(u.nome) like lower(concat('%', ?1,'%')) or u.login like %?1% or u.matricula like %?1% or s.nome like %?1% OR lower(s.nome) like lower(concat('%', ?1,'%'))")
	Page<Usuarios> findUseName(String nome, Pageable pageable);
}
