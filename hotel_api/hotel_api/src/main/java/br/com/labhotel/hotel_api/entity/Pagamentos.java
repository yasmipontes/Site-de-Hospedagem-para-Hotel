package br.com.labhotel.hotel_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade Pagamentos (JPA Entity)
 *
 * Espelho da tabela 'pagamentos'.
 * Armazena cada transação financeira ligada a uma reserva.
 */
@Entity
@Table(name = "pagamentos")
public class Pagamentos {

    /**
     * ID CRÍTICO - SEM @GeneratedValue
     * Assim como em 'Reservas', o ID desta tabela é gerado
     * pelo TRIGGER 'trg_before_insert_pagamentos' do MySQL.
     * O JPA (Java) só precisa saber que este é o @Id.
     */
    @Id
    @Column(name = "id_pagamento")
    private Integer idPagamento;

    /**
     * Relacionamento N:1 com Reservas
     * Muitos pagamentos podem estar ligados a UMA reserva.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reservas reserva; // Referencia o objeto Reservas

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private EPagamentoMetodo metodoPagamento;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago; // Dinheiro = BigDecimal

    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private EPagamentoStatus statusPagamento;

    // Construtor vazio
    public Pagamentos() {
    }

    // Getters e Setters
    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }

    public EPagamentoMetodo getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(EPagamentoMetodo metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public EPagamentoStatus getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(EPagamentoStatus statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}