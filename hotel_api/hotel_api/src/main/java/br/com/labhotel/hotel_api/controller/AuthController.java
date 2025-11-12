package br.com.labhotel.hotel_api.controller;

// Nossos DTOs e Serviços
import br.com.labhotel.hotel_api.dto.LoginRequest;
import br.com.labhotel.hotel_api.dto.LoginResponse;
import br.com.labhotel.hotel_api.dto.RegistroRequest;
import br.com.labhotel.hotel_api.service.AuthService;
import br.com.labhotel.hotel_api.service.UsuarioService;

// Pacotes do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller (Controlador) de Autenticação.
 *
 * @RestController: Diz ao Spring que esta classe é um "porteiro"
 * que lida com pedidos HTTP (API).
 *
 * @RequestMapping("/auth"): Diz ao Spring que TODOS os links
 * (endpoints) dentro desta classe começarão com "/auth".
 * Ex: /auth/login, /auth/registrar
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    // --- Injeção de Dependências ---
    // Pedimos ao Spring os "cérebros" (Serviços) que este
    // "porteiro" precisa de contactar.
    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint de Login.
     *
     * @PostMapping("/login"): Mapeia este método para pedidos
     * do tipo POST no link "/auth/login".
     *
     * @RequestBody: Diz ao Spring para "desempacotar" o JSON
     * que vem no corpo do pedido e transformá-lo
     * no nosso DTO 'LoginRequest'.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> fazerLogin(@RequestBody LoginRequest loginRequest) {
        // 1. Chama o "cérebro" (AuthService) para fazer a lógica
        LoginResponse resposta = authService.fazerLogin(loginRequest);
        
        // 2. Retorna a resposta (LoginResponse) com um status "200 OK"
        return ResponseEntity.ok(resposta);
    }

    /**
     * Endpoint de Registo.
     * Mapeia este método para pedidos POST em "/auth/registrar".
     */
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody RegistroRequest registroRequest) {
        
        try {
            // 1. Tenta chamar o "cérebro"
            usuarioService.registrarNovoUsuario(registroRequest);
            
            // 2. Se funcionar, retorna "OK"
            return ResponseEntity.ok("Utilizador registado com sucesso.");

        } catch (RuntimeException e) {
            // 3. Se o "cérebro" lançar o erro (ex: "Email já em uso")
            //    nós apanhamos o erro e devolvemos uma resposta
            //    HTTP 400 (Bad Request) com a mensagem de erro.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}