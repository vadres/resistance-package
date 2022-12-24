package com.ennodo.resistence.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GameResponseDTO {
	List<JogadorDTO> jogadores;
	Integer fase;

	@AllArgsConstructor
	@Getter
	public enum Fase {
		JOGO_NOVO(1),
		JOGO_JA_INICIADO(2);

		private final Integer valor;
	}
}
