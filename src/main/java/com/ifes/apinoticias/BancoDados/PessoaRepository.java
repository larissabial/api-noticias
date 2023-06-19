package com.ifes.apinoticias.BancoDados;

import com.ifes.apinoticias.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
