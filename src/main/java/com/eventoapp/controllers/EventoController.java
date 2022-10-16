package com.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {

  @Autowired
  private EventoRepository eventoRepository;

  @Autowired
  private ConvidadoRepository convidadoRepository;

  
  @RequestMapping(value = "/cadastrarEvento", method=RequestMethod.GET)
  public String form() {
    return "evento/formEvento";
  }

   
  @RequestMapping(value = "/cadastrarEvento", method=RequestMethod.POST)
  public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
    if (!result.hasErrors()) {
        this.eventoRepository.save(evento);

        attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso");

        return "redirect:/cadastrarEvento";
    }

    attributes.addFlashAttribute("mensagem", "Verifique os campos");

    return "redirect:/cadastrarEvento";
   
  }

  @RequestMapping(value = "/eventos")
  public ModelAndView listaEventos() {
    ModelAndView mv = new ModelAndView("index");

    Iterable<Evento> eventos = this.eventoRepository.findAll();

    mv.addObject("eventos", eventos);

    return mv;

  }

  @RequestMapping(value = "/{codigo}", method=RequestMethod.GET)
  public ModelAndView detalhesEvento(@PathVariable("codigo") Long codigo) {
    Evento evento = this.eventoRepository.findByCodigo(codigo);

    ModelAndView mv = new ModelAndView("evento/detalhesEvento");
    mv.addObject("evento", evento);

    Iterable<Convidado> convidados = this.convidadoRepository.findByEvento(evento);
    mv.addObject("convidados", convidados);

    // convidados.forEach(convidado -> System.out.println("nome:" + " " +convidado.getNomeConvidado()));

    return mv;
  }

  @RequestMapping("/deletarEvento")
  public String deletarEvento(long codigo) {
    Evento evento = this.eventoRepository.findByCodigo(codigo);
    this.eventoRepository.delete(evento);

    return "redirect:/eventos";
  }


  @RequestMapping(value="/{codigo}", method=RequestMethod.POST)
  public String detalhesEventoPost(@PathVariable("codigo") Long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
    if (!result.hasErrors()) {
        Evento evento = this.eventoRepository.findByCodigo(codigo);
    
        convidado.setEvento(evento);
      
        this.convidadoRepository.save(convidado);

        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso");

        return "redirect:/{codigo}";
    } 

    attributes.addFlashAttribute("mensagem", "Verifique os campos");

    return "redirect:/{codigo}";
   
  }

  @RequestMapping("/deletarConvidado")
  public String deletarConvidado(String rg) {
    Convidado convidado = this.convidadoRepository.findByRg(rg);
    this.convidadoRepository.delete(convidado);

    Evento evento = convidado.getEvento();
    long codigoLongEvento  = evento.getCodigo();
    String codigo = "" + codigoLongEvento;

    return "redirect:/" + codigo;
  }

}
