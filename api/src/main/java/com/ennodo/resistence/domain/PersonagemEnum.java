package com.ennodo.resistence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum PersonagemEnum {
	GUARDA_COSTAS("Comandantes", TeamEnum.R),
	COMANDANTE("Espiões", TeamEnum.R),
	ASSASSINO("Espiões", TeamEnum.E),
	FALSO_COMANDANTE("Espiões", TeamEnum.E),
	RESISTENCIA("", TeamEnum.R),
	ESPIAO("Espiões", TeamEnum.E),
	AGENTE_INVISIVEL("Espiões", TeamEnum.E);

	@Getter
	private final String observer;

	@Getter
	private final TeamEnum team;

	public static boolean acharEspiao(Jogador jogador) {
		return ESPIAO.equals(jogador.personagemEnum) ||
				ASSASSINO.equals(jogador.personagemEnum) ||
				FALSO_COMANDANTE.equals(jogador.personagemEnum) ||
				AGENTE_INVISIVEL.equals(jogador.personagemEnum);
	}

	public static PersonagemEnum byOrdinal(Integer ordinal) {
		return Arrays.stream(PersonagemEnum.values()).filter(c -> Integer.valueOf(c.ordinal()).equals(ordinal))
				.findFirst().orElse(RESISTENCIA);
	}

}
