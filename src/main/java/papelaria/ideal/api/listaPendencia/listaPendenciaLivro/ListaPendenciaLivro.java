package papelaria.ideal.api.listaPendencia.listaPendenciaLivro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.listaPendencia.ListaPendencia;
import papelaria.ideal.api.livro.Livro;

@Data
@Entity(name = "pendencia_livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ListaPendenciaLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lista_pendencia_id")
    private ListaPendencia listaPendencia;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro Livro;

    private Long quantidade;
}
