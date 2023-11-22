package papelaria.ideal.api.listaPendencia;

import java.util.List;

public record DadosCadastroLivroKitLivro(
        List<DadosCadastroPendenciaLivroKitLivro> livros,
        List<DadosCadastroPendenciaLivroKitLivro> kitLivros
) {
}
