package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure; // Importe esta
import org.springframework.data.repository.query.Param;
import br.com.labhotel.hotel_api.entity.Reservas;
import java.util.List;
import java.time.LocalDate; // Importe esta

/**
 * Repositório Reservas (JPA Repository)
 */
public interface ReservasRepository extends JpaRepository<Reservas, Integer> {

    // Método customizado para buscar todas as reservas de um cliente específico
    List<Reservas> findByClienteIdUsuario(Integer idUsuario);

    // Método customizado para buscar todas as reservas de um quarto específico
    List<Reservas> findByQuartoIdQuarto(Integer idQuarto);

    /**
     * CHAMADA DA STORED PROCEDURE (sp_fazer_reserva)
     *
     * @Procedure: Diz ao Spring Data JPA que este método não é um SELECT,
     * mas sim uma chamada a uma Stored Procedure do banco.
     *
     * O nome do método ("fazerReserva") é ligado ao nome da
     * Procedure "sp_fazer_reserva" no banco.
     *
     * Os parâmetros são mapeados automaticamente pelo nome.
     */
    // ... outros métodos ...

/**
 * CHAMADA DA STORED PROCEDURE (sp_fazer_reserva)
 *
 * @Procedure: Diz ao Spring Data JPA que este método é
 * uma chamada a uma Stored Procedure do banco.
 *
 * name = "sp_fazer_reserva": Liga o método Java 'fazerReserva'
 * à procedure 'sp_fazer_reserva' do MySQL.
 */
@Procedure(name = "sp_fazer_reserva") // <-- ESTA É A LINHA DA CORREÇÃO
void fazerReserva(
    @Param("p_id_cliente") Integer idCliente,
    @Param("p_id_quarto") Integer idQuarto,
    @Param("p_data_checkin") LocalDate dataCheckin,
    @Param("p_data_checkout") LocalDate dataCheckout
);

// ...
}