package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.labhotel.hotel_api.entity.GruposUsuarios;

/**
 * Repositório GruposUsuarios (JPA Repository)
 *
 * Esta interface é a nossa "ponte" mágica com o banco de dados.
 * Ao estender 'JpaRepository', o Spring Data JPA automaticamente
 * cria todos os comandos SQL básicos (CRUD) para nós.
 */
public interface GruposUsuariosRepository extends JpaRepository<GruposUsuarios, Integer> {
    
    // O JpaRepository já nos dá o findById(3) que o 
    // UsuarioService precisa, então não precisamos
    // de nenhum código customizado aqui.

}