package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.PartidaJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartidaRepository extends JpaRepository<PartidaJpa, Integer> {
	@Modifying
	@Query("update PartidaJpa p set p.atual = false where p.atual = true and p.id <> :id")
	void atualizarPartidasAnteriores(Integer id);

	@Modifying
	@Query("update PartidaJpa p set p.espioes = true where p.id = :id")
	void vencedorEspioes(Integer id);

	@Modifying
	@Query("update PartidaJpa p set p.resistencia = true where p.id = :id")
	void vencedorResistencia(Integer id);

	@Query("select p from PartidaJpa p join fetch p.grupo where p.atual = true ")
	Optional<PartidaJpa> findPartidaAtual();

	List<PartidaJpa> findByGrupo_Id(Integer grupo);
}