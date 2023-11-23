package papelaria.ideal.api.cliente;

import papelaria.ideal.api.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroCliente(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,
        @NotBlank
        String telefone,
        @NotBlank
        String email,
        @NotNull
        @Valid
        DadosEndereco endereco,
        Boolean responsavelAluno
) {
}

