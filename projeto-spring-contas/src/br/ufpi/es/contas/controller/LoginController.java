package br.ufpi.es.contas.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import br.ufpi.es.contas.dao.*;
import br.ufpi.es.contas.modelo.Usuario;

@Controller
public class LoginController{
  @RequestMapping("/loginForm")
  public String loginForm() {
    return "usuario/formulario-login";
  }

  @RequestMapping("/efetuaLogin")
  public String efetuaLogin(Usuario usuario, HttpSession session) {
    if(new UsuarioDAO().existeUsuario(usuario)) {
      // usuario existe, guardaremos ele na session
      session.setAttribute("usuarioLogado", usuario);
      return "menu";
    }
    // ele errou a senha, voltou para o formulario
    return "usuario/formulario-login";
  }
}