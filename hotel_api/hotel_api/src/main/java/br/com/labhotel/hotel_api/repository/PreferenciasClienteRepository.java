package br.com.labhotel.hotel_api.repository;

import java.util.Optional;

// Importação específica do MongoDB
import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.labhotel.hotel_api.document.PreferenciasCliente;

/**
 * Repositório PreferenciasCliente (Mongo Repository)
 *
 * Note que ele estende 'MongoRepository', e não 'JpaRepository'.
 * O Spring automaticamente entende que este repositório
 * deve "falar" com o banco definido em 'spring.data.mongodb.uri'.
 */
public interface PreferenciasClienteRepository extends MongoRepository<PreferenciasCliente, String> {
    // O JpaRepository usa ID 'Integer'.
    // O MongoRepository usa ID 'String'.

    /**
     * Método customizado para buscar as preferências
     * usando o ID do nosso usuário do MySQL.
     */
    Optional<PreferenciasCliente> findByIdClienteMysql(Integer idClienteMysql);
}