import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Turma.DadosCadastroTurma;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Transactional
    public Turma cadastrarTurma(DadosCadastroTurma dadosCadastroTurma) {
        Long serieId = DadosCadastroTurma.getSerieId();
        Serie serieAssociada = serieRepository.findById(serieId)
                .orElseThrow(() -> new IllegalArgumentException("Série inválida para a turma."));


        Turma turma = new Turma(DadosCadastroTurma.getNome());
        turma.setSerie(serieAssociada);

        return turmaRepository.save(turma);
    }

    public Turma atualizarTurma(Long id, Turma turma) {
        if (turmaRepository.existsById(id)) {
            turma.setId(id);
            return turmaRepository.save(turma);
        }
        return null;
    }

    public void deletarTurma(Long id) {
        turmaRepository.deleteById(id);
    }
}