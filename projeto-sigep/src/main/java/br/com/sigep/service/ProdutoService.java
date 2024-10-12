package br.com.sigep.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.exception.ProdutoException;
import br.com.sigep.model.Produto;
import br.com.sigep.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto criar(Produto produto) throws RuntimeException{
		
		validarCamposProduto(produto);
		
		return produtoRepository.criar(produto);
	}
	
	@Transactional
	public Produto alterar(Integer id, Produto produto) throws RuntimeException {
		
		//Verifica se o registro exite
		if(consultar(id) == null) {
			throw new ProdutoException("Produto não encontrado!");
		}
		
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
			throw new ProdutoException("Não foi possivel realizar essa operação!");
		}
	}
	
	@Transactional(readOnly = true)
	public Produto consultar(Integer id) throws RuntimeException {
		return produtoRepository.consultar(id);
	}
	
	//Existe validação no front-end, mas por segurança é feito as mesmas validaçoes tambem no back-end
	private void validarCamposProduto(Produto produto) {
		if(produto.getNome() == null || produto.getNome().isBlank()) {
			throw new ProdutoException("Campo nome inválido!");
		}
		if(produto.getQuantidadeDisponivel() == null || produto.getQuantidadeDisponivel() <= 0) {
			throw new ProdutoException("Campo quantidade disponível inválido!");
		}
		if(produto.getValorUnitario() == null || produto.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ProdutoException("Campo valor unitario inválido!");
		}
	}

}
