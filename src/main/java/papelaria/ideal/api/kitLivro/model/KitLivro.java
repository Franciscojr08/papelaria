package papelaria.ideal.api.kitLivro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "kit_livro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class KitLivro {

    @Id
    private Long id;
    private String nome;
    private String descricao;
    private Float valorUnitario;
    private Integer quantidadeKit;
    private boolean ativo;

}

