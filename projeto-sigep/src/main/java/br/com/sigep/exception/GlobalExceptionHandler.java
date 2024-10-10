package br.com.sigep.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger  = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(ProdutoException.class)
    public ResponseEntity<String> handleProdutoException(ProdutoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

   
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleGlobalException(Throwable e) {
    	String mensagem = "Ocorreu um erro inesperado: ";
	    logger.error(mensagem, e);
        return new ResponseEntity<>(mensagem + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}