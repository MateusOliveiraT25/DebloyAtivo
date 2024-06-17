package br.com.ativos.ativospatrimoniais.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Ativo implements Serializable {
    @Id
    private int cod;
    private String nome;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "ambiente_id")
    private Ambientes ambiente;
}