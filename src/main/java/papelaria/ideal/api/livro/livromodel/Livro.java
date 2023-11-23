package papelaria.ideal.api.livro.livromodel;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity(name = "livro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {

    @Id
    private Long id;
    private String isbn;
    private String nome;
    private Float valor;
    private Boolean usoInterno;
    private Integer quantidade;
    private Boolean ativo;
}
