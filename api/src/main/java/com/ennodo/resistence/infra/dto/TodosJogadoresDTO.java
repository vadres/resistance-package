package com.ennodo.resistence.infra.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodosJogadoresDTO {
	List<JogadorDTO> jogadores;
	Integer tipoJogo;
}
