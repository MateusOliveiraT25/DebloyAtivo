package br.com.ativos.ativospatrimoniais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.ativos.ativospatrimoniais.model.Ambientes;
import br.com.ativos.ativospatrimoniais.model.Ativo;
import br.com.ativos.ativospatrimoniais.repository.AmbientesRepository;
import br.com.ativos.ativospatrimoniais.repository.AtivoRepository;

@Controller
public class EditarAtivoController {

    @Autowired
    private AtivoRepository ativoRepository;

    @Autowired
    private AmbientesRepository ambienteRepository; // Injete o AmbienteRepository

    private Ativo atualizarAtivoAtributo(Integer id, Model model, String atributo, Object valor,
            String mensagemErro) {
        Optional<Ativo> optionalAtivo = ativoRepository.findById(id);
        if (optionalAtivo.isPresent()) {
            Ativo ativo = optionalAtivo.get();
            switch (atributo) {
                case "nome":
                    ativo.setNome((String) valor);
                    break;
                case "descricao":
                    ativo.setDescricao((String) valor);
                    break;
                case "ambiente":
                    Integer ambienteId = (Integer) valor;
                    Long ambienteIdLong = ambienteId.longValue();
                    Ambientes ambiente = ambienteRepository.findById(ambienteIdLong).orElse(null);
                    ativo.setAmbiente(ambiente);
                    break;
                default:
                    return null;
            }
            ativoRepository.save(ativo);
            return ativo;
        } else {
            model.addAttribute("error", mensagemErro);
            return null;
        }
    }

    @PostMapping("/editarCodAtivo/{id}")
    public String editarCodAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "novoCod", required = true) Integer novoCod) {
        // Busca o ativo pelo ID
        Optional<Ativo> optionalAtivo = ativoRepository.findById(id);

        if (optionalAtivo.isPresent()) {
            Ativo ativoOriginal = optionalAtivo.get();

            // Verifica se o novo código já está em uso por outro ativo
            Ativo ativoExistente = ativoRepository.findByCod(novoCod);
            if (ativoExistente != null && !ativoExistente.equals(ativoOriginal)) {
                // Se o novo código já está em uso, redirecione com uma mensagem de erro
                return "redirect:/administracao?error=Codigo ja esta sendo utilizado por outro ativo";
            } else {
                // Cria um novo ativo com o código atualizado e outras informações do ativo original
                Ativo ativoAtualizado = new Ativo();
                ativoAtualizado.setCod(novoCod);
                ativoAtualizado.setNome(ativoOriginal.getNome());
                ativoAtualizado.setDescricao(ativoOriginal.getDescricao());
                ativoAtualizado.setAmbiente(ativoOriginal.getAmbiente());
                // Salva o novo ativo
                ativoRepository.save(ativoAtualizado);
                // Remove o ativo original
                ativoRepository.delete(ativoOriginal);
                return "redirect:/administracao?success=Codigo do ativo editado com sucesso";
            }
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado";
        }
    }

    @PostMapping("/editarNomeAtivo/{id}")
    public String editarNomeAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "nome", required = false) String nome) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "nome", nome, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Nome do ativo editado com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou nome vazio";
        }
    }

    @PostMapping("/editarDescricaoAtivo/{id}")
    public String editarDescricaoAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "descricao", required = false) String descricao) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "descricao", descricao, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Descricao do ativo editada com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou descricao vazia";
        }
    }

    @PostMapping("/editarCorAtivo/{id}")
    public String editarCorAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "cor", required = false) String cor) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "cor", cor, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Cor do ativo editada com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou cor vazia";
        }
    }

    @PostMapping("/editarPrecoAtivo/{id}")
    public String editarPrecoAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "preco", required = false) Double preco) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "preco", preco, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Preco do ativo editado com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou preco vazio";
        }
    }

    @PostMapping("/editarAmbienteAtivo/{id}")
    public String editarAmbienteAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "ambiente", required = false) Integer ambienteId) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "ambiente", ambienteId, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Ambiente do ativo editada com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou ambiente vazia";
        }
    }

    @PostMapping("/editarMarcaAtivo/{id}")
    public String editarMarcaAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "marca", required = false) String marca) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "marca", marca, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Marca do ativo editada com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou marca vazia";
        }
    }

    @PostMapping("/editarTamanhoAtivo/{id}")
    public String editarTamanhoAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "tamanho", required = false) String tamanho) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "tamanho", tamanho, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Tamanho do ativo editado com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou tamanho vazio";
        }
    }

    @PostMapping("/editarLinkMlAtivo/{id}")
    public String editarLinkMlAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "linkMl", required = false) String linkMl) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "linkMl", linkMl, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Link do Mercado Livre do ativo editado com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou link do Mercado Livre vazio";
        }
    }

    @PostMapping("/editarLinkInstaAtivo/{id}")
    public String editarLinkInstaAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "linkInsta", required = false) String linkInsta) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "linkInsta", linkInsta, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Link do Instagram do ativo editado com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou link do Instagram vazio";
        }
    }

    @PostMapping("/editarLinkFaceMessAtivo/{id}")
    public String editarLinkFaceMessAtivo(Model model, @PathVariable Integer id,
            @RequestParam(value = "linkFaceMess", required = false) String linkFaceMess) {

        Ativo ativo = atualizarAtivoAtributo(id, model, "linkFaceMess", linkFaceMess, "Ativo não encontrado");
        if (ativo != null) {
            return "redirect:/administracao?success=Link do Facebook Messenger do ativo editado com sucesso";
        } else {
            return "redirect:/administracao?error=Ativo nao encontrado ou link do Facebook Messenger vazio";
        }
    }
}
