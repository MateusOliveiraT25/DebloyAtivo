package br.com.ativos.ativospatrimoniais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ativos.ativospatrimoniais.model.Funcionario;
import br.com.ativos.ativospatrimoniais.repository.AmbienteRepository;
import br.com.ativos.ativospatrimoniais.repository.FuncionarioRepository;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private AmbienteRepository ambienteRepository;

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("ambientes", ambienteRepository.findAll());
        return "cadastroFuncionario";
    }

    @PostMapping("/novo")
    public String novoFuncionario(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "redirect:/administracao";
    }
}
