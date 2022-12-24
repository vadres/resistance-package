package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.PartidaJogadorJpa;
import com.ennodo.resistence.infra.entity.PartidaJogadorIdJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartidaJogadorRepository extends JpaRepository<PartidaJogadorJpa, PartidaJogadorIdJpa> {
	@Query("select pj from PartidaJogadorJpa pj " +
			"inner join fetch pj.personagem pe " +
			"inner join fetch pj.jogador j " +
			"inner join fetch pj.partida pa " +
			"inner join pa.grupo pag " +
			"where pa.atual = true and pag.atual = true ")
	List<PartidaJogadorJpa> buscarJogadoresJogoAtual();
}