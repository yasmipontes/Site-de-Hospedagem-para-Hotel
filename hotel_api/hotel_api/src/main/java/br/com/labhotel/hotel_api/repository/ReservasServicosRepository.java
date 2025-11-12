package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure; // Importe esta
import org.springframework.data.repository.query.Param; // Importe esta
import br.com.labhotel.hotel_api.entity.ReservasServicos;
import java.util.List;

/**
 * Repositório ReservasServicos (JPA Repository)
 */
public interface ReservasServicosRepository extends JpaRepository<ReservasServicos, Integer> {

    // Método customizado para buscar todos os serviços consumidos
    // em uma reserva específica.
    List<ReservasServicos> findByReservaIdReserva(Integer idReserva);

    /**
     * CHAMADA DA STORED PROCEDURE (sp_adicionar_servico_reserva)
     *
     * Mapeia este método Java para a nossa procedure no MySQL.
     * A procedure 'sp_adicionar_servico_reserva' trata de:
     * 1. Apanhar o preço correto do serviço.
     * 2. Inserir o consumo em 'reservas_servicos'.
     * 3. Atualizar o 'valor_total_reserva' na tabela 'reservas'.
     */
    @Procedure(name = "sp_adicionar_servico_reserva")
    void adicionarServicoReserva(
        @Param("p_id_reserva") Integer idReserva,
        @Param("p_id_servico") Integer idServico,
        @Param("p_quantidade") Integer quantidade
    );
}