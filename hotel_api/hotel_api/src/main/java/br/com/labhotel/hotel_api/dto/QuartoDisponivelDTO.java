package br.com.labhotel.hotel_api.dto;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para enviar os dados
 * da nossa 'view_quartos_disponiveis' para o frontend.
 */
public record QuartoDisponivelDTO(
    Integer idQuarto,
    String numeroQuarto,
    String nomeTipo,
    Integer capacidadeMaxima,
    BigDecimal precoBaseDiaria
) {
}