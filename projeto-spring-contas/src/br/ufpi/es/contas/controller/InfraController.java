package br.ufpi.es.contas.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufpi.es.contas.ConnectionFactory;

@Controller
public class InfraController {

	@RequestMapping("/tabelas")
	public String criaBanco() throws SQLException {
		String dropTabelaContas = "drop table if exists contas";
		String dropTabelaUsuarios = "drop table if exists usuarios";		
		String criaTabelaContas = "" +
				"CREATE TABLE `bancocontas`.`contas` ("+
					"`id` INT NOT NULL,"+
					"`descricao` VARCHAR(255) NULL,"+
					"`valor` DOUBLE NULL,"+
					"`paga` INT NULL,"+
					"`datapagamento` DATETIME NULL,"+
					"`tipo` VARCHAR(45) NULL,"+
					"PRIMARY KEY (`id`));";
		String criaTabelaUsuarios = "" +
				"CREATE TABLE `bancocontas`.`usuarios` ("+
				"`login` VARCHAR(45) NOT NULL,"+
				"`senha` VARCHAR(45) NULL,"+
				"PRIMARY KEY (`login`));";
		String insereNovoUsuario = "insert into usuarios (login, senha) values ('armando', 'online');";

		Connection c = new ConnectionFactory().getConnection();
		
		PreparedStatement st1 = c.prepareStatement(dropTabelaContas);
		st1.execute();
		PreparedStatement st11 = c.prepareStatement(criaTabelaContas);
		st11.execute();
		PreparedStatement st2 = c.prepareStatement(dropTabelaUsuarios);
		st2.execute();
		PreparedStatement st22 = c.prepareStatement(criaTabelaUsuarios); 
		st22.execute();
		PreparedStatement st3 = c.prepareStatement(insereNovoUsuario);
		st3.execute();
		
		c.close();
		
		System.out.println("Tabelas criadas com sucesso!");
		
		return "infra-ok";

	}
}
