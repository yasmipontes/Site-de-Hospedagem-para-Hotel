package br.com.labhotel.hotel_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade ReservasServicos (JPA Entity)
 *
 * Esta é a nossa entidade "Muitos-para-Muitos" (N:M).
 * Ela é o espelho da tabela 'reservas_servicos' e serve
 * como a "ponte" entre uma Reserva e um ServicoAdicional.
 *
 * A tratamos como entidade pois ela tem colunas extras
 * (payload), como 'quantidade' e 'preco_cobrado'.
 */
@Entity
@Table(name = "reservas_servicos")
public class ReservasServicos {

    // Este ID é 'AUTO_INCREMENT'
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_servico")
    private Integer idReservaServico;

    /**
     * Relacionamento N:1 com Reservas
     * Muitos consumos de serviço pertencem a UMA reserva.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reservas reserva;

    /**
     * Relacionamento N:1 com ServicosAdicionais
     * Muitos consumos de serviço podem ser do MESMO serviço.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico", nullable = false)
    private ServicosAdicionais servico;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    /**
     * 'preco_cobrado' é crucial. Ele "congela" o preço do serviço
     * no momento do consumo, caso o preço mude no catálogo.
     */
    @Column(name = "preco_cobrado", nullable = false)
    private BigDecimal precoCobrado;

    @Column(name = "data_consumo", nullable = false)
    private LocalDateTime dataConsumo;

    // Construtor vazio
    public ReservasServicos() {
    }

    // Getters e Setters
    public Integer getIdReservaServico() {
        return idReservaServico;
    }

    public void setIdReservaServico(Integer idReservaServico) {
        this.idReservaServico = idReservaServico;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }

    public ServicosAdicionais getServico() {
        return servico;
    }

    public void setServico(ServicosAdicionais servico) {
        this.servico = servico;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoCobrado() {
        return precoCobrado;
    }

    public void setPrecoCobrado(BigDecimal precoCobrado) {
        this.precoCobrado = precoCobrado;
    }

    public LocalDateTime getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(LocalDateTime dataConsumo) {
        this.dataConsumo = dataConsumo;
    }
}