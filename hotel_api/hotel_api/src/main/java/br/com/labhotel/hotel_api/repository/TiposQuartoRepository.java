package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.labhotel.hotel_api.entity.TiposQuarto;

/**
 * Repositório TiposQuarto (JPA Repository)
 */
public interface TiposQuartoRepository extends JpaRepository<TiposQuarto, Integer> {
    // Não precisamos de nenhum método customizado por enquanto.
    // O JpaRepository já nos dá todo o CRUD (save, findById, findAll, delete).
}