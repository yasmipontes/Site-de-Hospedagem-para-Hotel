package br.com.labhotel.hotel_api.service;

import br.com.labhotel.hotel_api.dto.QuartoDisponivelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; // Importe este
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service (Serviço) para Quartos.
 */
@Service
public class QuartoService {

    /**
     * @Autowired
     * Estamos a pedir ao Spring o 'JdbcTemplate'. Esta é a ferramenta
     * mais fácil para fazer um 'SELECT' simples, especialmente numa VIEW.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Busca todos os quartos da nossa VIEW 'view_quartos_disponiveis'.
     */
    public List<QuartoDisponivelDTO> listarQuartosDisponiveis() {
        
        // A query SQL que queremos executar
        String sql = "SELECT id_quarto, numero_quarto, nome_tipo, capacidade_maxima, preco_base_diaria " +
                     "FROM view_quartos_disponiveis";

        // O jdbcTemplate executa o SQL e mapeia (RowMapper)
        // cada linha para o nosso DTO.
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new QuartoDisponivelDTO(
                rs.getInt("id_quarto"),
                rs.getString("numero_quarto"),
                rs.getString("nome_tipo"),
                rs.getInt("capacidade_maxima"),
                rs.getBigDecimal("preco_base_diaria")
            )
        );
    }
}