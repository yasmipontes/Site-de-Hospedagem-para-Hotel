package br.com.labhotel.hotel_api.service;

// Nossos DTOs e Repositórios
import br.com.labhotel.hotel_api.dto.ReservaRequest;
import br.com.labhotel.hotel_api.repository.ReservasRepository;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Service (Serviço) de Reservas.
 * Contém a lógica de negócio para operações de reservas.
 */
@Service
public class ReservaService {

    @Autowired
    private ReservasRepository reservasRepository;

    /**
     * Método para criar uma nova reserva.
     * Este método vai DELEGAR a lógica de negócio
     * (verificar conflitos, calcular valor) para a nossa
     * Stored Procedure 'sp_fazer_reserva' no MySQL.
     */
    public void criarReserva(ReservaRequest reservaRequest) {
        
        // 1. Validar e Converter as datas
        // O DTO dá-nos 'String', mas a Procedure precisa de 'LocalDate'.
        LocalDate checkin;
        LocalDate checkout;

        try {
            checkin = LocalDate.parse(reservaRequest.dataCheckin());
            checkout = LocalDate.parse(reservaRequest.dataCheckout());
        } catch (DateTimeParseException e) {
            // Se o formato da data (ex: "YYYY-MM-DD") estiver errado.
            throw new RuntimeException("Formato de data inválido. Use YYYY-MM-DD.", e);
        }

        // 2. Chamar o Repositório, que vai chamar a Procedure
        reservasRepository.fazerReserva(
            reservaRequest.idCliente(),
            reservaRequest.idQuarto(),
            checkin,
            checkout
        );
        
        // Se a Stored Procedure (sp_fazer_reserva) der um erro
        // (ex: "Quarto indisponível"), ela vai lançar uma exceção SQL,
        // que o Spring vai apanhar e reportar ao frontend.
        // A nossa lógica Java fica limpa e simples.
    }
}