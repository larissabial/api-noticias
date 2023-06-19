package com.ifes.apinoticias.service;

import com.ifes.apinoticias.BancoDados.PessoaRepository;
import com.ifes.apinoticias.models.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IEmailService {

    void disparar (List<Pessoa> pessoaList)
            throws Exception;

}
