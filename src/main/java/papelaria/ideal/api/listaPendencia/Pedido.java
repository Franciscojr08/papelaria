package papelaria.ideal.api.listaPendencia;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Pedido {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

}
