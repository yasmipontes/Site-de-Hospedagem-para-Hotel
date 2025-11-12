package br.com.labhotel.hotel_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para enviar os detalhes de uma reserva para o frontend.
 * É baseado na nossa 'view_detalhes_reservas' do MySQL.
 */
public record ReservaResponse(
    Integer idReserva,
    String statusReserva,
    String nomeCliente,
    String emailCliente,
    String numeroQuarto,
    String tipoQuarto,
    LocalDate dataCheckin,
    LocalDate dataCheckout,
    Integer totalNoites,
    BigDecimal valorTotalReserva
) {
}