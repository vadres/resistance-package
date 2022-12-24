package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.JogoJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<JogoJpa, Integer> {
	JogoJpa findByQtdJogadores(Integer qtdJogadores);
}