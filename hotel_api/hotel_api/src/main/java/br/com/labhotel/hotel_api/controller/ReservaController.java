package br.com.labhotel.hotel_api.controller;

import br.com.labhotel.hotel_api.dto.ReservaRequest;
import br.com.labhotel.hotel_api.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarReserva(@RequestBody ReservaRequest reservaRequest) {
        
        try {
            reservaService.criarReserva(reservaRequest);
            return ResponseEntity.ok("Reserva criada com sucesso.");

        } catch (Exception e) {
            String causaRaiz = e.getMessage();
            if (e.getCause() != null) {
                causaRaiz = e.getCause().getMessage();
            }
            return ResponseEntity.badRequest().body("Erro ao criar reserva: " + causaRaiz);
        }
    }
}