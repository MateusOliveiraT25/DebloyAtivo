package br.com.ativos.ativospatrimoniais.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ativos.ativospatrimoniais.model.Ambientes;
import br.com.ativos.ativospatrimoniais.model.Ativo;
import br.com.ativos.ativospatrimoniais.repository.AmbientesRepository;
import br.com.ativos.ativospatrimoniais.repository.AtivoRepository;

@Controller
public class AtivoController {

    @Autowired
    private AtivoRepository ativoRepository;
    @Autowired
    private AmbientesRepository ambienteRepository;

    @GetMapping("/ativo_edit")
    public String paginaAtivoEdit(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("cod").ascending());
        Page<Ativo> ativoPage = ativoRepository.findAll(pageable);
        List<Ambientes> ambientes = ambienteRepository.findAll();
        model.addAttribute("ambientes", ambientes);
        model.addAttribute("ativo", ativoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ativoPage.getTotalPages());
        model.addAttribute("totalItems", ativoPage.getTotalElements());
        return "sistema_de_controle/administracao";
    }

    @GetMapping("/excluir-ativo/{id}")
    public String excluirAtivo(@PathVariable Integer id) {
        Ativo ativo = ativoRepository.findById(id).orElse(null);

        if (ativo != null) {
            ativoRepository.delete(ativo);
            return "redirect:/administracao?success=Ativo excluido com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo não encontrado";
        }
    }

    @PostMapping("/novoAtivo")
    public String novoAtivo(Model model,
            @RequestParam("codigoAtivo") String codigoAtivo,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "quantidade", required = false) Integer quantidade,
            @RequestParam(value = "ambiente", required = false) Integer ambienteId) {

        if (codigoAtivo == null || codigoAtivo.trim().isEmpty()) {
            model.addAttribute("error", "Por favor, forneça um código de ativo válido.");
            return paginaAtivoEdit(model, 0);
        }

        int codigoAtivoInt;
        try {
            codigoAtivoInt = Integer.parseInt(codigoAtivo);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "O código do ativo fornecido não é válido.");
            return paginaAtivoEdit(model, 0);
        }

        Ativo ativoExistenteCodigo = ativoRepository.findByCod(codigoAtivoInt);
        if (ativoExistenteCodigo != null) {
            return "redirect:/administracao?error=Ja existe um ativo com o mesmo codigo";
        }

        Ativo ativo = new Ativo();
        ativo.setCod(codigoAtivoInt);
        ativo.setNome(nome);
        ativo.setDescricao(descricao);
        Long ambienteIdLong = ambienteId.longValue();
        Ambientes ambiente = ambienteRepository.findById(ambienteIdLong).orElse(null);
        ativo.setAmbiente(ambiente);
        ativoRepository.save(ativo);

        return "redirect:/administracao?success=Novo ativo cadastrado com sucesso";

    }
}