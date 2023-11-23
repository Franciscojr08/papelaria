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

    public void excluirLivro(Long livroId) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setAtivo(false);
            livroRepository.save(livro);
        });
    }

    public void atualizarIsbnLivro(Long livroId, String novoIsbn) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setIsbn(novoIsbn);
            livroRepository.save(livro);
        });
    }

    public void atualizarValorLivro(Long livroId, Float novoValor) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setValor(novoValor);
            livroRepository.save(livro);
        });
    }

    public void atualizarUsoInternoLivro(Long livroId, Boolean novoUsoInterno) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setUsoInterno(novoUsoInterno);
            livroRepository.save(livro);
        });
    }

    public void atualizarQuantidadeLivro(Long livroId, Integer novaQuantidade) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setQuantidade(novaQuantidade);
            livroRepository.save(livro);
        });
    }

    public void atualizarAtivoLivro(Long livroId, Boolean novoStatus) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        livroOpt.ifPresent(livro -> {
            livro.setAtivo(novoStatus);
            livroRepository.save(livro);
        });
    }

    // Método para buscar livro por ID
    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }
}

