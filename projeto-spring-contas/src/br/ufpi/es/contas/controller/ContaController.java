package br.ufpi.es.contas.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufpi.es.contas.dao.ContaDAO;
import br.ufpi.es.contas.modelo.Conta;

@Controller
public class ContaController {

	@RequestMapping("/formularioConta")
	public String carregaFormulario(){
		return "conta/formulario";
	}
	
	@RequestMapping("/adicionaConta")
	public String adiciona(@Valid Conta conta, BindingResult resultadoValidacao){
		
		if (resultadoValidacao.hasErrors()){
			List<ObjectError> listaErros = resultadoValidacao.getAllErrors();
			for (ObjectError item : listaErros){
				System.out.println("Erro: " + item.toString());
			}
			return("conta/formulario");
		}
		
		ContaDAO dao = new ContaDAO();
		dao.adiciona(conta);
		System.out.println("Conta " + conta.getDescricao() + " adiconada com sucesso!");
		
		return "conta/conta-adicionada";
	}
	
	@RequestMapping("/listaContas")
	public String lista(Model mv) {
	  ContaDAO dao = new ContaDAO();
	  List<Conta> contas = dao.lista();

	  mv.addAttribute("contas", contas);
	  return "conta/lista";
	}
	
	@RequestMapping("/removeConta")
	public String remove(Conta conta) {
	  ContaDAO dao = new ContaDAO();
	  dao.remove(conta);
	  //rediceriona para o recurso "listaContas" do ContaController
	  return "redirect:listaContas";
	}
	
	@RequestMapping("/mostraConta")
	public String mostra(Long id, Model model) {
	  ContaDAO dao = new ContaDAO();
	  model.addAttribute("conta", dao.buscaPorId(id));
	  return "conta/mostra";
	}
	
	@RequestMapping("/alteraConta")
	public String altera(Conta conta) {
	  ContaDAO dao = new ContaDAO();
	  dao.altera(conta);
	  return "redirect:listaContas";
	}
	
	@RequestMapping("/pagaConta")
	public void paga(Long id, HttpServletResponse response) {
	  ContaDAO dao = new ContaDAO();
	  dao.paga(id);
	  response.setStatus(200);
	}	
}
