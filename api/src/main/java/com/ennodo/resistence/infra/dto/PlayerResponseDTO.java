package com.ennodo.resistence.infra.dto;

import com.ennodo.resistence.domain.Jogador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerResponseDTO {
	String name;
	String function;

	public static PlayerResponseDTO toPlayerResponseDTO(Jogador jogador) {
		return new PlayerResponseDTO(jogador.getName(), jogador.getPersonagemEnum().name());
	}
}
