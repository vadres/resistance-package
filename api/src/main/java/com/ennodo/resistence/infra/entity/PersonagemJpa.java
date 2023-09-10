package com.ennodo.resistence.infra.entity;

import com.ennodo.resistence.domain.TeamEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personagem")
@Data
public class PersonagemJpa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "descricao", nullable = false, length = 50)
	private String descricao;

	@Column(name = "info", length = 50)
	private String info;

	@Column(name = "ids_info", length = 100)
	private String idsInfo;

	@Enumerated(EnumType.STRING)
	@Column(name = "team")
	private TeamEnum team;
}