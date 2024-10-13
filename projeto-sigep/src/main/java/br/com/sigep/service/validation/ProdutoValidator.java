package br.com.sigep.service.validation;

import java.math.BigDecimal;

import br.com.sigep.exception.ProdutoException;
import br.com.sigep.model.Produto;

public class ProdutoValidator {

	
	//Existe validação no front-end, mas por segurança 
	//é feito as mesmas validaçoes tambem no back-end
	public static void validar(Produto produto) {
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
