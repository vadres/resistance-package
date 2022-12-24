package com.ennodo.resistence.infra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jogo")
public class JogoJpa {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "qtd_jogadores", nullable = false)
	private Integer qtdJogadores;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQtdJogadores() {
		return qtdJogadores;
	}

	public void setQtdJogadores(Integer qtdJogadores) {
		this.qtdJogadores = qtdJogadores;
	}

}