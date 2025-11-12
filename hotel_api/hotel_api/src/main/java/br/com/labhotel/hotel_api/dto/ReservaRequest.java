package br.com.labhotel.hotel_api.dto;

/**
 * DTO para receber um pedido de criação de nova reserva.
 */
public record ReservaRequest(
    Integer idCliente,  // Quem está a reservar
    Integer idQuarto,   // Qual quarto
    String dataCheckin, // Formato "YYYY-MM-DD"
    String dataCheckout // Formato "YYYY-MM-DD"
) {
}