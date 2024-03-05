package papelaria.ideal.api.kitLivro.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroKitLivro(
		@NotBlank
		String nome,
		String descricao,
		@NotNull
		Float valor,
		@NotNull
		Long quantidadeDisponivel
) {
}
