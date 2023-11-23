package papelaria.ideal.api.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarError404() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErrorValidacao(MethodArgumentNotValidException exp) {
		var erros = exp.getFieldErrors();

		return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity tratarErro400(HttpMessageNotReadableException exp) {
		return ResponseEntity.badRequest().body(
				"Algum campo preenchido está com o formato inválido. Verifique os campos e tente novamente!"
		);
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity tratarErro500(Exception exp) {
//		return ResponseEntity.internalServerError().body("Desculpe-me, ocorreu um erro interno no sistema!");
//	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
