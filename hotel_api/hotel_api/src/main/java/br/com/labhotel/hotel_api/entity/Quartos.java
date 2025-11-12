package br.com.labhotel.hotel_api.entity;

// Importações do JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade Quartos (JPA Entity)
 *
 * Espelho da tabela 'quartos'.
 * Armazena os quartos físicos do hotel, cada um ligado a um 'tipo_quarto'.
 */
@Entity
@Table(name = "quartos")
public class Quartos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quarto")
    private Integer idQuarto;

    /**
     * Relacionamento N:1 (Muitos-para-Um)
     * Muitos quartos físicos pertencem a UM tipo de quarto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_quarto", nullable = false)
    private TiposQuarto tipoQuarto; // Referenciamos o objeto, não o ID

    @Column(name = "numero_quarto", nullable = false, unique = true, length = 10)
    private String numeroQuarto;

    @Column(name = "andar")
    private Integer andar;

    /**
     * Mapeamento de ENUM do MySQL.
     * @Enumerated(EnumType.STRING) diz ao JPA para salvar
     * o TEXTO do ENUM ('Disponível', 'Ocupado', etc.) no banco,
     * e não o seu número (0, 1, 2), o que é muito melhor para
     * a leitura do banco.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status_quarto", nullable = false)
    private EStatusQuarto statusQuarto; // Usaremos um Enum Java para isso

    // Construtor vazio (obrigatório pelo JPA)
    public Quartos() {
    }

    // Getters e Setters
    public Integer getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(Integer idQuarto) {
        this.idQuarto = idQuarto;
    }

    public TiposQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TiposQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(String numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public EStatusQuarto getStatusQuarto() {
        return statusQuarto;
    }

    public void setStatusQuarto(EStatusQuarto statusQuarto) {
        this.statusQuarto = statusQuarto;
    }
}