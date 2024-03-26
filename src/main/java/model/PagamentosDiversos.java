package model;

public class PagamentosDiversos extends FormaPagamento{
int colaborador_id, supervisorid, gerenteid;
String fornecedor, descricao, vencimento, valor, empresa, supervisor, gerente, data, status , supervisorhash, gerentehash;


public int getSupervisorid() {
	return supervisorid;
}
public void setSupervisorid(int supervisorid) {
	this.supervisorid = supervisorid;
}
public int getGerenteid() {
	return gerenteid;
}
public void setGerenteid(int gerenteid) {
	this.gerenteid = gerenteid;
}
public String getSupervisorhash() {
	return supervisorhash;
}
public void setSupervisorhash(String supervidorhash) {
	this.supervisorhash = supervidorhash;
}
public String getGerentehash() {
	return gerentehash;
}
public void setGerentehash(String gerentehash) {
	this.gerentehash = gerentehash;
}

public int getColaborador_id() {
	return colaborador_id;
}
public String getSupervisor() {
	return supervisor;
}
public void setSupervisor(String supervisor) {
	this.supervisor = supervisor;
}
public void setColaborador_id(int colaborador_id) {
	this.colaborador_id = colaborador_id;
}
public String getFornecedor() {
	return fornecedor;
}
public void setFornecedor(String fornecedor) {
	this.fornecedor = fornecedor;
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}
public String getVencimento() {
	return vencimento;
}
public void setVencimento(String vencimento) {
	this.vencimento = vencimento;
}
public String getValor() {
	return valor;
}
public void setValor(String valor) {
	this.valor = valor;
}
public String getEmpresa() {
	return empresa;
}
public void setEmpresa(String empresa) {
	this.empresa = empresa;
}
public String getGerente() {
	return gerente;
}
public void setGerente(String gerente) {
	this.gerente = gerente;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}


}
