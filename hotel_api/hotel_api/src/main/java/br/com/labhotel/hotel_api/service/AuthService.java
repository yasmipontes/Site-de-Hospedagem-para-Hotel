package br.com.labhotel.hotel_api.service;

// Importações dos nossos pacotes
import br.com.labhotel.hotel_api.dto.LoginRequest;
import br.com.labhotel.hotel_api.dto.LoginResponse;
import br.com.labhotel.hotel_api.entity.Usuarios;
import br.com.labhotel.hotel_api.repository.UsuariosRepository;

// Importações do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service (Serviço) de Autenticação.
 *
 * @Service diz ao Spring: "Esta classe é um 'cérebro',
 * um componente que contém a lógica de negócios".
 */
@Service
public class AuthService {

    // --- Injeção de Dependências ---
    // Estamos a "pedir" ao Spring as ferramentas que precisamos.

    @Autowired
    private UsuariosRepository usuariosRepository; // O "operário" que fala com a tabela 'usuarios'

    @Autowired
    private PasswordEncoder passwordEncoder; // A ferramenta que verifica senhas (do SecurityConfig)

    /**
     * Método principal de login.
     * Recebe os dados brutos (LoginRequest) e
     * retorna os dados filtrados (LoginResponse) se o login for válido.
     */
    public LoginResponse fazerLogin(LoginRequest loginRequest) {

        // 1. Encontrar o utilizador pelo email no banco de dados
        // Usamos o método 'findByEmail' que criámos no Repositório.
        Optional<Usuarios> usuarioOpt = usuariosRepository.findByEmailComGrupo(loginRequest.email());

        // 2. Verificar se o utilizador existe
        if (usuarioOpt.isEmpty()) {
            // Se o Optional está vazio, o email não foi encontrado.
            throw new RuntimeException("Email ou senha inválidos."); // Não diga ao utilizador *qual* está errado
        }

        // 3. Se o utilizador existe, pegamos o objeto
        Usuarios usuario = usuarioOpt.get();

        // 4. Verificar a Senha
        // Esta é a parte crucial.
        // O 'passwordEncoder.matches()' vai:
        // A) Pegar a senha bruta (ex: "123456") do 'loginRequest'
        // B) Pegar o hash (ex: "$2a$10$...") do banco de dados (em 'usuario.getSenhaHash()')
        // C) Comparar os dois de forma segura.
        if (passwordEncoder.matches(loginRequest.senha(), usuario.getSenhaHash())) {
            
            // 5. SUCESSO! Senha correta.
            // Montamos o nosso DTO de resposta (LoginResponse)
            // NUNCA enviamos a 'senhaHash' de volta.
            return new LoginResponse(
                    usuario.getIdUsuario(),
                    usuario.getNome(),
                    usuario.getGrupo().getNomeGrupo() // Pega o nome (ex: "Gerente") do grupo
            );

        } else {
            // 6. FALHA! Senha incorreta.
            throw new RuntimeException("Email ou senha inválidos.");
        }
    }
}
