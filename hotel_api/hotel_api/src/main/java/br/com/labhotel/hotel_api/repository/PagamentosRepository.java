package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.labhotel.hotel_api.entity.Pagamentos;
import java.util.List;

/**
 * Repositório Pagamentos (JPA Repository)
 */
public interface PagamentosRepository extends JpaRepository<Pagamentos, Integer> {

    // Método customizado para buscar todos os pagamentos de uma reserva
    List<Pagamentos> findByReservaIdReserva(Integer idReserva);
}