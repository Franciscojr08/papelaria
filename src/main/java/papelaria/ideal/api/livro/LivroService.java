package papelaria.ideal.api.livro;

import org.springframework.stereotype.Service;
import papelaria.ideal.api.livroKitLivro.LivroKitLivroServiceInterface;

@Service
public class LivroService implements LivroKitLivroServiceInterface {


	public void atualizarQuantidade(Long quantidade) {
		System.out.println("atualizou Livro: " + quantidade);
	}
}
