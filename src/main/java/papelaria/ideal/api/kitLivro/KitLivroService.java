package papelaria.ideal.api.kitLivro;

import org.springframework.stereotype.Service;
import papelaria.ideal.api.livroKitLivro.LivroKitLivroServiceInterface;

@Service
public class KitLivroService implements LivroKitLivroServiceInterface {

	public void atualizarQuantidade(Long quantidade) {
		System.out.println("atualizou Kit: " + quantidade);
	}
}
