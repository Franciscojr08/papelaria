import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Serie.DadosCadastrarSerie;

import javax.transaction.Transactional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Transactional
    public Serie cadastrarSerie(DadosCadastrarSerie dadosCadastrarSerie) {
        // Verificar se já existe uma série com o mesmo nome
        String nomeSerie = dadosCadastrarSerie.getNome();
        if (serieRepository.existsByNome(nomeSerie)) {
            throw new IllegalArgumentException("Já existe uma série com o mesmo nome.");
        }

        // Criar a série a partir do record
        Serie serie = new Serie(dadosCadastrarSerie.getNome());

        return serieRepository.save(serie);
    }

    public List<Serie> listarSeries() {
        return serieRepository.findAll();
    }

    public Optional<Serie> listarSeriePorId(Long id) {
        return serieRepository.findById(id);
    }

    @Transactional
    public Serie atualizarSerie(Long id, Serie serie) {
        if (serieRepository.existsById(id)) {
            serie.setId(id);
            return serieRepository.save(serie);
        }
        return null;
    }

    public void deletarSerie(Long id) {
        serieRepository.deleteById(id);
    }
}

