package br.com.sigep.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Venda {

	private Integer id;
	private String cliente;
	private BigDecimal valorTotal;
	private List<VendaProduto> vendaProdutos;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public List<VendaProduto> getVendaProdutos() {
		return vendaProdutos;
	}
	public void setVendaProdutos(List<VendaProduto> vendaProdutos) {
		this.vendaProdutos = vendaProdutos;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Venda [id=" + id + ", cliente=" + cliente + ", valorTotal=" + valorTotal + ", vendaProdutos="
				+ vendaProdutos + "]";
	}
}
