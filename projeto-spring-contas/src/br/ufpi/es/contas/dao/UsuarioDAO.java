package br.ufpi.es.contas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufpi.es.contas.modelo.Usuario;

@Repository //Padrão repositório para permitir resolução da Injeção de Dependência
public class UsuarioDAO {
	private Connection connection;

	@Autowired //Injeção de Dependência do DataSource registrado no struts-content
	public UsuarioDAO(DataSource ds) {
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checa se o usuário existe
	 * @param usuario Usuario 
	 * @return TRUE se existe
	 */
	public boolean existeUsuario(Usuario usuario) {
		
		if(usuario == null) {
			throw new IllegalArgumentException("Usuário nao deve ser nulo");
		}
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement("select * from usuarios where login = ? and senha = ?");
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			ResultSet rs = stmt.executeQuery();

			boolean encontrado = rs.next();
			rs.close();
			stmt.close();
			
			return encontrado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Insere um novo Usuario
	 * @param usuario Usuario
	 */
	public void insere(Usuario usuario) {
		if(usuario == null) {
			throw new IllegalArgumentException("Usuário nao deve ser nulo");
		}
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement("insert into usuarios (login,senha) values (?,?)");
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}
}