package br.com.labhotel.hotel_api.entity;

import java.math.BigDecimal; // Importação para dinheiro

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade ServicosAdicionais (JPA Entity)
 *
 * Espelho da tabela 'servicos_adicionais'.
 * Armazena o catálogo de serviços que podem ser consumidos.
 */
@Entity
@Table(name = "servicos_adicionais")
public class ServicosAdicionais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "nome_servico", nullable = false, unique = true, length = 100)
    private String nomeServico;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario; // Sempre BigDecimal para dinheiro

    // Construtor vazio
    public ServicosAdicionais() {
    }

    // Getters e Setters
    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}