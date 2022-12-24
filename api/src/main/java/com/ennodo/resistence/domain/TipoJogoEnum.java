package com.ennodo.resistence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoJogoEnum {
    SIMPLES("Simples", 1),
    COM_COMANDANTE("Com comandante", 2),
    COM_FALSO_COMANDANTE("Com falso comandante", 3),
    COM_ASSASSINO("Com falso comandante e assassino", 4),
    COM_AGENTE_INVISIVEL("Com falso comandante e agente invisível", 5);

    @Getter
    private final String descricao;
    @Getter
    private final Integer valor;
}
