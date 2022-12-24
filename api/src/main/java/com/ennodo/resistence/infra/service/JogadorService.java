package com.ennodo.resistence.infra.service;

import com.ennodo.resistence.infra.entity.JogadorJpa;
import com.ennodo.resistence.infra.repository.JogadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JogadorService {
	private final JogadorRepository jogadorRepository;

	public void criarJogador(String nome) {
		JogadorJpa jogadorJpa = new JogadorJpa(nome);
		jogadorRepository.save(jogadorJpa);
	}

	@Transactional
	public void removerJogador(Integer id) {
		try {
			JogadorJpa jogadorJpa = jogadorRepository.getReferenceById(id);
			jogadorRepository.delete(jogadorJpa);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}
}
