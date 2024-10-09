package br.com.sigep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.model.Produto;
import br.com.sigep.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto criar(Produto produto) throws RuntimeException{
		return produtoRepository.criar(produto);
	}
	
	@Transactional
	public Produto alterar(Produto produto) throws RuntimeException {
		return produtoRepository.alterar(produto);
	}
	
	@Transactional(readOnly = true)
	public List<Produto> listar() throws RuntimeException {
		return produtoRepository.listar();
	}
	
	@Transactional
	public boolean excluir(Integer id) throws RuntimeException {
		return produtoRepository.excluir(id);
	}
	
}
