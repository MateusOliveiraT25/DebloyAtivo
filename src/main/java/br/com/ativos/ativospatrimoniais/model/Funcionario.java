package br.com.ativos.ativospatrimoniais.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Funcionario implements Serializable {
    @Id
    private String username;
    private String senha;
    private String cargo;
   
    @ManyToOne
    @JoinColumn(name = "ambiente_id")
    private Ambientes ambiente;
   
   
    @Enumerated(EnumType.STRING)
    private Privilegio privilegio;
    public enum Privilegio {
        Administrador,
        Professor,
        Funcionario
    }
}