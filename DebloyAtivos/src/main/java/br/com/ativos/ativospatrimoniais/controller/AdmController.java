package br.com.ativos.ativospatrimoniais.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ativos.ativospatrimoniais.model.Funcionario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AdmController {
    // Método POST para processar o login do administrador
    @PostMapping("/login-adm")
    public String processAdminLogin(@RequestParam("username") String username,
            @RequestParam("password") String password, HttpServletRequest request) {
        // Lógica para processar o login do administrador
        if (validarCredenciais(username, password)) {
            // As credenciais são válidas, cria uma nova sessão
            HttpSession session = request.getSession(true);

            // Define um atributo na sessão para indicar que o administrador está logado
            session.setAttribute("admin", true);

            // Redireciona para a página do painel administrativo
            return "redirect:/dashboard-adm";
        } else {
            // As credenciais são inválidas, redireciona de volta para a página de login com
            // uma mensagem de erro
            return "redirect:/login-adm?error";
        }
    }

    // Método privado para validar as credenciais do administrador
    private boolean validarCredenciais(String username, String password) {
        // Verificar se a senha fornecida corresponde à senha armazenada
        Funcionario admin = new Funcionario();
        admin.setUsername(username); // Set the username
        admin.setSenha(password); // Set the password
        // Assuming you have a way to retrieve the stored password for the provided username
        if (admin.getUsername() != null && admin.getSenha().equals(password)) {
            return true; // Credenciais válidas
        } else {
            return false; // Credenciais inválidas
        }
    }

    // Método para fazer logoff do administrador
    @GetMapping("/logoff-adm")
    public String logoffAdmin(HttpServletRequest request) {
        // Obtém a sessão atual, se existir
        HttpSession session = request.getSession(false);

        // Invalida a sessão, se existir
        if (session != null) {
            session.invalidate();
        }

        // Redireciona para a página inicial
        return "index";
    }
}