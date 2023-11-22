package papelaria.ideal.api.Turma;

public record DadosCadastroTurma(
        @NotBlank(message = "A série não pode estar em branco")
        private String serie;

        @NotBlank(message = "O nome da turma não pode estar em branco")
        private String nome;
) {
}
