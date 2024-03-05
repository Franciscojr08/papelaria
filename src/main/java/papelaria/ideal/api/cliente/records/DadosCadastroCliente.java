package papelaria.ideal.api.cliente.records;

import papelaria.ideal.api.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCliente(
        @NotBlank(message = "O nome do cliente não pode estar em branco")
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String telefone,
        @NotBlank
        String email,
        @NotNull
        @Valid
        DadosEndereco endereco,
        @NotNull

        Boolean responsavelAluno
) {
}

