package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importe esta
import org.springframework.data.repository.query.Param; // Importe esta
import br.com.labhotel.hotel_api.entity.Usuarios;
import java.util.Optional;

/**
 * Repositório Usuarios (JPA Repository)
 */
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

    /**
     * CORREÇÃO DE PERFORMANCE (JOIN FETCH):
     *
     * O 'findByEmail' padrão sofreria de LazyInitializationException
     * ao tentarmos acessar o 'grupo' do usuário no AuthService.
     *
     * Com o @Query e o 'JOIN FETCH u.grupo', estamos a dizer ao JPA:
     * "Quando buscares o Usuário (u), traz (FETCH)
     * o seu 'grupo' na MESMA consulta SQL."
     *
     * Isto resolve o problema de performance "N+1" e
     * o erro de lazy loading.
     *
     * @Param("email") liga o parâmetro 'email' do método
     * ao ':email' da query.
     */
    @Query("SELECT u FROM Usuarios u JOIN FETCH u.grupo WHERE u.email = :email")
    Optional<Usuarios> findByEmailComGrupo(@Param("email") String email);

    // Vamos manter o findByEmail original caso precisemos dele
    // em outro lugar sem o grupo.
    Optional<Usuarios> findByEmail(String email);
    
}