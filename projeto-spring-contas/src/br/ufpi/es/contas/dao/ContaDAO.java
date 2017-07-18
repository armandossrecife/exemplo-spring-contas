package br.ufpi.es.contas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufpi.es.contas.modelo.Conta;
import br.ufpi.es.contas.modelo.TipoDaConta;

@Repository //Padrão repositório para permitir resolução da Injeção de Dependência
public class ContaDAO {
	private Connection connection;

	@Autowired //Injeção de Dependência do DataSource registrado no struts-content
	public ContaDAO(DataSource ds) {
		try {
			this.connection = ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adiciona uma nova Conta
	 * @param conta Conta
	 */
	public void adiciona(Conta conta) {
		String sql = "insert into contas (descricao, paga, valor, tipo) values (?,?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, conta.getDescricao());
			stmt.setBoolean(2, conta.isPaga());
			stmt.setDouble(3, conta.getValor());
			stmt.setString(4, conta.getTipo().name());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Remove uma Conta
	 * @param conta Conta
	 */
	public void remove(Conta conta) {

		if (conta.getId() == null) {
			throw new IllegalStateException("Id da conta naoo deve ser nula.");
		}

		String sql = "delete from contas where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, conta.getId());
			stmt.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Dada uma Conta existente faz a alteração da mesma
	 * @param conta Conta com novos dados
	 */
	public void altera(Conta conta) {
		String sql = "update contas set descricao = ?, paga = ?, dataPagamento = ?, tipo = ?, valor = ? where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, conta.getDescricao());
			stmt.setBoolean(2, conta.isPaga());
			stmt.setDate(3, conta.getDataPagamento() != null ? new Date(conta
					.getDataPagamento().getTimeInMillis()) : null);
			stmt.setString(4, conta.getTipo().name());
			stmt.setDouble(5, conta.getValor());
			stmt.setLong(6, conta.getId());
			stmt.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna uma lista de Contas
	 * @return Lista de Contas
	 */
	public List<Conta> lista() {
		try {
			List<Conta> contas = new ArrayList<Conta>();
			PreparedStatement stmt = this.connection.prepareStatement("select * from contas");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// adiciona a conta na lista
				contas.add(populaConta(rs));
			}

			rs.close();
			stmt.close();

			return contas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Dado um id de uma Conta faz a busca da Conta
	 * @param id identificador da Conta
	 * @return conta Conta
	 */
	public Conta buscaPorId(Long id) {

		
		if (id == null) {
			throw new IllegalStateException("Id da conta nao deve ser nula.");
		}

		try {
			PreparedStatement stmt = this.connection.prepareStatement("select * from contas where id = ?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return populaConta(rs);
			}

			rs.close();
			stmt.close();
			
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Dada uma Conta com id fornecido, caso a Conta não esteja paga muda o status para pago
	 * @param id id da Conta
	 */
	public void paga(Long id) {

		if (id == null) {
			throw new IllegalStateException("Id da conta nao deve ser nula.");
		}

		String sql = "update contas set paga = ?, dataPagamento = ? where id = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setBoolean(1, true);
			stmt.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			stmt.setLong(3, id);
			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Popula dados da Conta 
	 * @param rs ResultSet
	 * @return conta Conta
	 * @throws SQLException caso ocorra alguma Exceção SQL
	 */
	private Conta populaConta(ResultSet rs) throws SQLException {
		Conta conta = new Conta();

		conta.setId(rs.getLong("id"));
		conta.setDescricao(rs.getString("descricao"));
		conta.setPaga(rs.getBoolean("paga"));
		conta.setValor(rs.getDouble("valor"));

		Date data = rs.getDate("dataPagamento");
		if (data != null) {
			Calendar dataPagamento = Calendar.getInstance();
			dataPagamento.setTime(data);
			conta.setDataPagamento(dataPagamento);
		}
		
		conta.setTipo(Enum.valueOf(TipoDaConta.class, rs.getString("tipo")));
		
		return conta;
	}
}