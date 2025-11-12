package br.com.labhotel.hotel_api.dto;

/**
 * DTO para receber um pedido de adicionar um serviço
 * a uma reserva existente.
 */
public record AddServicoRequest(
    Integer idReserva,
    Integer idServico,
    Integer quantidade
) {
}