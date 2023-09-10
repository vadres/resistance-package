package com.ennodo.resistence.infra.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jogo")
@Data
public class JogoJpa {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "qtd_jogadores", nullable = false)
	private Integer qtdJogadores;

	@Column(name = "qtd_resistencia", nullable = false)
	private Integer qtdResistencia;

	@Column(name = "qtd_espioes", nullable = false)
	private Integer qtdEspioes;
}