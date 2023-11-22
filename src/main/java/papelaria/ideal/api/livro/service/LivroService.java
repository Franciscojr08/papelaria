package papelaria.ideal.api.livro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.livro.repository.LivroRepository;
import papelaria.ideal.api.livro.livromodel.Livro;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> consultarLivrosAtivos() {
        return livroRepository.findByAtivoTrue();
    }

    public List<Livro> consultarTodosLivros() {
        return livroRepository.findAll();
    }

    public void atualizarNomeLivro(Long livroId, String novoNome) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setNome(novoNome);
            livroRepository.save(livro);
        });
    }

    // Demais métodos para atualizar outros atributos de livro
    // ...

    public void excluirLivro(Long livroId) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setAtivo(false);
            livroRepository.save(livro);
        });
    }
}

