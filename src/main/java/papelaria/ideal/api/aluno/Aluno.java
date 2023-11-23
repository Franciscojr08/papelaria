package papelaria.ideal.api.aluno;

import lombok.*;
import papelaria.ideal.api.cliente.Cliente;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

@Data
@Entity(name = "aluno")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id_responsavel")
    private Cliente cliente;

    private String nome;
    private String matricula;
    private String rg;
    private String cpf;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;
}