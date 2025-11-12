package br.com.labhotel.hotel_api.service;

// Nossos pacotes
import br.com.labhotel.hotel_api.document.PreferenciasCliente;
import br.com.labhotel.hotel_api.dto.PreferenciasRequest;
import br.com.labhotel.hotel_api.repository.PreferenciasClienteRepository;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Service (Serviço) para as Preferências do Cliente (MongoDB).
 */
@Service
public class PreferenciasService {

    @Autowired
    private PreferenciasClienteRepository preferenciasRepository; // O repositório do MongoDB!

    /**
     * Busca as preferências de um cliente.
     */
    public Map<String, Object> buscarPreferencias(Integer idClienteMysql) {
        // Usa o método que criámos no repositório
        Optional<PreferenciasCliente> prefs = preferenciasRepository.findByIdClienteMysql(idClienteMysql);

        if (prefs.isPresent()) {
            // Se encontrou, devolve o "mapa" flexível
            return prefs.get().getPreferencias();
        } else {
            // Se o cliente não tem preferências salvas, devolve um mapa vazio
            return Map.of(); // Devolve um mapa vazio
        }
    }

    /**
     * Salva ou Atualiza as preferências de um cliente.
     */
    public void salvarPreferencias(PreferenciasRequest request) {
        
        // 1. Verifica se o cliente já tem um documento de preferências
        Optional<PreferenciasCliente> prefsOpt = preferenciasRepository.findByIdClienteMysql(request.idClienteMysql());

        PreferenciasCliente preferencias;

        if (prefsOpt.isPresent()) {
            // 2. Se JÁ EXISTE:
            // Pega o documento existente...
            preferencias = prefsOpt.get();
            // ...e atualiza o mapa de preferências
            preferencias.setPreferencias(request.preferencias());
        } else {
            // 3. Se NÃO EXISTE:
            // Cria um novo documento...
            preferencias = new PreferenciasCliente();
            preferencias.setIdClienteMysql(request.idClienteMysql());
            preferencias.setPreferencias(request.preferencias());
        }

        // 4. Salva (ou atualiza) o documento no MongoDB
        preferenciasRepository.save(preferencias);
    }
}