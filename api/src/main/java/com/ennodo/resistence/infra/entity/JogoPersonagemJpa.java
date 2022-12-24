package com.ennodo.resistence.infra.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "jogo_personagem")
public class JogoPersonagemJpa {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "id_personagem",
			referencedColumnName = "id",
			insertable = false, updatable = false,
			foreignKey = @javax.persistence
					.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private PersonagemJpa personagem;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "id_jogo",
			referencedColumnName = "id",
			insertable = false, updatable = false,
			foreignKey = @javax.persistence
					.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private JogoJpa jogo;

	@Column(name = "qtd_personagem", nullable = false, updatable = false)
	private Integer qtdPersonagem;

	@Column(name = "tipo_jogo", nullable = false)
	private Integer tipoJogo;

	public PersonagemJpa getPersonagem() {
		return personagem;
	}

	public void setPersonagem(PersonagemJpa personagem) {
		this.personagem = personagem;
	}

	public JogoJpa getJogo() {
		return jogo;
	}

	public void setJogo(JogoJpa jogo) {
		this.jogo = jogo;
	}

	public Integer getQtdPersonagem() {
		return qtdPersonagem;
	}

	public void setQtdPersonagem(Integer qtdPersonagem) {
		this.qtdPersonagem = qtdPersonagem;
	}

	public Integer getTipoJogo() {
		return tipoJogo;
	}

	public void setTipoJogo(Integer tipoJogo) {
		this.tipoJogo = tipoJogo;
	}
}