package com.ifes.apinoticias.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ifes.apinoticias.PreferenciaNoticia;
import com.ifes.apinoticias.records.DadosPessoaDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "pessoa")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

	@Id
	@Column(name = "cod_pessoa")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codPessoa;

	@Column(name = "nome")
	private String nome;

	@Column(name = "CPF")
	private String CPF;

	@Column(name = "email")
	private String email;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "preferencia")
	private PreferenciaNoticia vldPreferencia;

	public Pessoa(DadosPessoaDTO dados) {
		this.nome = dados.nome();
		this.CPF = dados.CPF();
		this.email = dados.email();
		this.vldPreferencia = dados.preferencia();
	}

	public Long getCodPessoa() {
		return codPessoa;
	}

	public void setCodPessoa(Long codPessoa) {
		this.codPessoa = codPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public PreferenciaNoticia getVldPreferencia() {
		return vldPreferencia;
	}

	public void setVldPreferencia(PreferenciaNoticia vldPreferencia) {
		this.vldPreferencia = vldPreferencia;
	}



}
