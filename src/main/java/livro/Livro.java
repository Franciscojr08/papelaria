package livro;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "livro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "isbn")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;


    private String nome;
    private Float valor;
    private Boolean usoInterno;
    private Integer quantidade;
    private Boolean ativo;
}
