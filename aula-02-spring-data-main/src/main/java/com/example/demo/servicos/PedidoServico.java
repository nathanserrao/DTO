package com.example.demo.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.PedidoDTO;
import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.entidades.Pedido;
import com.example.demo.entidades.Produto;
import com.example.demo.repositorio.PedidoRepositorioJpa;
import com.example.demo.repositorio.ProdutoRepositorioJPA;

@Service
public class PedidoServico {
	
	
	@Autowired
	private PedidoRepositorioJpa repository;
	
	@Autowired
	private ProdutoRepositorioJPA produtoRepositorioJPA;
	
	
	@Transactional
	public PedidoDTO insert(PedidoDTO pedido) {
		Pedido ped = new Pedido(pedido.getEndereco());
		
		for(ProdutoDTO p : pedido.getProdutos()) {
			Produto produto = produtoRepositorioJPA.getReferenceById(p.getId());
			ped.getProdutos().add(produto);
		}
		
		ped = repository.save(ped);
		return new PedidoDTO(ped);
		
	}
	
	@Transactional(readOnly = true)
	public List<PedidoDTO> findAll (){
		List<Pedido> list = repository.findAll();
		return list.stream().map(x -> new PedidoDTO(x)).collect(Collectors.toList());
	}

}
