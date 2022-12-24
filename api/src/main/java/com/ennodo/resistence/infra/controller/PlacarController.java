package com.ennodo.resistence.infra.controller;

import com.ennodo.resistence.infra.dto.AtualizarPlacarDTO;
import com.ennodo.resistence.infra.dto.PlacarDTO;
import com.ennodo.resistence.infra.service.PlacarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/placar")
@RequiredArgsConstructor
public class PlacarController {

	private final PlacarService placarService;

	@GetMapping
	public ResponseEntity<PlacarDTO> get() {
		return ResponseEntity.ok(placarService.buscarPlacar());
	}

	@PostMapping
	public ResponseEntity<Void> post(@RequestBody AtualizarPlacarDTO dto) {
		placarService.atualizarPlacar(dto);
		return ResponseEntity.ok().build();
	}

}
