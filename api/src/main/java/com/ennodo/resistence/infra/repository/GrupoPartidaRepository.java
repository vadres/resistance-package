package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.GrupoPartidaJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GrupoPartidaRepository extends JpaRepository<GrupoPartidaJpa, Integer> {
	@Modifying
	@Query("update GrupoPartidaJpa p set p.atual = false where p.atual = true and p.id <> :id")
	void atualizarGruposAnteriores(Integer id);

	Optional<GrupoPartidaJpa> findByAtual(boolean atual);
}