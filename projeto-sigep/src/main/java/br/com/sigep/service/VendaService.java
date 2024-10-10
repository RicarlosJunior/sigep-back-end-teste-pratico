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
	
	
	@Transactional
	public Venda criar(Venda venda) throws RuntimeException{
		
		//Por segurança valida os compos de acordo com a regra de negoico
		validarCamposVenda(venda);
		
		//Cria a venda
		venda = vendaRepository.criar(venda);
		
		//Cria o relacionamento de venda e produto
		vendaProdutoRepository.criar(venda);
		
		//Baixa a quantidade disponivel na tb produto
		baixarQuatidadeDisponivelProduto(venda);
		
		return venda;
	}
	
	@Transactional
	public Venda alterar(Venda venda) throws RuntimeException {
		
		//Por segurança valida os compos de acordo com a regra de negoico
		validarCamposVenda(venda);
		
		//Para evitar furos devolve a quantidade disponivel do registro antes da alteração
		devolverQuatidadeDisponivelProduto(venda.getId());
		
		//Excluir o relacionamento que existia antes da alteração entre venda e produto
		vendaProdutoRepository.excluir(venda.getId());
		
		//Faz a alteraçao na venda
		venda = vendaRepository.alterar(venda);

		//Cria novamente o relacionamento da venda e produto
		vendaProdutoRepository.criar(venda);
		
		//Baixa a quantidade disponivel na tabela produto
		baixarQuatidadeDisponivelProduto(venda);
		
		return venda;
	}
	
	@Transactional(readOnly = true)
	public List<Venda> listar() throws RuntimeException {
		return vendaRepository.listar();
	}
	
	@Transactional
	public String excluir(Integer id) throws RuntimeException {
		
		//Devolve a quantidade disponivel para tb produto
		devolverQuatidadeDisponivelProduto(id);
		
		boolean sucesso = vendaRepository.excluir(id);
		
		if(sucesso) {
			
			vendaProdutoRepository.excluir(id);
			
			return "Registro excluido com sucesso!";
			
		}else {
			return "Não foi possivel realizar essa operação!";
		}
	}
	
	@Transactional(readOnly = true)
	public Venda consultar(Integer id) throws RuntimeException {
		return vendaRepository.consultar(id);
	}
	
	
	private void devolverQuatidadeDisponivelProduto(Integer id) {
		
		Venda vendaBD = vendaRepository.consultar(id);
			
		String operacao = "+"; //devolver a quantidade do produto
		
		vendaBD.getVendaProdutos().stream().forEach(vp ->
			produtoRepository.atualziarQuatidadeDisponivelProduto(vp.getQuantidade(), vp.getProduto().getId(), operacao)
		);
		
	}
	
	private void baixarQuatidadeDisponivelProduto(Venda venda) {
			
		String operacao = "-"; //baixar a quantidade do produto
		
		venda.getVendaProdutos().stream().forEach(vp ->
			produtoRepository.atualziarQuatidadeDisponivelProduto(vp.getQuantidade(), vp.getProduto().getId(), operacao)
		);
		
	}
	
	//Existe validação no front-end, mas por segurança é feito uma validação tambem no back-end
	private void validarCamposVenda(Venda venda) {
		if(venda.getCliente() == null || venda.getCliente().isBlank()) {
			throw new VendaException("Campo cliente inválido!");
		}
		if(venda.getValorTotal() == null || venda.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new VendaException("Campo valor total inválido!");
		}
		if(venda.getVendaProdutos() == null || venda.getVendaProdutos().size() == 0) {
			throw new VendaException("Para gerar uma venda e necessario informar ao menos um produto!");
		}
		if(venda.getVendaProdutos().stream().anyMatch(vp -> vp.getQuantidade() <= 0)) {
			throw new VendaException("Existe produto sem quantidade informada!");
		}
		if(venda.getVendaProdutos().stream().anyMatch(vp -> vp.getQuantidade() > vp.getProduto().getQuantidadeDisponivel())) {
			throw new VendaException("Existe produto com a quantidade informada, maior que o saldo disponivel para venda!");
		}
	}
}
