package com.ennodo.resistence.infra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "partida")
public class PartidaJpa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_jogo", nullable = false)
	private JogoJpa jogoJpa;

	@Column(name = "atual")
	private Boolean atual;

	@Column(name = "resistencia")
	private Boolean resistencia;

	@Column(name = "espioes")
	private Boolean espioes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupo")
	private GrupoPartidaJpa grupo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JogoJpa getJogo() {
		return jogoJpa;
	}

	public void setJogo(JogoJpa jogoJpa) {
		this.jogoJpa = jogoJpa;
	}

	public Boolean getAtual() {
		return atual;
	}

	public void setAtual(Boolean atual) {
		this.atual = atual;
	}

	public Boolean getResistencia() {
		return Boolean.TRUE.equals(resistencia);
	}

	public void setResistencia(Boolean resistencia) {
		this.resistencia = resistencia;
	}

	public Boolean getEspioes() {
		return Boolean.TRUE.equals(espioes);
	}

	public void setEspioes(Boolean espioes) {
		this.espioes = espioes;
	}

	public GrupoPartidaJpa getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoPartidaJpa grupo) {
		this.grupo = grupo;
	}
}