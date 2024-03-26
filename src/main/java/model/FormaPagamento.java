package model;

public class FormaPagamento extends Colaborador {
	int id, pagamentosDiversos_id;
	String formaPagamento, banco, agencia, contacorrente, cpf, cnpj, email, telefone;
	
	
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPagamentosDiversos_id() {
		return pagamentosDiversos_id;
	}
	public void setPagamentosDiversos_id(int pagamentosDiversos_id) {
		this.pagamentosDiversos_id = pagamentosDiversos_id;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getContacorrente() {
		return contacorrente;
	}
	public void setContacorrente(String contacorrente) {
		this.contacorrente = contacorrente;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	

}
