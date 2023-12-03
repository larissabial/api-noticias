package com.ifes.apinoticias.BancoDados;

import com.ifes.apinoticias.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByEmailIgnoreCase (String email);
}
