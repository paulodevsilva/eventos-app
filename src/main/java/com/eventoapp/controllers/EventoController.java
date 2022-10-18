package com.eventoapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@RestController()
@RequestMapping("events")
public class EventoController {

  @Autowired
  private EventoRepository eventoRepository;

  @Autowired
  private ConvidadoRepository convidadoRepository;

  

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody  Evento evento, BindingResult result) {

    if (result.hasErrors()) {
      Map<String, Object> response = new HashMap<String, Object>();
      
      response.put("message", "Required fields Missing");

      return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
    }

    this.eventoRepository.save(evento);

    ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.CREATED);

    return response;
  }

  @GetMapping()
  public ResponseEntity<List<Evento>> findAll() {
    List<Evento> eventos = (List<Evento>) this.eventoRepository.findAll();
    
    ResponseEntity<List<Evento>> response = new ResponseEntity<List<Evento>>(eventos, HttpStatus.OK);

    return response;
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Object> findDetails(@PathVariable("codigo") Long codigo) {
    Evento evento = this.eventoRepository.findByCodigo(codigo);

    if (Objects.isNull(evento)) {
      Map<String, Object> response = new HashMap<String, Object>();

      response.put("message", "Event is not found");

      return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<Object> response = new ResponseEntity<>(evento,HttpStatus.OK);

    return response;
  }

  @DeleteMapping("/{codigo}")
  public ResponseEntity<Object> delete(@PathVariable("codigo") long codigo) {
    Evento evento = this.eventoRepository.findByCodigo(codigo);

    if (Objects.isNull(evento)) {
      Map<String, Object> response = new HashMap<String, Object>();

      response.put("message", "Event is not found");

      return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
    }

    this.eventoRepository.delete(evento);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Object> update(@PathVariable("codigo") Long codigo, @RequestBody Evento evento) {
     Evento isExistsEvento = this.eventoRepository.findByCodigo(codigo);

    if (Objects.isNull(isExistsEvento)) {
      Map<String, Object> response = new HashMap<String, Object>();

      response.put("message", "Event is not found");

      return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
    }

    evento.setCodigo(codigo);

    this.eventoRepository.save(evento);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  // @RequestMapping(value="/{codigo}", method=RequestMethod.POST)
  // public String detalhesEventoPost(@PathVariable("codigo") Long codigo, @Valid Convidado convidado, BindingResult result) {
  //   if (!result.hasErrors()) {
  //       Evento evento = this.eventoRepository.findByCodigo(codigo);
    
  //       convidado.setEvento(evento);
      
  //       this.convidadoRepository.save(convidado);

  //       attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso");

  //       return "redirect:/{codigo}";
  //   } 

  //   attributes.addFlashAttribute("mensagem", "Verifique os campos");

  //   return "redirect:/{codigo}";
   
  // }

  // @RequestMapping("/deletarConvidado")
  // public String deletarConvidado(String rg) {
  //   Convidado convidado = this.convidadoRepository.findByRg(rg);
  //   this.convidadoRepository.delete(convidado);

  //   Evento evento = convidado.getEvento();
  //   long codigoLongEvento  = evento.getCodigo();
  //   String codigo = "" + codigoLongEvento;

  //   return "redirect:/" + codigo;
  // }

}
