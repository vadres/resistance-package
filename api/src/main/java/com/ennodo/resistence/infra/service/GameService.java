package com.ennodo.resistence.infra.service;

import com.ennodo.resistence.domain.PersonagemEnum;
import com.ennodo.resistence.domain.TeamEnum;
import com.ennodo.resistence.infra.dto.GameResponseDTO;
import com.ennodo.resistence.infra.dto.JogadorDTO;
import com.ennodo.resistence.infra.dto.TodosJogadoresDTO;
import com.ennodo.resistence.infra.entity.GrupoPartidaJpa;
import com.ennodo.resistence.infra.entity.JogadorJpa;
import com.ennodo.resistence.infra.entity.JogoJpa;
import com.ennodo.resistence.infra.entity.PartidaJogadorIdJpa;
import com.ennodo.resistence.infra.entity.PartidaJogadorJpa;
import com.ennodo.resistence.infra.entity.PartidaJpa;
import com.ennodo.resistence.infra.entity.PersonagemJpa;
import com.ennodo.resistence.infra.repository.GrupoPartidaRepository;
import com.ennodo.resistence.infra.repository.JogoRepository;
import com.ennodo.resistence.infra.repository.PartidaJogadorRepository;
import com.ennodo.resistence.infra.repository.PartidaRepository;
import com.ennodo.resistence.infra.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {
	private final GrupoPartidaRepository grupoPartidaRepository;
	private final JogadorService jogadorService;
	private final JogoRepository jogoRepository;
	private final PartidaJogadorRepository partidaJogadorRepository;
	private final PartidaRepository partidaRepository;
	private final PersonagemRepository personagemRepository;

	public GameResponseDTO buscarJogadores() {
		List<PartidaJogadorJpa> partidaJogadoreJpas = partidaJogadorRepository.buscarJogadoresJogoAtual();

		if (partidaJogadoreJpas == null || partidaJogadoreJpas.isEmpty()) {
			List<JogadorJpa> jogadores = jogadorService.buscarTodos();
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
	public List<JogadorDTO> iniciarJogo(TodosJogadoresDTO todosJogadoresDTO) {
		List<JogadorJpa> jogadores = jogadorService.localizarJogadores(todosJogadoresDTO.getJogadores());

		JogoJpa jogoJpa = jogoRepository.findByQtdJogadores(jogadores.size());

		final List<PartidaJogadorJpa> partidaJogadoreJpas = new ArrayList<>();
		List<PersonagemJpa> personagens = personagemRepository.findAll();
		Collections.shuffle(personagens, new Random());

		PersonagemJpa resistencia = personagens.stream().filter(p -> p.getId().equals(7))
				.findFirst().orElseThrow();

		for (int i = 0; i < jogoJpa.getQtdResistencia(); i++) {
			PartidaJogadorJpa partidaJogadorJpa = new PartidaJogadorJpa();
			partidaJogadorJpa.setPersonagem(resistencia);

			partidaJogadoreJpas.add(partidaJogadorJpa);
		}

		PersonagemJpa espiao = personagens.stream().filter(p -> p.getId().equals(1))
				.findFirst().orElseThrow();

		for (int i = 0; i < jogoJpa.getQtdEspioes(); i++) {
			PartidaJogadorJpa partidaJogadorJpa = new PartidaJogadorJpa();
			partidaJogadorJpa.setPersonagem(espiao);

			partidaJogadoreJpas.add(partidaJogadorJpa);
		}

		Collections.shuffle(partidaJogadoreJpas);
		Collections.shuffle(todosJogadoresDTO.getPersonagens(), new Random());
		Deque<PersonagemEnum> stack = new ArrayDeque<>(todosJogadoresDTO.getPersonagens());

		while (!stack.isEmpty() && temPersonagemExtra(stack, partidaJogadoreJpas)) {
			PersonagemEnum personagemExtra = stack.peek();
			for (PartidaJogadorJpa partidaJogadoreJpa : partidaJogadoreJpas) {
				if (partidaJogadoreJpa.getPersonagem().getId().equals(1) || partidaJogadoreJpa.getPersonagem().getId().equals(7)) {
					if (partidaJogadoreJpa.getPersonagem().getTeam().equals(personagemExtra.getTeam())) {
						PersonagemJpa personagemExtraJpa = personagens.stream().filter(p -> p.getDescricao().equals(personagemExtra.name()))
								.findFirst().orElseThrow();

						partidaJogadoreJpa.setPersonagem(personagemExtraJpa);
						stack.pop();
						break;
					}
				}
			}
		}

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

		int i = 0;
		for (JogadorJpa jogadorJpa : jogadores) {
			PartidaJogadorIdJpa partidaJogadorIdJpa = new PartidaJogadorIdJpa(jogadorJpa.getId(), partidaJpa.getId());
			PartidaJogadorJpa partidaJogadorJpa = partidaJogadoreJpas.get(i);
			partidaJogadorJpa.setId(partidaJogadorIdJpa);
			partidaJogadorJpa.setPartida(partidaJpa);
			partidaJogadorJpa.setJogador(jogadorJpa);

			partidaJogadoreJpas.set(i++, partidaJogadorRepository.save(partidaJogadorJpa));
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

	private boolean temPersonagemExtra(Deque<PersonagemEnum> stack, List<PartidaJogadorJpa> partidaJogadoreJpas) {
		boolean temEspiaoNaStack = stack.stream().anyMatch(p -> p.getTeam().equals(TeamEnum.E));
		boolean temEspiaoNaLista = partidaJogadoreJpas.stream().anyMatch(pjj -> pjj.getPersonagem().getId().equals(1));
		boolean temResNaStack = stack.stream().anyMatch(p -> p.getTeam().equals(TeamEnum.R));
		boolean temResNaLista = partidaJogadoreJpas.stream().anyMatch(pjj -> pjj.getPersonagem().getId().equals(7));

		return (temEspiaoNaStack && temEspiaoNaLista) || (temResNaStack && temResNaLista);
	}
}
