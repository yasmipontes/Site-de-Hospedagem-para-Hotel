package br.com.labhotel.hotel_api.entity;

import java.math.BigDecimal; // Importação correta para dinheiro (DECIMAL)

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade TiposQuarto (JPA Entity)
 *
 * Espelho da tabela 'tipos_quarto'.
 * Armazena as categorias (ex: Standard, Deluxe) e seus preços base.
 */
@Entity
@Table(name = "tipos_quarto")
public class TiposQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_quarto")
    private Integer idTipoQuarto;

    @Column(name = "nome_tipo", nullable = false, length = 100)
    private String nomeTipo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "capacidade_maxima", nullable = false)
    private Integer capacidadeMaxima;

    /**
     * IMPORTANTE:
     * No MySQL usamos DECIMAL(10, 2) para dinheiro.
     * Em Java, o tipo correspondente CORRETO é 'BigDecimal'.
     * Nunca use 'double' ou 'float' para dinheiro, pois causam
     * erros de arredondamento.
     */
    @Column(name = "preco_base_diaria", nullable = false)
    private BigDecimal precoBaseDiaria;

    // Construtor vazio (obrigatório pelo JPA)
    public TiposQuarto() {
    }

    // Getters e Setters
    public Integer getIdTipoQuarto() {
        return idTipoQuarto;
    }

    public void setIdTipoQuarto(Integer idTipoQuarto) {
        this.idTipoQuarto = idTipoQuarto;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public BigDecimal getPrecoBaseDiaria() {
        return precoBaseDiaria;
    }

    public void setPrecoBaseDiaria(BigDecimal precoBaseDiaria) {
        this.precoBaseDiaria = precoBaseDiaria;
    }
}