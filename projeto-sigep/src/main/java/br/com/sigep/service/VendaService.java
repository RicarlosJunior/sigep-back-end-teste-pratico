package br.com.sigep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.model.Venda;
import br.com.sigep.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Transactional
	public Venda criar(Venda venda) throws RuntimeException{
		if(venda.getVendaProdutos() == null || venda.getVendaProdutos().size() == 0) {
			throw new IllegalArgumentException("Informe ao menos um produto!");
		}
		return vendaRepository.criar(venda);
	}
	
	@Transactional
	public Venda alterar(Venda venda) throws RuntimeException {
		if(venda.getVendaProdutos() == null || venda.getVendaProdutos().size() == 0) {
			throw new IllegalArgumentException("Informe ao menos um produto!");
		}
		return vendaRepository.alterar(venda);
	}
	
	@Transactional(readOnly = true)
	public List<Venda> listar() throws RuntimeException {
		return vendaRepository.listar();
	}
	
	@Transactional
	public String excluir(Integer id) throws RuntimeException {
		boolean sucesso = vendaRepository.excluir(id);
		if(sucesso) {
			return "Registro excluido com sucesso!";
		}else {
			return "Não foi possivel realizar essa operação!";
		}
	}
	
	@Transactional(readOnly = true)
	public Venda consultar(Integer id) throws RuntimeException {
		return vendaRepository.consultar(id);
	}
	
}
