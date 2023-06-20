package com.ifes.apinoticias.enums;

public enum PreferenciaNoticia {

	DIVERSOS(0), EDUCACAO(1);


	private final int preferencia;
	PreferenciaNoticia(int preferencia){
		this.preferencia = preferencia;
	}
	public int getPreferencia() {
		return preferencia;
	}
}
