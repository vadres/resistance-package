package com.ennodo.resistence.infra.dto;

import com.ennodo.resistence.domain.PersonagemEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodosJogadoresDTO {
	List<JogadorDTO> jogadores;
	List<PersonagemEnum> personagens;
	Integer tipoJogo;
}
