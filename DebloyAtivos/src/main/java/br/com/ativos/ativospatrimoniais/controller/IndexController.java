package br.com.ativos.ativospatrimoniais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ativos.ativospatrimoniais.model.Ambientes;
import br.com.ativos.ativospatrimoniais.model.Ativo;
import br.com.ativos.ativospatrimoniais.model.Funcionario;
import br.com.ativos.ativospatrimoniais.repository.AmbientesRepository;
import br.com.ativos.ativospatrimoniais.repository.AtivoRepository;
import br.com.ativos.ativospatrimoniais.repository.FuncionarioRepository;

@Controller
public class IndexController {

    @Autowired
    private AtivoRepository ativoRepository;
    @Autowired
    private AmbientesRepository ambienteRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/index")
    public String abrirIndex(Model model) {
        return "index";
    }

    @GetMapping("/cadastro-adm")
    public String adminCadastro(Model model) {
        return "cadastro-adm";
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "cadastro-funcionario";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(Funcionario funcionario, Model model) {
        Funcionario existingFuncionario = funcionarioRepository.findByUsername(funcionario.getUsername());
        if (existingFuncionario != null) {
            model.addAttribute("error", "O username já está em uso.");
            return "cadastro-funcionario";
        }

        funcionarioRepository.save(funcionario);
        return "redirect:/login"; // Redireciona para a página de login após o cadastro
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        return "login_funcionario";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String username, @RequestParam String senha, Model model) {
        Funcionario funcionario = funcionarioRepository.findByUsername(username);

        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            switch (funcionario.getPrivilegio()) {
                case Administrador:
                    return "redirect:/administracao";
                case Professor:
                    return "redirect:/professo_dashboard";
                case Funcionario:
                    return "redirect:/funcionario_dashboard";
                default:
                    return "redirect:/login?error=cargo";
            }
        } else {
            return "redirect:/login?error=credenciais";
        }
    }

    @PostMapping("/logout")
    public String postMethodName(@RequestBody String entity) {
        return entity;
    }

    @GetMapping("/administracao")
    public String paginaAdministracao(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("cod").ascending());
        Page<Ativo> ativoPage = ativoRepository.findAll(pageable);
        List<Ambientes> ambientes = ambienteRepository.findAll();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("funcionario", funcionarios.get(0));
        model.addAttribute("ambientes", ambientes);
        model.addAttribute("ativo", ativoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ativoPage.getTotalPages());
        model.addAttribute("totalItems", ativoPage.getTotalElements());
        return "/administracao";
    }

}
