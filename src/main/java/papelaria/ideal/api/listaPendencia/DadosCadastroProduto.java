package papelaria.ideal.api.listaPendencia;

import java.util.List;

public record DadosCadastroProduto(
        List<DadosCadastroPendenciaLivroKitLivro> livros,
        List<DadosCadastroPendenciaLivroKitLivro> kitLivros
) {
}
