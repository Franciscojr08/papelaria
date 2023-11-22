package papelaria.ideal.api.livro.livromodel;
package papelaria.ideal.api.livro;

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
@EqualsAndHashCode(of = "isbn")
public class Livro {

    @Id
    private String isbn;

    private String nome;
    private Float valor;
    private Boolean usoInterno;
    private Integer quantidade;
    private Boolean ativo;


    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Boolean getUsoInterno() {
        return usoInterno;
    }

    public void setUsoInterno(Boolean usoInterno) {
        this.usoInterno = usoInterno;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

