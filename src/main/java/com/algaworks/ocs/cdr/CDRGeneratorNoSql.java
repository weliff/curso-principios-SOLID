package com.algaworks.ocs.cdr;

import java.io.File;

public class CDRGeneratorNoSql extends CDRGeneratorFile{

	@Override
	public void gerar(File file, String numero, double tempo, double valorLigacao) {
		// TODO Salva no NoSQL
	}

	@Override
	public File getFile(String numero, String pastaCdr) {
		// TODO Auto-generated method stub
		return super.getFile(numero, pastaCdr);
	}
	
	
}
