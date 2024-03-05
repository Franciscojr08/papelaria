package papelaria.ideal.api.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratarErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<DadosResponse> tratarError404(EntityNotFoundException ex) {
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Bad Request",
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
		);

		return ResponseEntity.badRequest().body(dadosResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DadosResponse> tratarErrorValidacao(MethodArgumentNotValidException exp) {
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Bad Request",
				HttpStatus.BAD_REQUEST.value(),
				"Algum campo obrigatório não foi preenchido!"
		);
		dadosResponse.setDados(exp.getFieldErrors().stream().map(DadosErrosValidacao::new).toList());

		return ResponseEntity.badRequest().body(dadosResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DadosResponse> tratarErro400(HttpMessageNotReadableException exp) {
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Bad Request",
				HttpStatus.BAD_REQUEST.value(),
				"Algum campo preenchido está com o formato inválido. Verifique os campos e tente novamente!"
		);

		return ResponseEntity.badRequest().body(dadosResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<DadosResponse> tratarErro500(Exception exp) {
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Internal Server Error",
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Ocorreu um erro interno no sistema!"
		);

		return ResponseEntity.internalServerError().body(dadosResponse);
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<DadosResponse> tratarErroRegraDeNegocio(ValidacaoException ex) {
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Bad Request",
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
		);

		return ResponseEntity.badRequest().body(dadosResponse);
	}
}