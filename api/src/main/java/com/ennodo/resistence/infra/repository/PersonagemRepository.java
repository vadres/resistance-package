package com.ennodo.resistence.infra.repository;

import com.ennodo.resistence.infra.entity.PersonagemJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<PersonagemJpa, Integer> {

}