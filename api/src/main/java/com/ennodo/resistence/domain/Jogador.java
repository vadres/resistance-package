package com.ennodo.resistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Jogador {
	public Jogador(String name, Integer character) {
		this.personagemEnum = PersonagemEnum.byOrdinal(character);
		this.name = name;
	}

	String name;
	@JsonIgnore
	PersonagemEnum personagemEnum;
}
