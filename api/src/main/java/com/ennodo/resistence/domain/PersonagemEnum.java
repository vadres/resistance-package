package com.ennodo.resistence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum PersonagemEnum {
	GUARDA_COSTAS("Comandantes"),
	COMANDANTE("Espiões"),
	ASSASSINO("Espiões"),
	FALSO_COMANDANTE("Espiões"),
	RESISTENCIA(""),
	ESPIAO("Espiões"),
	AGENTE_INVISIVEL("Espiões");

	@Getter
	private final String observer;

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
