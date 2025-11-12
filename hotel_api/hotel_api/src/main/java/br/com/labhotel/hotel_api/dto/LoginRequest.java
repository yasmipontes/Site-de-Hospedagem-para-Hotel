package br.com.labhotel.hotel_api.dto;

/**
 * DTO (Data Transfer Object) para receber dados de Login.
 * Um "record" é uma forma moderna no Java de criar uma classe
 * que apenas "guarda" dados (como email e senha) de forma imutável.
 */
public record LoginRequest(
    String email,
    String senha
) {
}