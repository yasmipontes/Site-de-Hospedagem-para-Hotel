package br.com.labhotel.hotel_api.dto;

/**
 * DTO para receber dados de registo de um novo utilizador.
 */
public record RegistroRequest(
    String nome,
    String email,
    String senha, // A senha em texto puro
    String telefone
) {
}