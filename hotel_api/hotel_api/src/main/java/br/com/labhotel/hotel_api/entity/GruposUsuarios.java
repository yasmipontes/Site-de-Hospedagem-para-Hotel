package br.com.labhotel.hotel_api.entity;

// Importações necessárias para o JPA (nosso "Tradutor" de MySQL)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade GruposUsuarios (JPA Entity)
 * * Esta classe é um "espelho" da nossa tabela 'grupos_usuarios' do banco de dados MySQL.
 * O Spring Data JPA vai usar esta classe para saber como ler,
 * salvar, atualizar e deletar dados nessa tabela.
 */

// @Entity: Marca esta classe como uma entidade gerenciada pelo JPA.
@Entity 
// @Table(name = ...): Especifica o nome EXATO da tabela no banco de dados.
// É crucial porque o Java usa "camelCase" (GruposUsuarios) e o SQL
// usa "snake_case" (grupos_usuarios).
@Table(name = "grupos_usuarios") 
public class GruposUsuarios {

    // @Id: Marca este campo como a Chave Primária (PK) da tabela.
    @Id 
    // @GeneratedValue(...): Informa ao JPA como o ID é gerado.
    // 'GenerationType.IDENTITY' significa que estamos usando o 'AUTO_INCREMENT'
    // do próprio MySQL para esta tabela específica.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_grupo") // Liga este atributo à coluna 'id_grupo'
    private Integer idGrupo; // Em Java, usamos 'Integer'. 'idGrupo' é camelCase.

    // @Column(...): Liga este atributo à coluna 'nome_grupo'.
    // 'nullable = false' diz ao JPA que esta coluna NÃO PODE ser nula (NOT NULL)
    // 'unique = true' diz ao JPA que o valor deve ser único (UNIQUE)
    @Column(name = "nome_grupo", nullable = false, unique = true, length = 50)
    private String nomeGrupo;

    @Column(name = "descricao") // 'TEXT' não precisa de 'length'
    private String descricao;

    // --- Construtores, Getters e Setters ---
    // O JPA precisa de um construtor vazio para funcionar.
    public GruposUsuarios() {
    }

    // Getters e Setters são métodos públicos que o Java usa
    // para acessar e modificar os atributos (que são privados).

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}