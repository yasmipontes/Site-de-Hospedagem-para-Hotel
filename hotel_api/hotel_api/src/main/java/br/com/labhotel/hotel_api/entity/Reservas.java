package br.com.labhotel.hotel_api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
 * Entidade Reservas (JPA Entity)
 *
 * Esta é a entidade transacional mais importante.
 * Ela liga um 'Usuario' (cliente) a um 'Quarto'.
 */
@Entity
@Table(name = "reservas")
public class Reservas {

    /**
     * ID CRÍTICO - SEM @GeneratedValue
     * Note que NÃO estamos usando @GeneratedValue(strategy = GenerationType.IDENTITY).
     * Por quê? Porque no nosso SCRIPT SQL (02_advanced_ids_triggers.sql),
     * nós criamos um TRIGGER ('trg_before_insert_reservas') que
     * gera o ID para nós usando a nossa FUNÇÃO 'fn_get_next_id'.
     *
     * O JPA (Java) só precisa saber que este é o @Id.
     * Quando o Java fizer o INSERT, o banco de dados (MySQL)
     * vai interceptar o comando e preencher o ID automaticamente.
     */
    @Id
    @Column(name = "id_reserva")
    private Integer idReserva;

    /**
     * Relacionamento N:1 com Usuarios
     * Muitas reservas podem ser feitas por UM cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuarios cliente;

    /**
     * Relacionamento N:1 com Quartos
     * Muitas reservas podem ser feitas para UM quarto (em datas diferentes).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_quarto", nullable = false)
    private Quartos quarto;

    @Column(name = "data_checkin", nullable = false)
    private LocalDate dataCheckin; // No MySQL é DATE, em Java é LocalDate

    @Column(name = "data_checkout", nullable = false)
    private LocalDate dataCheckout; // No MySQL é DATE, em Java é LocalDate

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva", nullable = false)
    private EReservaStatus statusReserva;

    @Column(name = "valor_total_reserva", nullable = false)
    private BigDecimal valorTotalReserva;

    @Column(name = "data_reserva", nullable = false)
    private LocalDateTime dataReserva; // No MySQL é TIMESTAMP, em Java é LocalDateTime

    
    // Construtor vazio (obrigatório pelo JPA)
    public Reservas() {
    }

    // Getters e Setters
    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Usuarios getCliente() {
        return cliente;
    }

    public void setCliente(Usuarios cliente) {
        this.cliente = cliente;
    }

    public Quartos getQuarto() {
        return quarto;
    }

    public void setQuarto(Quartos quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(LocalDate dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public LocalDate getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(LocalDate dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public EReservaStatus getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(EReservaStatus statusReserva) {
        this.statusReserva = statusReserva;
    }

    public BigDecimal getValorTotalReserva() {
        return valorTotalReserva;
    }

    public void setValorTotalReserva(BigDecimal valorTotalReserva) {
        this.valorTotalReserva = valorTotalReserva;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }
}