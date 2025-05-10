package br.rosa.rjooq.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Column;

public class MyAuthorJPAAnnotated {

	@Column(name = "ID")
	private int iddddddddd;
	@Column(name = "FIRST_NAME")
	private String nomeeeeeeeee;

	public int getIddddddddd() {
		return iddddddddd;
	}

	public void setIddddddddd(int iddddddddd) {
		this.iddddddddd = iddddddddd;
	}

	public String getNomeeeeeeeee() {
		return nomeeeeeeeee;
	}

	public void setNomeeeeeeeee(String nomeeeeeeeee) {
		this.nomeeeeeeeee = nomeeeeeeeee;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).append("iddddddddd", iddddddddd)
				.append("nomeeeeeeeee", nomeeeeeeeee).toString();
	}
}
