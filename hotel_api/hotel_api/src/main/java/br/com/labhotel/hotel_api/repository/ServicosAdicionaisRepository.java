package br.com.labhotel.hotel_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.labhotel.hotel_api.entity.ServicosAdicionais;

/**
 * Repositório ServicosAdicionais (JPA Repository)
 */
public interface ServicosAdicionaisRepository extends JpaRepository<ServicosAdicionais, Integer> {
    // CRUD básico já incluído
}