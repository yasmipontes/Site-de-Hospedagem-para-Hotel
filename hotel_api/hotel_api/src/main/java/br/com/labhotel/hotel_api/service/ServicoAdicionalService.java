package br.com.labhotel.hotel_api.service;

// Nossos DTOs e Repositórios
import br.com.labhotel.hotel_api.dto.AddServicoRequest;
import br.com.labhotel.hotel_api.repository.ReservasServicosRepository;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service (Serviço) para Serviços Adicionais.
 */
@Service
public class ServicoAdicionalService {

    @Autowired
    private ReservasServicosRepository reservasServicosRepository;

    /**
     * Método para adicionar um consumo (serviço) a uma reserva existente.
     */
    public void adicionarServico(AddServicoRequest request) {
        
        reservasServicosRepository.adicionarServicoReserva(
            request.idReserva(),
            request.idServico(),
            request.quantidade()
        );
    }
}