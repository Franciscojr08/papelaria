package papelaria.ideal.api.kitLivro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.kitLivro.model.KitLivro;
import papelaria.ideal.api.kitLivro.repository.KitLivroRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KitLivroService {

    @Autowired
    private KitLivroRepository kitLivroRepository;

    public KitLivro cadastrarKitLivro(KitLivro kitLivro) {
        return kitLivroRepository.save(kitLivro);
    }

    public List<KitLivro> consultarKitsLivroAtivos() {
        return kitLivroRepository.findAll().stream()
                .filter(KitLivro::isAtivo)
                .collect(Collectors.toList());
    }

    public void desativarKitLivro(Long kitLivroId) {
        Optional<KitLivro> kitLivroOpt = kitLivroRepository.findById(kitLivroId);
        kitLivroOpt.ifPresent(kitLivro -> {
            kitLivro.setAtivo(false);
            kitLivroRepository.save(kitLivro);
        });
    }

    public void atualizarNomeKitLivro(Long kitLivroId, String novoNome) {
        Optional<KitLivro> kitLivroOpt = kitLivroRepository.findById(kitLivroId);
        kitLivroOpt.ifPresent(kitLivro -> {
            kitLivro.setNome(novoNome);
            kitLivroRepository.save(kitLivro);
        });
    }

    public void atualizarDescricaoKitLivro(Long kitLivroId, String novaDescricao) {
        Optional<KitLivro> kitLivroOpt = kitLivroRepository.findById(kitLivroId);
        kitLivroOpt.ifPresent(kitLivro -> {
            kitLivro.setDescricao(novaDescricao);
            kitLivroRepository.save(kitLivro);
        });
    }

    public void atualizarValorUnitarioKitLivro(Long kitLivroId, Float novoValorUnitario) {
        Optional<KitLivro> kitLivroOpt = kitLivroRepository.findById(kitLivroId);
        kitLivroOpt.ifPresent(kitLivro -> {
            kitLivro.setValorUnitario(novoValorUnitario);
            kitLivroRepository.save(kitLivro);
        });
    }

    public void atualizarQuantidadeKitLivro(Long kitLivroId, Integer novaQuantidadeKit) {
        Optional<KitLivro> kitLivroOpt = kitLivroRepository.findById(kitLivroId);
        kitLivroOpt.ifPresent(kitLivro -> {
            kitLivro.setQuantidadeKit(novaQuantidadeKit);
            kitLivroRepository.save(kitLivro);
        });
    }

    public KitLivro buscarKitLivroPorId(Long id) {
        return kitLivroRepository.findById(id).orElse(null);
    }
}
