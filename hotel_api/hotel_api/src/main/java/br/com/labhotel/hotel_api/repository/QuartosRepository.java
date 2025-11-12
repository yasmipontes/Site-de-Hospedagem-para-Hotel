package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.labhotel.hotel_api.entity.Quartos;

/**
 * Repositório Quartos (JPA Repository)
 */
public interface QuartosRepository extends JpaRepository<Quartos, Integer> {
    // Já temos o CRUD. Não precisamos de mais nada por enquanto.
}