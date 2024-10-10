package br.com.sigep.exception;

public class VendaException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public VendaException(String mensagemException) {
        super(mensagemException);
    }
}