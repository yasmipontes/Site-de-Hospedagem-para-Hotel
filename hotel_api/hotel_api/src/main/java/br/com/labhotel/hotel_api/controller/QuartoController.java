package br.com.labhotel.hotel_api.controller;

import br.com.labhotel.hotel_api.dto.QuartoDisponivelDTO;
import br.com.labhotel.hotel_api.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller (Controlador) para Quartos.
 */
@RestController
@RequestMapping("/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    /**
     * Endpoint (link) para o frontend buscar os quartos.
     * Mapeia para GET em "/quartos/disponiveis".
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<List<QuartoDisponivelDTO>> buscarQuartosDisponiveis() {
        List<QuartoDisponivelDTO> quartos = quartoService.listarQuartosDisponiveis();
        return ResponseEntity.ok(quartos); // Devolve a lista como JSON
    }
}