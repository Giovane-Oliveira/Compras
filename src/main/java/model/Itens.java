package model;

public class Itens extends Colaborador {
	int compraServicos_id, id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	String quantidade, item, especificacao;
	
	public int getCompraServicos_id() {
		return compraServicos_id;
	}

	public void setCompraServicos_id(int compraServicos_id) {
		this.compraServicos_id = compraServicos_id;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}

}
