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

import br.com.sigep.model.Venda;
import br.com.sigep.service.VendaService;

@RestController
@RequestMapping("/vendas")
@CrossOrigin("*") //Obs esta assim por estar em faze de desenvolvimento
public class VendaController {

	@Autowired
	private VendaService vendaService;
	
	
	@PostMapping
	public ResponseEntity<Venda> criar(@RequestBody Venda venda) {
		Venda novaVenda = vendaService.criar(venda);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Venda> alterar(@PathVariable Integer id, @RequestBody Venda venda) {
		Venda vendaAlterda = vendaService.alterar(id, venda);
		return ResponseEntity.ok(vendaAlterda);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Venda>> listar()  {
		List<Venda> vendas = vendaService.listar();
		return ResponseEntity.ok(vendas);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> excluir(@PathVariable Integer id)  {
		String mensagem = vendaService.excluir(id);
		return ResponseEntity.ok(mensagem);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venda> consultar(@PathVariable Integer id)  {
		Venda venda = vendaService.consultar(id);
		return ResponseEntity.ok(venda);
	}
}
