package papelaria.ideal.api.listaPendencia;

import lombok.Getter;

@Getter
public enum SituacaoListaPendenciaEnum {
    ENTREGUE,
    SOLICITADO,
    PENDENTE
    ;

    SituacaoListaPendenciaEnum() {

    }
}
