package com.ennodo.resistence.infra.controller;

import com.ennodo.resistence.infra.dto.JogadorDTO;
import com.ennodo.resistence.infra.dto.TodosJogadoresDTO;
import com.ennodo.resistence.infra.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jogo")
public class GameController {
	@Autowired
	public GameService gameService;

	@PostMapping("/iniciar")
	public ResponseEntity<List<JogadorDTO>> initGame(@RequestBody TodosJogadoresDTO todosJogadores) {
		return ResponseEntity.ok(gameService.iniciarJogo(todosJogadores.getJogadores(), todosJogadores.getTipoJogo()));
	}

	@PostMapping("/resetar")
	public ResponseEntity<Void> resetar() {
		gameService.resetarJogo();
		return ResponseEntity.ok().build();
	}

	@PostMapping("/finalizarPartida")
	public ResponseEntity<Void> finalizar() {
		gameService.finalizarPartida();
		return ResponseEntity.ok().build();
	}
}
