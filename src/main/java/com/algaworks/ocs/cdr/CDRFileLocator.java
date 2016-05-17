package com.algaworks.ocs.cdr;

import java.io.File;

import com.algaworks.ocs.api.Finalizavel;

public interface CDRFileLocator extends Finalizavel{
	
	File getFile(String numero);
}
