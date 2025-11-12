// Espera o HTML carregar
document.addEventListener("DOMContentLoaded", () => {

    // --- CONFIGURAÇÃO ---
    const API_URL = "http://localhost:8081"; // Verifique a porta!
    
    // Secções
    const secaoLogin = document.getElementById("secao-login");
    const secaoApp = document.getElementById("secao-app");

    // Formulários
    const formRegistrar = document.getElementById("form-registrar");
    const formLogin = document.getElementById("form-login");
    const formReserva = document.getElementById("form-reserva");

    // Locais de Log (Req. #12)
    const logLogin = document.getElementById("log-login");
    const logRegistrar = document.getElementById("log-registrar");
    const logReserva = document.getElementById("log-reserva");

    // Elementos da App
    const msgBemVindo = document.getElementById("bem-vindo-msg");
    const tabelaQuartosBody = document.getElementById("lista-quartos-tbody");
    const inputQuartoSelecionado = document.getElementById("quarto-selecionado-info");

    // --- ESTADO DA APLICAÇÃO (A "MEMÓRIA") ---
    let estadoApp = {
        idCliente: null,
        nomeCliente: null,
        idQuartoSelecionado: null,
        nomeQuartoSelecionado: null,
        linhaSelecionada: null // Para o efeito visual da tabela
    };
    // ------------------------------------------

    // --- FUNÇÃO DE LOG (Req. #11 e #12) ---
    function log(mensagem, tipo, elemento) {
        elemento.textContent = mensagem;
        elemento.className = 'log-msg'; // Limpa classes antigas
        
        if (tipo === 'success') {
            elemento.classList.add('log-success');
        } else if (tipo === 'error') {
            elemento.classList.add('log-error');
        }
        elemento.classList.remove('hidden'); // Mostra a mensagem

        // Esconde a mensagem depois de 5 segundos
        setTimeout(() => {
            elemento.classList.add('hidden');
        }, 5000);
    }

    // --- 1. LÓGICA DE REGISTO ---
    formRegistrar.addEventListener("submit", async (e) => {
        e.preventDefault();
        log("A registar...", 'info', logRegistrar);
        const dados = {
            nome: document.getElementById("reg-nome").value,
            email: document.getElementById("reg-email").value,
            senha: document.getElementById("reg-senha").value,
            telefone: document.getElementById("reg-telefone").value
        };
        try {
            const resposta = await fetch(`${API_URL}/auth/registrar`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados)
            });
            const textoResposta = await resposta.text();
            if (!resposta.ok) throw new Error(textoResposta); // Apanha o erro "Email já em uso"
            log(textoResposta, 'success', logRegistrar);
            formRegistrar.reset(); // Limpa o formulário
        } catch (erro) {
            log(erro.message, 'error', logRegistrar);
        }
    });

    // --- 2. LÓGICA DE LOGIN ---
    formLogin.addEventListener("submit", async (e) => {
        e.preventDefault();
        log("A fazer login...", 'info', logLogin);
        const dados = {
            email: document.getElementById("login-email").value,
            senha: document.getElementById("login-senha").value
        };
        try {
            const resposta = await fetch(`${API_URL}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados)
            });
            if (!resposta.ok) throw new Error("Email ou senha inválidos.");
            
            const dadosResposta = await resposta.json();
            estadoApp.idCliente = dadosResposta.id;
            estadoApp.nomeCliente = dadosResposta.nome;

            // Remove o ID da mensagem (Req. #4)
            msgBemVindo.textContent = `Bem-vindo, ${estadoApp.nomeCliente}!`;
            
            secaoLogin.classList.add("hidden");
            secaoApp.classList.remove("hidden");
            
            carregarQuartosDisponiveis();

        } catch (erro) {
            log(erro.message, 'error', logLogin);
        }
    });

    // --- 3. LÓGICA DE BUSCAR QUARTOS (Req. #5, #6, #7) ---
    async function carregarQuartosDisponiveis() {
        tabelaQuartosBody.innerHTML = "<tr><td colspan='4'>A buscar quartos...</td></tr>";
        
        try {
            const resposta = await fetch(`${API_URL}/quartos/disponiveis`);
            if (!resposta.ok) throw new Error("Não foi possível carregar os quartos.");
            
            const quartos = await resposta.json();
            tabelaQuartosBody.innerHTML = ""; // Limpa o "A buscar..."

            if (quartos.length === 0) {
                 tabelaQuartosBody.innerHTML = "<tr><td colspan='4'>Nenhum quarto disponível no momento.</td></tr>";
                 return;
            }

            // "Desenha" a tabela de quartos (Req. #6)
            quartos.forEach(quarto => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${quarto.numeroQuarto}</td>
                    <td>${quarto.nomeTipo}</td>
                    <td>${quarto.capacidadeMaxima}</td>
                    <td>R$ ${quarto.precoBaseDiaria}</td>
                `;

                // Lógica de clique (Req. #8)
                row.addEventListener("click", () => {
                    if (estadoApp.linhaSelecionada) {
                        estadoApp.linhaSelecionada.classList.remove("selected-row");
                    }
                    row.classList.add("selected-row");
                    estadoApp.linhaSelecionada = row;
                    estadoApp.idQuartoSelecionado = quarto.idQuarto;
                    estadoApp.nomeQuartoSelecionado = `Quarto ${quarto.numeroQuarto} (${quarto.nomeTipo})`;
                    inputQuartoSelecionado.value = estadoApp.nomeQuartoSelecionado;
                    logReserva.classList.add("hidden");
                });
                tabelaQuartosBody.appendChild(row);
            });
        } catch (erro) {
            // Mostra o erro no log de reserva
            log(erro.message, 'error', logReserva);
        }
    }

    // --- 4. LÓGICA DE RESERVA (Req. #9, #10) ---
    formReserva.addEventListener("submit", async (e) => {
        e.preventDefault();
        log("", 'info', logReserva);

        if (!estadoApp.idQuartoSelecionado) {
            log("Por favor, clique num quarto da tabela para selecionar.", 'error', logReserva);
            return;
        }

        const dados = {
            idCliente: estadoApp.idCliente,
            idQuarto: estadoApp.idQuartoSelecionado,
            dataCheckin: document.getElementById("res-checkin").value,
            dataCheckout: document.getElementById("res-checkout").value
        };

        log("A criar reserva...", 'info', logReserva);
        try {
            const resposta = await fetch(`${API_URL}/reservas/criar`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados)
            });
            
            const textoResposta = await resposta.text();
            
            if (!resposta.ok) {
                // A 'causaRaiz' do Java contém a mensagem de erro do MySQL!
                throw new Error(textoResposta);
            }

            log(textoResposta, 'success', logReserva);
            
            // Limpa a seleção
            if (estadoApp.linhaSelecionada) {
                estadoApp.linhaSelecionada.classList.remove("selected-row");
            }
            estadoApp.idQuartoSelecionado = null;
            estadoApp.nomeQuartoSelecionado = null;
            inputQuartoSelecionado.value = "Reserva confirmada! Selecione outro quarto.";

        } catch (erro) {
            // Mostra o erro da Stored Procedure! (Req. #10)
            log(erro.message, 'error', logReserva);
        }
    });

});