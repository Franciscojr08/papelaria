package papelaria.ideal.api.cliente.records;

import papelaria.ideal.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCliente(
        @NotNull
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        DadosEndereco endereco,
        Boolean responsavelAluno
) {

}
