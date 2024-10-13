package br.com.sigep.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sigep.exception.VendaException;
import br.com.sigep.model.Venda;
import br.com.sigep.repository.ProdutoRepository;
import br.com.sigep.repository.VendaProdutoRepository;
import br.com.sigep.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private VendaProdutoRepository vendaProdutoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private final static String SOMAR_QTDE_DISPONIVEL = "+";
	private final static String SUBTRAIR_QTDE_DISPONIVEL = "-";
	
	
	@Transactional
	public Venda criar(Venda venda) throws RuntimeException{
		
		validarCamposVenda(venda);
		
		venda = vendaRepository.criar(venda);
	
		vendaProdutoRepository.criar(venda);
		
		atualizarQuantidadeDisponivelProduto(venda, SUBTRAIR_QTDE_DISPONIVEL);
		
		return venda;
	}
	
	@Transactional
	public Venda alterar(Integer id, Venda venda) throws RuntimeException {
		
		validarCamposVenda(venda);
		
		Venda vendaBD = vendaRepository.consultar(id);
		
		if(vendaBD == null) {
			throw new VendaException("Venda não encontrada!");
		}
		
		//As duas ações a seguir sao para evitar furos
		//primeiro é devolvido a quantidade disponivel de cada produto
		//depois é feita a exclusão da relação venda - produto do antigo registro.
		//representado pelo objeto vendaDB
		atualizarQuantidadeDisponivelProduto(vendaBD, SOMAR_QTDE_DISPONIVEL);
		
		vendaProdutoRepository.excluir(vendaBD.getId());
		
		vendaRepository.alterar(venda);
		
		vendaProdutoRepository.criar(venda);
		
		atualizarQuantidadeDisponivelProduto(venda, SUBTRAIR_QTDE_DISPONIVEL);
		
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
			atualizarQuantidadeDisponivelProduto(vendaBD, SOMAR_QTDE_DISPONIVEL);
		}
		
		vendaProdutoRepository.excluir(id);
		 
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
	
	
	
	private void atualizarQuantidadeDisponivelProduto(Venda venda, String operacao) {
	    venda.getVendaProdutos().forEach(vp -> 
	        produtoRepository.atualizarQuantidadeDisponivel(vp.getQuantidade(), vp.getProduto().getId(), operacao)
	    );
	}
	

	//Existe validação no front-end, mas por segurança é feito 
	//as mesmas validaçoes tambem no back-end
	private void validarCamposVenda(Venda venda) {
		if(venda.getCliente() == null || venda.getCliente().isBlank()) {
			throw new VendaException("Campo cliente inválido!");
		}
		if(venda.getValorTotal() == null || venda.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new VendaException("Campo valor total inválido!");
		}
		if(venda.getVendaProdutos() == null || venda.getVendaProdutos().size() == 0) {
			throw new VendaException("Para gerar/alterar uma venda é necessario informar ao menos um produto!");
		}
		if(venda.getVendaProdutos().stream().anyMatch(vp -> vp.getQuantidade() <= 0)) {
			throw new VendaException("Existe produto(s) com quantidade informada inválida!");
		}
		if(venda.getVendaProdutos().stream().anyMatch(vp -> vp.getQuantidade() > vp.getProduto().getQuantidadeDisponivel())) {
			throw new VendaException("Existe produto(s) com a quantidade informada, maior que o saldo disponivel para venda!");
		}
	}
}
