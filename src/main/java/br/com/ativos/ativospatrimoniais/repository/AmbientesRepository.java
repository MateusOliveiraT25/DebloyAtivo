package br.com.ativos.ativospatrimoniais.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import br.com.ativos.ativospatrimoniais.model.Ambientes;

@Repository
public interface AmbientesRepository extends CrudRepository<Ambientes, Long> {
    Ambientes findByNomeAmbiente(String nomeAmbiente);

    @NonNull
    List<Ambientes> findAll();
}
