package br.com.sigep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.model.Venda;
import br.com.sigep.repository.VendaProdutoRepository;

@Service
public class VendaProdutoService {

	@Autowired
	private VendaProdutoRepository vendaProdutoRepository;
	
	@Transactional
	public void criar(Venda venda) throws RuntimeException{
		vendaProdutoRepository.criar(venda);
	}
	
	@Transactional
	public boolean excluir(Integer id) throws RuntimeException {
		return vendaProdutoRepository.excluir(id);
	}
}
