package model;

public class ComprasServicos extends Itens{
	int id, supervisorid, gerenteid;
	
	String justificativa, status, supervisor, supervisorhash, gerente, gerentehash, data, unidade;
	


	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

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

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSupervisorhash() {
		return supervisorhash;
	}

	public void setSupervisorhash(String supervisorhash) {
		this.supervisorhash = supervisorhash;
	}

	public String getGerentehash() {
		return gerentehash;
	}

	public void setGerentehash(String gerentehash) {
		this.gerentehash = gerentehash;
	}
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}