package papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.kitLivro.KitLivro;
import papelaria.ideal.api.listaPendencia.ListaPendencia;

@Data
@Entity(name = "lista_pendencia_kit_livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ListaPendenciaKitLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_pendencia_id")
    private ListaPendencia listaPendencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kit_livro_id")
    private KitLivro kitLivro;

    private Long quantidade;

}
