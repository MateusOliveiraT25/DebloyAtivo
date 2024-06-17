package br.com.ativos.ativospatrimoniais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ativos.ativospatrimoniais.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
    Funcionario findByUsername(String username);
}
