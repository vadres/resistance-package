package com.ennodo.resistence.infra.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PartidaJogadorIdJpa implements Serializable {
	private static final long serialVersionUID = -2337629632937615105L;
	@Column(name = "id_jogador", nullable = false)
	private Integer idJogador;

	@Column(name = "id_partida", nullable = false)
	private Integer idPartida;

	public Integer getIdJogador() {
		return idJogador;
	}

	public void setIdJogador(Integer idJogador) {
		this.idJogador = idJogador;
	}

	public Integer getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		PartidaJogadorIdJpa entity = (PartidaJogadorIdJpa) o;
		return Objects.equals(this.idPartida, entity.idPartida) &&
				Objects.equals(this.idJogador, entity.idJogador);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPartida, idJogador);
	}

}