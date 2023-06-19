package com.ifes.apinoticias.records;

import com.ifes.apinoticias.PreferenciaNoticia;

public record DadosPessoaDTO (String nome, String CPF, String email, PreferenciaNoticia preferencia) {
}
