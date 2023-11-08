package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.entidades.Produto;
import com.example.demo.servicos.ProdutoServico;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoServico servico;
	
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody ProdutoDTO produto){
		ProdutoDTO prod = servico.insert(produto);
		return prod !=  null ? new ResponseEntity<>("Produto criado com sucesso", HttpStatus.CREATED) 
				: new ResponseEntity<>("Erro ao criar produto", HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> findAll(){
		List<ProdutoDTO> list = servico.findAll();
		return !list.isEmpty() ?  new ResponseEntity<>(list, HttpStatus.OK) 
							   : new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/produto-jpa-id/{id}")
	public ResponseEntity<ProdutoDTO> findById(@PathVariable Integer id){
		Optional<Produto> optionalProduto = servico.findById(id);
		if (optionalProduto.isPresent()) {
			Produto produto = optionalProduto.get();
			ProdutoDTO produtoDTO = new ProdutoDTO(produto);
			return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/produto-update")
	public ResponseEntity<String> update(@RequestBody ProdutoDTO produtoDTO){
		Produto produto = new Produto();
		produto.setId(produtoDTO.getId());
		produto.setNome(produtoDTO.getNome());
		produto.setPreco(produtoDTO.getPreco());

		
		ProdutoDTO updatedProduto = servico.update(produto);

		if (updatedProduto != null) {
			return new ResponseEntity<>("Produto atualizado com sucesso", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Erro ao atualizar produto", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/produto-delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		servico.delete(id);

		if (servico.findById(id).isEmpty()) {
			return new ResponseEntity<>("Produto deletado com sucesso", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Erro ao deletar produto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/produto-jpa-id-preco-jpql/{id}/{preco}")
	public ResponseEntity<Optional<Produto>> findByIdPrecoJpql(@PathVariable Integer id, @PathVariable Double preco){
		Optional<Produto> prod = servico.findByIdPrecoJpql(id, preco);
		return prod.isPresent() ?  new ResponseEntity<>(prod, HttpStatus.OK) 
							    : new ResponseEntity<>(prod, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/produto-jpa-id-preco-sql/{id}/{preco}")
	public ResponseEntity<Optional<Produto>> findByIdPrecoSql(@PathVariable Integer id, @PathVariable Double preco){
		Optional<Produto> prod = servico.findByIdPrecoSql(id, preco);
		return prod.isPresent() ?  new ResponseEntity<>(prod, HttpStatus.OK) 
							    : new ResponseEntity<>(prod, HttpStatus.BAD_REQUEST);
	}

}
