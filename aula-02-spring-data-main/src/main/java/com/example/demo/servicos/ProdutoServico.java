package com.example.demo.servicos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.entidades.Produto;
import com.example.demo.repositorio.ProdutoRepositorioJPA;

@Service
public class ProdutoServico {
	
	@Autowired
	private ProdutoRepositorioJPA produtoRepositorioJPA;
	
	public ProdutoDTO insert(ProdutoDTO produto) {
		Produto prod = new Produto(produto.getId(), produto.getNome(), produto.getPreco());	
		return new ProdutoDTO(produtoRepositorioJPA.save(prod));
		
	}
	
	
	public List<ProdutoDTO> findAll() {
		List<Produto> list = produtoRepositorioJPA.findAll();
		return list.stream().map(x -> new ProdutoDTO(x)).collect(Collectors.toList());
		
	}
	
	public Optional<Produto> findById(Integer id) {
		return produtoRepositorioJPA.findById(id);
		
	}
	
	public void delete(Integer id) {
		produtoRepositorioJPA.deleteById(id);
	}
	
	public Optional<Produto> findByIdPrecoJpql(Integer id, Double preco) {
		return produtoRepositorioJPA.findProdutoParam(id, preco);
		
	}
	
	public Optional<Produto> findByIdPrecoSql(Integer id, Double preco) {
		return produtoRepositorioJPA.findProdutoParamSql(id, preco);
		
	}


}
