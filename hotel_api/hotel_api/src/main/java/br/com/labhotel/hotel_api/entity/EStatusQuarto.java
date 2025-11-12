package br.com.labhotel.hotel_api.entity;

/**
 * Enum Java que representa o ENUM do MySQL 'status_quarto'.
 * Isso garante que o nosso código só pode usar valores válidos.
 */
public enum EStatusQuarto {
    Disponível,
    Ocupado,
    Manutenção,
    Limpeza
}