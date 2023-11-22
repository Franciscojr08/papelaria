package papelaria.ideal.api.livro;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.utils.LivroKitLivroServiceInterface;

@Service
public class LivroService implements LivroKitLivroServiceInterface {

	@Autowired
	private LivroRepository livroRepository;

	@Transactional
	public void atualizarQuantidade(Long id, Long quantidade) {
		var livro = livroRepository.getReferenceById(id);
		livro.setQuantidadeDisponivel(quantidade);
	}
}
