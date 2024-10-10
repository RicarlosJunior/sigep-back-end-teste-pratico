package br.com.sigep.exception;

public class ProdutoException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public ProdutoException(String mensagemException) {
        super(mensagemException);
    }
}