package com.ifes.apinoticias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifes.apinoticias.BancoDados.PessoaRepository;
import com.ifes.apinoticias.models.Pessoa;
import com.ifes.apinoticias.records.DadosPessoaDTO;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaRepository repository;

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public void cadastrar(@RequestBody DadosPessoaDTO dados) throws Exception {
		repository.save(new Pessoa(dados));

	};

}
