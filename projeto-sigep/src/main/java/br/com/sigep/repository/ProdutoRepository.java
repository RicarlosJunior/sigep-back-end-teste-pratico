package br.com.sigep.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.sigep.model.Produto;

@Repository
public class ProdutoRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Produto criar(Produto produto) {
		try {
			final String sql = "INSERT INTO produto (nome, descricao, quantidade_disponivel, valor_unitario) VALUES (?,?,?,?)";
			
			//Objeto que vai armazenar o id gerado apos o insert
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			this.jdbcTemplate.update(connection -> {
	                PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	                ps.setString(1, produto.getNome());
	                ps.setString(2, produto.getDescricao());
	                ps.setInt(3, produto.getQuantidadeDisponivel());
	                ps.setBigDecimal(4, produto.getValorUnitario());
	                return ps;
	            }, keyHolder);
			
			//setando o id gerado
			produto.setId(keyHolder.getKey().intValue());
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao inserir produto ", e);
        }
		return produto;
	}
	
	public Produto alterar(Produto produto) {
		try {
			
			final String sql = "UPDATE produto SET nome = ?, descricao = ?, quantidade_disponivel = ?, valor_unitario = ? WHERE id = ?"; 
			
			this.jdbcTemplate.update(sql,
					produto.getNome(),
					produto.getDescricao(),
					produto.getQuantidadeDisponivel(),
					produto.getValorUnitario(), 
					produto.getId());
			
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao alterar produto ", e);
        }
		return produto;
	}
	
	public List<Produto> listar(){
		List<Produto> produtos = null;
		try {
	        
			final String sql = "SELECT id, nome, descricao, quantidade_disponivel, valor_unitario FROM produto";
			
			produtos = this.jdbcTemplate.query(sql, (rs, linha) -> {
										Produto produto = new Produto();
										produto.setId(rs.getInt("id"));
										produto.setNome(rs.getString("nome"));
										produto.setDescricao(rs.getString("descricao"));
										produto.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
										produto.setValorUnitario(rs.getBigDecimal("valor_unitario"));
										return produto;
									}); 
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao listar produto ", e);
        }
		return produtos;
	}
	
	public boolean excluir(Integer id) {
		boolean resultado = false;
		try {
			
			if(existeVendaCadastradaParaProduto(id)) {
				throw new RuntimeException("Produto não pode ser excluido pois está vinculado a uma venda!");
			}
			
			final String sql = "DELETE FROM produto WHERE id = ?";
			resultado = (this.jdbcTemplate.update(sql, id) > 0);
			
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao excluir produto ", e);
        }
		return resultado;
	}
	
	
	private boolean existeVendaCadastradaParaProduto(Integer produtoId) {
		final String sql = "SELECT COUNT(*) FROM venda_produto WHERE produto_id = ? ";
		Integer qtdVendaProduto = this.jdbcTemplate.queryForObject(sql, Integer.class, produtoId);
		return (qtdVendaProduto != null && qtdVendaProduto > 0);
	}
}
