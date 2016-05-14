package com.algaworks.ocs.api;

import java.io.File;

import com.algaworks.ocs.api.repository.Clientes;
import com.algaworks.ocs.cdr.CDRFileLocator;
import com.algaworks.ocs.cdr.CDRGenerator;
import com.algaworks.ocs.log.ApiLogger;
import com.algaworks.ocs.model.Cliente;
import com.algaworks.ocs.service.ClienteService;

public class OcsApi {

	private ApiLogger logger;
	private Clientes clientes;
	
	private CDRGenerator cdrGenerator;
	private CDRFileLocator cdrFileLocator;
	
	private Autorizavel autorizavel;
	
	public OcsApi(CDRGenerator cdrGenerator, CDRFileLocator cdrFileLocator) {
		this.clientes = new Clientes();
		
		this.cdrFileLocator = cdrFileLocator;
		this.cdrGenerator = cdrGenerator;
		autorizavel = new ClienteService(clientes);
		this.logger = new ApiLogger(autorizavel);
		
	}

	public Ligacao autorizar(String numero) {
		return autorizavel.autorizar(numero);
	}

	public void finalizar(String numero, double tempo) {
		logger.finalizandoLigacao(numero, tempo);;
		Cliente cliente = getCliente(numero);
		double tarifa = cliente.getTarifa().getValor();
		double saldo = cliente.getSaldo();
		double valorLigacao = (tempo / 60) * tarifa;
		saldo -= valorLigacao;
		cliente.setSaldo(saldo);
		clientes.guardar(cliente);
		logger.chamadaFinalizada(valorLigacao, numero);
		cdrGenerator.gerar(numero, tempo, valorLigacao);
	}
	
	public File getCdrFile(String numero) {
		return cdrFileLocator.getFile(numero);
	}
	

	private Cliente getCliente(String numero) {
		return clientes.porNumero(numero);
	}
	
}
