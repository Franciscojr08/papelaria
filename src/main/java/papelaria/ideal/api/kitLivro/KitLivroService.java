package papelaria.ideal.api.kitLivro;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.utils.LivroKitLivroServiceInterface;

@Service
public class KitLivroService implements LivroKitLivroServiceInterface {

	@Autowired
	private KitLivroRepository kitLivroRepository;

	@Transactional
	public void atualizarQuantidade(Long id, Long quantidade) {
		var kitLivro = kitLivroRepository.getReferenceById(id);
		kitLivro.setQuantidadeDisponivel(quantidade);
	}
}
