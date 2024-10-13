package br.com.sigep.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.sigep.exception.VendaException;
import br.com.sigep.model.Venda;

@Repository
public class VendaProdutoRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void criar(Venda venda) {
		try {
			
			venda.getVendaProdutos().forEach(vendaProduto -> {

				final String sql = "INSERT INTO venda_produto (venda_id, produto_id, quantidade) VALUES (?,?,?)";
				this.jdbcTemplate.update(sql, 
						venda.getId(),
						vendaProduto.getProduto().getId(),
						vendaProduto.getQuantidade());
			});
			
        } catch (DataAccessException e) {
            throw new VendaException("Erro ao inserir venda_produto ");
        }
	}
	
	public boolean excluir(Integer vendaId) {
		boolean resultado = false;
		try {

			final String sql = "DELETE FROM venda_produto WHERE venda_id = ?";
			resultado = (this.jdbcTemplate.update(sql, vendaId) > 0);
			
        } catch (DataAccessException e) {
            throw new VendaException("Erro ao excluir venda_produto ");
        }
		return resultado;
	}
	
	
}
