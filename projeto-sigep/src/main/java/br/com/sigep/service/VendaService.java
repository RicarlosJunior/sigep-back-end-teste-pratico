package br.com.sigep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.exception.VendaException;
import br.com.sigep.model.Venda;
import br.com.sigep.repository.VendaRepository;
import br.com.sigep.service.validation.VendaValidator;

@Service
public class VendaService {
	
	@Autowired
	private VendaProdutoService vendaProdutoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	private final static String SOMAR_QTDE_DISPONIVEL = "+";
	private final static String SUBTRAIR_QTDE_DISPONIVEL = "-";
	
	
	@Transactional
	public Venda criar(Venda venda) throws RuntimeException{
		
		VendaValidator.validar(venda);
		
		venda = vendaRepository.criar(venda);
	
		vendaProdutoService.criar(venda);
		
		produtoService.atualizarQuantidadeDisponivelProduto(venda, SUBTRAIR_QTDE_DISPONIVEL);
		
		return venda;
	}
	
	@Transactional
	public Venda alterar(Integer id, Venda venda) throws RuntimeException {
		
		VendaValidator.validar(venda);
		
		Venda vendaBD = vendaRepository.consultar(id);
		
		if(vendaBD == null) {
			throw new VendaException("Venda não encontrada!");
		}
		
		//As duas ações a seguir sao para evitar furos na "quantidade disponivel da tabela produto"
		//primeiro é devolvido a quantidade disponivel de cada produto
		//depois é feita a exclusão da relação venda - produto do antigo registro.
		//representado pelo objeto vendaDB
		produtoService.atualizarQuantidadeDisponivelProduto(vendaBD, SOMAR_QTDE_DISPONIVEL);
		
		vendaProdutoService.excluir(vendaBD.getId());
		
		vendaRepository.alterar(venda);
		
		vendaProdutoService.criar(venda);
		
		produtoService.atualizarQuantidadeDisponivelProduto(venda, SUBTRAIR_QTDE_DISPONIVEL);
		
		return venda;
	}
	
	@Transactional(readOnly = true)
	public List<Venda> listar() throws RuntimeException {
		return vendaRepository.listar();
	}
	
	@Transactional
	public String excluir(Integer id) throws RuntimeException {
		
		Venda vendaBD = vendaRepository.consultar(id);
		
		if(vendaBD != null) {
			produtoService.atualizarQuantidadeDisponivelProduto(vendaBD, SOMAR_QTDE_DISPONIVEL);
		}
		
		vendaProdutoService.excluir(id);
		 
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
