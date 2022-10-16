package com.eventoapp.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Convidado implements Serializable {
  
  @Id
  @NotBlank
  private String rg;

  @NotBlank
  private String nomeConvidado;
  
  @ManyToOne
  private Evento evento;
}
