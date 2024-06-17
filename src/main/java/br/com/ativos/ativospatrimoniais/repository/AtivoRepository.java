package br.com.ativos.ativospatrimoniais.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ativos.ativospatrimoniais.model.Ativo;

@Repository
public interface AtivoRepository extends CrudRepository<Ativo, Integer> {
    Ativo findByNome(String nome);
    Ativo findByCod(int cod);
    Page<Ativo> findByAmbiente(String ambiente, Pageable pageable);
    Page<Ativo> findAll(org.springframework.data.domain.Pageable pageable);
}