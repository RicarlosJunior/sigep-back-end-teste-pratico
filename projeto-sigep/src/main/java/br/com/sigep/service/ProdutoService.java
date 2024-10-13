package br.com.sigep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.exception.ProdutoException;
import br.com.sigep.model.Produto;
import br.com.sigep.model.Venda;
import br.com.sigep.repository.ProdutoRepository;
import br.com.sigep.service.validation.ProdutoValidator;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto criar(Produto produto) throws RuntimeException{
		
		ProdutoValidator.validar(produto);
		
		return produtoRepository.criar(produto);
	}
	
	@Transactional
	public Produto alterar(Integer id, Produto produto) throws RuntimeException {
		
		if(produtoRepository.consultar(id) == null) {
			throw new ProdutoException("Produto não encontrado!");
		}
		
		ProdutoValidator.validar(produto);
		
		return produtoRepository.alterar(produto);
	}
	
	@Transactional(readOnly = true)
	public List<Produto> listar() throws RuntimeException {
		return produtoRepository.listar();
	}
	
	@Transactional
	public String excluir(Integer id) throws RuntimeException {
		boolean sucesso = produtoRepository.excluir(id);
		if(sucesso) {
			return "Registro excluido com sucesso!";
		}else {
			return "Não foi possivel realizar essa operação!";
		}
	}
	
	@Transactional(readOnly = true)
	public Produto consultar(Integer id) throws RuntimeException {
		return produtoRepository.consultar(id);
	}
	
	@Transactional
	public void atualizarQuantidadeDisponivelProduto(Venda venda, String operacao) {
	    venda.getVendaProdutos().forEach(vp -> 
	        produtoRepository.atualizarQuantidadeDisponivel(vp.getQuantidade(), vp.getProduto().getId(), operacao)
	    );
	}
}
