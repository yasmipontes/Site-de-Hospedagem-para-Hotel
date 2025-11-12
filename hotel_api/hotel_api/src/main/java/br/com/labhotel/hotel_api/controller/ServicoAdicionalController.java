package br.com.labhotel.hotel_api.controller;

// Nossos DTOs e Serviços
import br.com.labhotel.hotel_api.dto.AddServicoRequest;
import br.com.labhotel.hotel_api.service.ServicoAdicionalService;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller (Controlador) para Serviços Adicionais.
 *
 * @RestController
 * @RequestMapping("/servicos"): Todos os endpoints aqui
 * começarão com "/servicos".
 */
@RestController
@RequestMapping("/servicos")
public class ServicoAdicionalController {

    @Autowired
    private ServicoAdicionalService servicoAdicionalService;

    /**
     * Endpoint para adicionar um consumo a uma reserva.
     * Mapeia este método para pedidos POST em "/servicos/adicionar".
     */
    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarServico(@RequestBody AddServicoRequest request) {
        
        try {
            servicoAdicionalService.adicionarServico(request);
            return ResponseEntity.ok("Serviço adicionado com sucesso.");

        } catch (Exception e) {
            // Se a Stored Procedure falhar (ex: FK constraint)
            String causaRaiz = e.getMessage();
            if (e.getCause() != null) {
                causaRaiz = e.getCause().getMessage();
            }
            return ResponseEntity.badRequest().body("Erro ao adicionar serviço: " + causaRaiz);
        }
    }
}