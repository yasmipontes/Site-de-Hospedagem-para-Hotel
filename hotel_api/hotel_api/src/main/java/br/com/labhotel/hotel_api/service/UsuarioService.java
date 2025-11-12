package br.com.labhotel.hotel_api.service;

// Nossos pacotes
import br.com.labhotel.hotel_api.dto.RegistroRequest;
import br.com.labhotel.hotel_api.entity.GruposUsuarios;
import br.com.labhotel.hotel_api.entity.Usuarios;
import br.com.labhotel.hotel_api.repository.GruposUsuariosRepository;
import br.com.labhotel.hotel_api.repository.UsuariosRepository;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service (Serviço) de Usuários.
 * Contém a lógica de negócio para operações de utilizadores,
 * como o registo.
 */
@Service
public class UsuarioService {

    // --- Injeção de Dependências ---
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private GruposUsuariosRepository gruposUsuariosRepository; // Precisamos disto para encontrar o grupo "Cliente"

    @Autowired
    private PasswordEncoder passwordEncoder; // A ferramenta de codificar senhas

    /**
     * Método para registar um novo utilizador no sistema.
     */
    public void registrarNovoUsuario(RegistroRequest registroRequest) {

        // 1. Verificar se o email já está em uso
        if (usuariosRepository.findByEmail(registroRequest.email()).isPresent()) {
            // Se 'isPresent()', o email já existe.
            throw new RuntimeException("O email " + registroRequest.email() + " já está em uso.");
        }

        // 2. Encontrar o grupo de utilizador "Cliente"
        // No nosso script SQL (05_usuarios_permissoes.sql),
        // definimos o grupo "Cliente" com o ID = 3.
        GruposUsuarios grupoCliente = gruposUsuariosRepository.findById(3)
                .orElseThrow(() -> new RuntimeException("Grupo 'Cliente' (ID 3) não encontrado."));

        // 3. Criar a nova entidade Usuario
        Usuarios novoUsuario = new Usuarios();
        novoUsuario.setNome(registroRequest.nome());
        novoUsuario.setEmail(registroRequest.email());
        novoUsuario.setTelefone(registroRequest.telefone());
        novoUsuario.setGrupo(grupoCliente); // Associa ao grupo "Cliente"
        novoUsuario.setDataCriacao(LocalDateTime.now()); // Define a data de agora

        // 4. Codificar a Senha (O passo mais importante)
        // NUNCA guardamos a senha em texto puro.
        String senhaCodificada = passwordEncoder.encode(registroRequest.senha());
        novoUsuario.setSenhaHash(senhaCodificada); // Guarda o HASH no banco

        // 5. Salvar o novo utilizador no banco
        usuariosRepository.save(novoUsuario);
    }
}