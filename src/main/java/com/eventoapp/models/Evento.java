package com.eventoapp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Evento implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long codigo;
  
  @NotBlank
  private String nome;

  @NotBlank
  private String local;
  
  @NotBlank
  private String data;

  @NotBlank
  private String horario;

  @OneToMany
  private List<Convidado> convidados; 
}