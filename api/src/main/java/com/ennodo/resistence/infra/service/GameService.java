package com.ennodo.resistence.infra.service;

import com.ennodo.resistence.infra.dto.GameResponseDTO;
import com.ennodo.resistence.infra.dto.JogadorDTO;
import com.ennodo.resistence.infra.entity.GrupoPartidaJpa;
import com.ennodo.resistence.infra.entity.JogadorJpa;
import com.ennodo.resistence.infra.entity.JogoJpa;
import com.ennodo.resistence.infra.entity.JogoPersonagemJpa;
import com.ennodo.resistence.infra.entity.PartidaJogadorIdJpa;
import com.ennodo.resistence.infra.entity.PartidaJogadorJpa;
import com.ennodo.resistence.infra.entity.PartidaJpa;
import com.ennodo.resistence.infra.repository.GrupoPartidaRepository;
import com.ennodo.resistence.infra.repository.JogadorRepository;
import com.ennodo.resistence.infra.repository.JogoPersonagemRepository;
import com.ennodo.resistence.infra.repository.JogoRepository;
import com.ennodo.resistence.infra.repository.PartidaJogadorRepository;
import com.ennodo.resistence.infra.repository.PartidaRepository;
import com.ennodo.resistence.infra.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {
	private final GrupoPartidaRepository grupoPartidaRepository;
	private final JogadorRepository jogadorRepository;
	private final JogoRepository jogoRepository;
	private final PartidaJogadorRepository partidaJogadorRepository;
	private final PartidaRepository partidaRepository;
	private final JogoPersonagemRepository jogoPersonagemRepository;
	private final PersonagemRepository personagemRepository;

	public GameResponseDTO buscarJogadores() {
		List<PartidaJogadorJpa> partidaJogadoreJpas = partidaJogadorRepository.buscarJogadoresJogoAtual();

		if (partidaJogadoreJpas == null || partidaJogadoreJpas.isEmpty()) {
			List<JogadorJpa> jogadores = jogadorRepository.findAll();
			return new GameResponseDTO(
					jogadores.stream().map(JogadorDTO::toDTO).toList(),
					GameResponseDTO.Fase.JOGO_NOVO.getValor()
			);
		}

		return new GameResponseDTO(
				partidaJogadoreJpas.stream().map(partidaJogadorJpa -> (
					new JogadorDTO(partidaJogadorJpa.getJogador().getId(),
							partidaJogadorJpa.getJogador().getNome(),
							partidaJogadorJpa.getPersonagem().getDescricao(),
							partidaJogadorJpa.getPersonagem().getInfo(),
							revelados(partidaJogadorJpa, partidaJogadoreJpas)
					)
				)).toList(),
				GameResponseDTO.Fase.JOGO_JA_INICIADO.getValor()
		);
	}

	@Transactional
	public List<JogadorDTO> iniciarJogo(List<JogadorDTO> jogadoresDTO, Integer tipoJogo) {
		List<JogadorJpa> jogadores = jogadorRepository.buscarPorIds(
			jogadoresDTO.stream().map(JogadorDTO::getId).toList()
		);

		Collections.shuffle(jogadores, new Random());

		JogoJpa jogoJpa = jogoRepository.findByQtdJogadores(jogadores.size());

		List<JogoPersonagemJpa> jogoPersonagems = jogoPersonagemRepository
				.buscarPersonagens(jogoJpa.getId(), tipoJogo);
		Collections.shuffle(jogoPersonagems, new Random());

		GrupoPartidaJpa grupoPartida = grupoPartidaRepository.findByAtual(true)
						.orElse(new GrupoPartidaJpa(true));
		grupoPartida = grupoPartidaRepository.save(grupoPartida);
		grupoPartidaRepository.atualizarGruposAnteriores(grupoPartida.getId());

		PartidaJpa partidaJpa = new PartidaJpa();
		partidaJpa.setGrupo(grupoPartida);
		partidaJpa.setJogo(jogoJpa);
		partidaJpa.setAtual(true);
		partidaJpa = partidaRepository.save(partidaJpa);
		partidaRepository.atualizarPartidasAnteriores(partidaJpa.getId());

		final List<PartidaJogadorJpa> partidaJogadoreJpas = new ArrayList<>();
		int i = 0;
		for (JogadorJpa jogadorJpa : jogadores) {
			JogoPersonagemJpa jogoPersonagem = jogoPersonagems.get(i);
			if (jogoPersonagem.getQtdPersonagem() == 1) i++;
			else jogoPersonagem.setQtdPersonagem( jogoPersonagem.getQtdPersonagem() - 1);

			PartidaJogadorIdJpa partidaJogadorIdJpa = new PartidaJogadorIdJpa(jogadorJpa.getId(), partidaJpa.getId());
			PartidaJogadorJpa partidaJogadorJpa = new PartidaJogadorJpa();
			partidaJogadorJpa.setId(partidaJogadorIdJpa);
			partidaJogadorJpa.setPartida(partidaJpa);
			partidaJogadorJpa.setPersonagem(jogoPersonagem.getPersonagem());
			partidaJogadorJpa.setJogador(jogadorJpa);

			partidaJogadoreJpas.add(partidaJogadorRepository.save(partidaJogadorJpa));
		}

		return partidaJogadoreJpas.stream().map(partidaJogadorJpa -> (
			new JogadorDTO(partidaJogadorJpa.getJogador().getId(),
					partidaJogadorJpa.getJogador().getNome(),
					partidaJogadorJpa.getPersonagem().getDescricao(),
					partidaJogadorJpa.getPersonagem().getInfo(),
					revelados(partidaJogadorJpa, partidaJogadoreJpas)
			)
		)).toList();
	}

	private List<String> revelados(PartidaJogadorJpa partidaJogadorJpa, List<PartidaJogadorJpa> partidaJogadoreJpas) {
		return partidaJogadoreJpas.stream()
				.filter(pj -> partidaJogadorJpa.getIds().contains(pj.getPersonagem().getId()) && !pj.getJogador().getId().equals(partidaJogadorJpa.getJogador().getId()))
				.map(pj -> pj.getJogador().getNome())
				.toList();
	}

	@Transactional
	public void finalizarPartida() {
		PartidaJpa partidaJpa = partidaRepository.findPartidaAtual().orElseThrow();
		partidaJpa.setAtual(false);
		partidaRepository.save(partidaJpa);
	}

	@Transactional
	public void resetarJogo() {
		PartidaJpa partidaJpa = partidaRepository.findPartidaAtual().orElseThrow();
		GrupoPartidaJpa grupoPartidaJpa = grupoPartidaRepository.findByAtual(true).orElseThrow();

		partidaJpa.setAtual(false);
		grupoPartidaJpa.setAtual(false);

		partidaRepository.save(partidaJpa);
		grupoPartidaRepository.save(grupoPartidaJpa);
	}
}
