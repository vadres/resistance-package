package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.JogoPersonagemJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoPersonagemRepository extends JpaRepository<JogoPersonagemJpa, Integer> {
	@Query("select jp from JogoPersonagemJpa jp " +
			"join fetch jp.personagem p " +
			"join fetch jp.jogo j " +
			"where j.id = :idJogo and jp.tipoJogo = :tipoJogo")
	List<JogoPersonagemJpa> buscarPersonagens(Integer idJogo, Integer tipoJogo);
}