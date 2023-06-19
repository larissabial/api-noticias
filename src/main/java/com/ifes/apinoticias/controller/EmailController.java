package com.ifes.apinoticias.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifes.apinoticias.BancoDados.PessoaRepository;
import com.ifes.apinoticias.models.Pessoa;
import com.ifes.apinoticias.service.IEmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private PessoaRepository repository;

	@Autowired
	private IEmailService emailService;

	@GetMapping
	@Transactional(rollbackFor = Exception.class)
	public void dispararEmails() throws Exception {
		List<Pessoa> pessoaList = new ArrayList<>();
		pessoaList = repository.findAll();

		System.out.println(pessoaList);
		emailService.disparar(pessoaList);
	}
}
