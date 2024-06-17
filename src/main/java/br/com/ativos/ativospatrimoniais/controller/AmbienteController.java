package br.com.ativos.ativospatrimoniais.controller;

import br.com.ativos.ativospatrimoniais.model.Ambientes;
import br.com.ativos.ativospatrimoniais.repository.AmbientesRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AmbienteController {

    private final AmbientesRepository ambienteRepository;

    public AmbienteController(AmbientesRepository ambienteRepository) {
        this.ambienteRepository = ambienteRepository;
    }
    
    @PostMapping("/ambientes")
    public String cadastrarAmbiente(@ModelAttribute Ambientes ambiente, Model model) {
        // Verifica se o nome da ambiente está em branco
        if (ambiente.getNomeAmbiente() == null || ambiente.getNomeAmbiente().isEmpty()) {
            return "redirect:/administracao?error=O nome da ambiente esta em branco";
        }

        // Verifica se a ambiente já existe no banco de dados
        if (ambienteRepository.findByNomeAmbiente(ambiente.getNomeAmbiente()) != null) {
            return "redirect:/administracao?error=Ambiente ja cadastrado!";
        } else {
            // Salva a nova ambiente
            ambienteRepository.save(ambiente);
            model.addAttribute("mensagem", "Ambiente cadastrada com sucesso!");
            return "redirect:/administracao?success=Ambiente cadastrado com sucesso!";
        }
    }

    @GetMapping("/excluir-ambiente/{id}")
    public String excluirAmbiente(@PathVariable Long id) {
        try {
            ambienteRepository.deleteById(id);
        return "redirect:/administracao?success=Ambiente excluida com sucesso!";
        } catch (Exception e) {
            return "redirect:/administracao?error=Ambiente sendo usado!";
        }
        
    }

}
