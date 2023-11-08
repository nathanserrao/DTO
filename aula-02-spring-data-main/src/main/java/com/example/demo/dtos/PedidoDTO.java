package com.example.demo.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entidades.Pedido;

public class PedidoDTO {
	
	private Integer id;
	private String endereco;
	private List<ProdutoDTO> produtos = new ArrayList<>();
	
	public PedidoDTO() {}

	public PedidoDTO(Integer id, String endereco) {
		super();
		this.id = id;
		this.endereco = endereco;
	}
	
	
	public PedidoDTO(Pedido pedido) {
		id = pedido.getId();
		endereco = pedido.getEndereco();
		produtos = pedido.getProdutos().stream().map(x -> new ProdutoDTO(x)).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}

	
	
	
	

}
