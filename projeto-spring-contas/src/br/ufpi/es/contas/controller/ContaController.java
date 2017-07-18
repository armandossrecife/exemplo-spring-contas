package br.ufpi.es.contas.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufpi.es.contas.dao.ContaDAO;
import br.ufpi.es.contas.modelo.Conta;

@Controller //Define como padrão Controlador para permitir a resolução da Injeção de Dependência
public class ContaController {
	private ContaDAO dao;
	
	@Autowired //resolve a dependência via injeção de dependência
	public ContaController(ContaDAO dao){
		this.dao = dao;
	}

	/**
	 * Carrega o formulário de cadastro de nova Conta
	 * @return página formulario.jsp
	 */
	@RequestMapping("/formularioConta")
	public String carregaFormulario(){
		return "conta/formulario";
	}
	
	/**
	 * Adiciona uma nova conta e faz a validação dos dados da Conta
	 * @param conta Conta
	 * @param resultadoValidacao checa se os dados da Conta são válidos
	 * @return página formulario.jsp
	 */
	@RequestMapping("/adicionaConta")
	public String adiciona(@Valid Conta conta, BindingResult resultadoValidacao){
		
		if (resultadoValidacao.hasErrors()){
			List<ObjectError> listaErros = resultadoValidacao.getAllErrors();
			for (ObjectError item : listaErros){
				System.out.println("Erro: " + item.toString());
			}
			return("conta/formulario");
		}
		dao.adiciona(conta);
		System.out.println("Conta " + conta.getDescricao() + " adiconada com sucesso!");
		
		return "conta/conta-adicionada";
	}
	
	/**
	 * Lista de Contas cadastradas
	 * @param mv Model para passar informações para a view
	 * @return página lista.jsp
	 */
	@RequestMapping("/listaContas")
	public String lista(Model mv) {
	  List<Conta> contas = dao.lista();

	  mv.addAttribute("contas", contas);
	  return "conta/lista";
	}
	
	/**
	 * Remove uma Conta dada
	 * @param conta Conta
	 * @return redireciona para a action listaContas para listar as Contas cadastradas e atualizadas
	 */
	@RequestMapping("/removeConta")
	public String remove(Conta conta) {
	  dao.remove(conta);
	  //rediceriona para o recurso "listaContas" do ContaController
	  return "redirect:listaContas";
	}
	
	/**
	 * Dada uma Conta mostra os detalhes da Conta
	 * @param id Id da Conta
	 * @param model Model para passar informações para a view 
	 * @return página mostra.jsp
	 */
	@RequestMapping("/mostraConta")
	public String mostra(Long id, Model model) {
	  model.addAttribute("conta", dao.buscaPorId(id));
	  return "conta/mostra";
	}
	
	/**
	 * Dada uma Conta altera os dados
	 * @param conta Conta com os novos dados
	 * @return redireciona para a action listaContas para listar as Contas cadastradas e atualizadas
	 */
	@RequestMapping("/alteraConta")
	public String altera(Conta conta) {
	  dao.altera(conta);
	  return "redirect:listaContas";
	}
	
	/**
	 * Dada uma conta muda o status de pagamento para paga
	 * @param id Id da Conta
	 * @param response faz a mudança de status da conta para paga e retorna 200 para a requisição AJAX
	 */
	@RequestMapping("/pagaConta")
	public void paga(Long id, HttpServletResponse response) {
	  dao.paga(id);
	  response.setStatus(200);
	}	
}