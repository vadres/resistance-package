package com.ennodo.resistence.infra.service;

import com.ennodo.resistence.infra.dto.JogadorDTO;
import com.ennodo.resistence.infra.entity.JogadorJpa;
import com.ennodo.resistence.infra.repository.JogadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class JogadorService {
	private final JogadorRepository jogadorRepository;

	public List<JogadorJpa> buscarTodos() {
		return jogadorRepository.findAll();
	}

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

	public List<JogadorJpa> localizarJogadores(List<JogadorDTO> jogadoresDTO) {
		List<JogadorJpa> jogadores = jogadorRepository.buscarPorIds(
				jogadoresDTO.stream().map(JogadorDTO::getId).toList()
		);

		Collections.shuffle(jogadores, new Random());
		return jogadores;
	}
}
