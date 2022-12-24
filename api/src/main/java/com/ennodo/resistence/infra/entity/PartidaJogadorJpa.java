package com.ennodo.resistence.infra.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "partida_jogador")
public class PartidaJogadorJpa {
	@EmbeddedId
	private PartidaJogadorIdJpa id;

	@MapsId("idJogador")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_jogador", nullable = false)
	private JogadorJpa jogador;

	@MapsId("idPartida")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_partida", nullable = false)
	private PartidaJpa partida;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_personagem", nullable = false)
	private PersonagemJpa personagem;

	public PartidaJogadorIdJpa getId() {
		return id;
	}

	public void setId(PartidaJogadorIdJpa id) {
		this.id = id;
	}

	public JogadorJpa getJogador() {
		return jogador;
	}

	public void setJogador(JogadorJpa jogadorJpa) {
		this.jogador = jogadorJpa;
	}

	public PartidaJpa getPartida() {
		return partida;
	}

	public void setPartida(PartidaJpa partidaJpa) {
		this.partida = partidaJpa;
	}

	public PersonagemJpa getPersonagem() {
		return personagem;
	}

	public void setPersonagem(PersonagemJpa pPersonagemJpa) {
		this.personagem = pPersonagemJpa;
	}

	public List<Integer> getIds() {
		if (personagem.getIdsInfo() == null || personagem.getIdsInfo().isEmpty()) {
			return new ArrayList<>();
		}

		return Arrays.stream(personagem.getIdsInfo().split(","))
				.map(Integer::valueOf).toList();
	}
}