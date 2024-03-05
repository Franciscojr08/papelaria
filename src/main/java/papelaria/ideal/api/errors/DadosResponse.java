package papelaria.ideal.api.errors;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DadosResponse {

	private String dataHora;
	private String tipo;
	private Integer status;
	private String mensagem;
	private List<DadosErrosValidacao> dados;

	public      DadosResponse(LocalDateTime dataHora, String tipo, Integer status, String mensagem) {
		this.dataHora = dataHora.toString();
		this.tipo = tipo;
		this.status = status;
		this.mensagem = mensagem;
	}
}
