package br.com.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@CrossOrigin("*") //desenvolvimento
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	
	@PostMapping
	public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
		Produto novoProduto = produtoService.criar(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> alterar(@PathVariable Integer id, @RequestBody Produto produto) {
		Produto produtoAlterado = produtoService.alterar(id, produto);
		return ResponseEntity.ok(produtoAlterado);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Produto>> listar()  {
		List<Produto> produtos = produtoService.listar();
		return ResponseEntity.ok(produtos);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> excluir(@PathVariable Integer id)  {
		String mensagem = produtoService.excluir(id);
		return ResponseEntity.ok(mensagem);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> consultar(@PathVariable Integer id)  {
		Produto produto = produtoService.consultar(id);
		return ResponseEntity.ok(produto);
	}
}
