package br.com.sigep.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.sigep.model.Venda;

@Repository
public class VendaRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Venda criar(Venda venda) {
		try {
			final String sql = "INSERT INTO venda (cliente, valor_total) VALUES (?,?)";
			
			//Objeto que vai armazenar o id gerado apos o insert
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			this.jdbcTemplate.update(connection -> {
	                PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	                ps.setString(1, venda.getCliente());
	                ps.setBigDecimal(2, venda.getValorTotal());
	                return ps;
	            }, keyHolder);
			
			//setando o id gerado
			venda.setId(keyHolder.getKey().intValue());
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao inserir venda ", e);
        }
		return venda;
	}
	
	public Venda alterar(Venda venda) {
		try {
			
			final String sql = "UPDATE venda SET cliente = ?, valor_total = ? WHERE id = ?"; 
			
			this.jdbcTemplate.update(sql,
					venda.getCliente(),
					venda.getValorTotal(), 
					venda.getId());
			
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao alterar venda ", e);
        }
		return venda;
	}
	
	public List<Venda> listar(){
		List<Venda> vendas = null;
		try {
	        
			final String sql = "SELECT id, cliente, valor_total FROM venda";
			
			vendas = this.jdbcTemplate.query(sql, (rs, linha) -> {
										Venda venda = new Venda();
										venda.setId(rs.getInt("id"));
										venda.setCliente(rs.getString("cliente"));
										venda.setValorTotal(rs.getBigDecimal("valor_total"));
										return venda;
									}); 
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao listar vendas ", e);
        }
		return vendas;
	}
	
	public boolean excluir(Integer id) {
		boolean resultado = false;
		try {

			final String sql = "DELETE FROM venda WHERE id = ?";
			resultado = (this.jdbcTemplate.update(sql, id) > 0);
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao excluir venda ", e);
        }
		return resultado;
	}
	
	
	
	public Venda consultar(Integer id) {
		Venda venda = null;
		try {
			final String sql = "SELECT id, cliente, valor_total FROM produto WHERE id = ?";
			venda = this.jdbcTemplate.queryForObject(sql, (rs, linha) -> {
				Venda vendaResultado = new Venda();
				vendaResultado.setId(rs.getInt("id"));
				vendaResultado.setCliente(rs.getString("cliente"));
				vendaResultado.setValorTotal(rs.getBigDecimal("valor_total"));
				return vendaResultado;
			}, id); 
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao consultar venda ", e);
        }
		return venda;
	}
	
}
