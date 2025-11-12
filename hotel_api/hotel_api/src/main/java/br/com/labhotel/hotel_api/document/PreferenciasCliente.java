package br.com.labhotel.hotel_api.document;

import java.util.Map;

// Importações específicas do MongoDB
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Documento PreferenciasCliente (MongoDB)
 * Esta classe NÃO é uma @Entity do JPA, mas sim um @Document do MongoDB.
 * Ela será usada para armazenar os dados flexíveis (preferências)
 * que não se encaixam bem no MySQL.
 */
@Document(collection = "preferencias_clientes") // Nome da "tabela" (coleção) no Mongo
public class PreferenciasCliente {

    /**
     * @Id (do MongoDB)
     * Note que a importação é 'org.springframework.data.annotation.Id'.
     * O ID do Mongo é quase sempre uma 'String'.
     */
    @Id
    private String id; // O ID do Mongo (ex: "673f8f...")

    /**
     * Esta é a nossa "Chave Estrangeira" manual.
     * Vamos usar este campo para ligar este documento Mongo
     * ao ID do usuário na tabela 'usuarios' do MySQL.
     */
    @Field("id_cliente_mysql") // Define o nome do campo no Bando
    private Integer idClienteMysql;

    /**
     * A MÁGICA DO NOSQL ESTÁ AQUI.
     *
     * 'Map<String, Object>' é um "dicionário" flexível.
     * Ele nos permite salvar QUALQUER estrutura de dados.
     *
     * Exemplo de "preferencias":
     * {
     * "alergia": "amendoim",
     * "andar": "alto",
     * "pet": { "nome": "Max", "tipo": "cachorro" }
     * }
     *
     * Isso cumpre o requisito de "justificar o uso do NoSQL"
     * para dados não-estruturados.
     */
    private Map<String, Object> preferencias;

    // Construtor vazio
    public PreferenciasCliente() {
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdClienteMysql() {
        return idClienteMysql;
    }

    public void setIdClienteMysql(Integer idClienteMysql) {
        this.idClienteMysql = idClienteMysql;
    }

    public Map<String, Object> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Map<String, Object> preferencias) {
        this.preferencias = preferencias;
    }
}