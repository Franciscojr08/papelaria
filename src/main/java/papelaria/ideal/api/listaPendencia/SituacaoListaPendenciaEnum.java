package papelaria.ideal.api.listaPendencia;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum SituacaoListaPendenciaEnum {
    @Enumerated(EnumType.STRING)
    ENTREGUE,
    @Enumerated(EnumType.STRING)
    PENDENTE,
    @Enumerated(EnumType.STRING)
    SOLICITADA,
    @Enumerated(EnumType.STRING)
    CANCELADA
}
