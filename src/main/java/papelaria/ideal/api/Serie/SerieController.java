package papelaria.ideal.api.Serie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<Serie> listarSeries() {
        return serieService.listarSeries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> listarSeriePorId(@PathVariable Long id) {
        Optional<Serie> serie = serieService.listarSeriePorId(id);
        return serie.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Serie> cadastrarSerie(@RequestBody Serie serie) {
        Serie novaSerie = serieService.cadastrarSerie(serie);
        return new ResponseEntity<>(novaSerie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Serie> atualizarSerie(@PathVariable Long id, @RequestBody Serie serie) {
        Serie serieAtualizada = serieService.atualizarSerie(id, serie);
        return serieAtualizada != null ?
                new ResponseEntity<>(serieAtualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSerie(@PathVariable Long id) {
        serieService.deletarSerie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

