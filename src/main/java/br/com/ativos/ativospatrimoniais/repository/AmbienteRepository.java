package br.com.ativos.ativospatrimoniais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ativos.ativospatrimoniais.model.Ambientes;

public interface AmbienteRepository extends JpaRepository<Ambientes, Long> {
}
