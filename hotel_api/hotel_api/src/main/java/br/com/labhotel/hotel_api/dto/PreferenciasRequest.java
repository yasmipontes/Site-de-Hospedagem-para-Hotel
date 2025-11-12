package br.com.labhotel.hotel_api.dto;

import java.util.Map;

/**
 * DTO para receber (salvar/atualizar) as preferências
 * de um cliente.
 */
public record PreferenciasRequest(
    // O ID do cliente no MySQL
    Integer idClienteMysql, 

    // O "dicionário" flexível de preferências
    // Ex: { "alergia": "amendoim", "andar": "alto" }
    Map<String, Object> preferencias 
) {
}