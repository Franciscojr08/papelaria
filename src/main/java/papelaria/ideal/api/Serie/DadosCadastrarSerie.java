package papelaria.ideal.api.Serie;

@Data
@NoArgsConstructor
public class DadosCadastrarSerie {
    private String nome;

    public DadosCadastrarSerie(String nome) {
        this.nome = nome;
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da série não ficar vazio.");
        }
    }
}
