package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.JogadorJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<JogadorJpa, Integer> {
	@Query("select j from JogadorJpa j where id in :toList")
	List<JogadorJpa> buscarPorIds(List<Integer> toList);
}
