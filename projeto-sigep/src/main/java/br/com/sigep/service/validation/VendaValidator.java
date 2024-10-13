package br.com.sigep.service.validation;

import java.math.BigDecimal;

import br.com.sigep.exception.VendaException;
import br.com.sigep.model.Venda;

public class VendaValidator {

	//Existe validação no front-end, mas por segurança é feito 
	//as mesmas validaçoes tambem no back-end
	public static void validar(Venda venda) {
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
		if((venda.getId() == null || venda.getId() == 0) && 
				venda.getVendaProdutos().stream().anyMatch(vp -> vp.getQuantidade() > vp.getProduto().getQuantidadeDisponivel())) {
			throw new VendaException("Existe produto(s) com a quantidade informada, maior que o saldo disponivel para venda!");
		}
	}
}
