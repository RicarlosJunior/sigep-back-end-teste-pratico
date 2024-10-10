package br.com.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sigep.model.Produto;
import br.com.sigep.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	
	@PostMapping
	public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
		produto = produtoService.criar(produto);
		return ResponseEntity.ok(produto);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> alterar(@PathVariable Integer id, @RequestBody Produto produto) {
		produto = produtoService.alterar(produto);
		return ResponseEntity.ok(produto);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Produto>> listar()  {
		return ResponseEntity.ok(produtoService.listar());
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> excluir(@PathVariable Integer id)  {
		return ResponseEntity.ok(produtoService.excluir(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> consultar(@PathVariable Integer id)  {
		return ResponseEntity.ok(produtoService.consultar(id));
	}
	
}
