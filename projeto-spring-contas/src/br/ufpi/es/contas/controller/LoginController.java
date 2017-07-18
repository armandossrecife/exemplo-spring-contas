package br.ufpi.es.contas.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import br.ufpi.es.contas.dao.*;
import br.ufpi.es.contas.modelo.Usuario;

@Controller
public class LoginController{
	UsuarioDAO dao;
	
	@Autowired //permite a resolução de dependência via injeção de dependência
	public LoginController(UsuarioDAO dao){
		this.dao = dao;
	}
	
	/**
	 * no primeiro GET retorna o formulário de login da aplicação
	 * @return página formulario-login.jsp
	 */
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "usuario/formulario-login";
	}

	/**
	 * Recebe os dados enviados do formulário de login e testa se é um usuário registrado e válido
	 * @param usuario Usuario passado no formulário de login
	 * @param session Sessao - cria uma sessão do usuário
	 * @return página menu.jsp se o usuário é válido, página formulario-login.jsp caso não seja um usuário válido
	 */
	@RequestMapping("/efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpSession session) {
		if(dao.existeUsuario(usuario)) {
			// usuario existe, guardaremos ele na session
			session.setAttribute("usuarioLogado", usuario);
			return "menu";
		}
		// ele errou a senha, voltou para o formulario
		return "usuario/formulario-login";
	}
}