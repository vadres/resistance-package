package com.ennodo.resistence.infra.controller;

import com.ennodo.resistence.infra.dto.GameResponseDTO;
import com.ennodo.resistence.infra.service.GameService;
import com.ennodo.resistence.infra.service.JogadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jogador")
@RequiredArgsConstructor
public class JogadorController {

	private final GameService gameService;
	private final JogadorService jogadorService;

	@GetMapping("/all")
	public ResponseEntity<GameResponseDTO> all() {
		return ResponseEntity.ok(gameService.buscarJogadores());
	}

	@PostMapping("/novo/{nome}")
	public ResponseEntity<Void> criarNovo(@PathVariable String nome) {
		jogadorService.criarJogador(nome);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> removerJogador(@PathVariable Integer id) {
		jogadorService.removerJogador(id);
		return ResponseEntity.ok().build();
	}
}
