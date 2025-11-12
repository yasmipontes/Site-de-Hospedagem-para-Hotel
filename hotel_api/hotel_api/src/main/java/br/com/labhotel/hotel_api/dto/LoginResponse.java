package br.com.labhotel.hotel_api.dto;

/**
 * DTO (Data Transfer Object) para enviar a resposta do Login.
 * Estamos enviando apenas o ID do usuário, o nome e o "papel" (ex: "Gerente").
 * NUNCA enviamos a senha de volta.
 */
public record LoginResponse(
    Integer id,
    String nome,
    String nomeGrupo
) {
}