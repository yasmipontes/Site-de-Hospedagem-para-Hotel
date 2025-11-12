package br.com.labhotel.hotel_api.controller;

// Nossos DTOs e Serviços
import br.com.labhotel.hotel_api.dto.PreferenciasRequest;
import br.com.labhotel.hotel_api.service.PreferenciasService;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa todos (GetMapping, PostMapping, etc)

import java.util.Map;

/**
 * Controller (Controlador) para as Preferências (MongoDB).
 *
 * @RestController
 * @RequestMapping("/preferencias"): Todos os endpoints aqui
 * começarão com "/preferencias".
 */
@RestController
@RequestMapping("/preferencias")
public class PreferenciasController {

    @Autowired
    private PreferenciasService preferenciasService; // O "cérebro" do MongoDB

    /**
     * Endpoint para BUSCAR as preferências de um cliente.
     * Mapeia para GET em "/preferencias/{idCliente}"
     * @PathVariable: Pega o "idCliente" do próprio link.
     */
    @GetMapping("/{idClienteMysql}")
    public ResponseEntity<Map<String, Object>> buscarPreferencias(@PathVariable Integer idClienteMysql) {
        
        Map<String, Object> preferencias = preferenciasService.buscarPreferencias(idClienteMysql);
        return ResponseEntity.ok(preferencias);
    }

    /**
     * Endpoint para SALVAR/ATUALIZAR as preferências de um cliente.
     * Mapeia para POST em "/preferencias".
     */
    @PostMapping("/salvar")
    public ResponseEntity<String> salvarPreferencias(@RequestBody PreferenciasRequest request) {
        try {
            preferenciasService.salvarPreferencias(request);
            return ResponseEntity.ok("Preferências salvas com sucesso.");
        
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar preferências: " + e.getMessage());
        }
    }
}