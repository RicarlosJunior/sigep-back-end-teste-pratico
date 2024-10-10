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

import br.com.sigep.model.Venda;
import br.com.sigep.service.VendaService;

@RestController
@RequestMapping("/vendas")
@CrossOrigin("*")
public class VendaController {

	@Autowired
	private VendaService vendaService;
	
	
	@PostMapping
	public ResponseEntity<Venda> criar(@RequestBody Venda venda) {
		venda = vendaService.criar(venda);
		return ResponseEntity.ok(venda);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Venda> alterar(@PathVariable Integer id, @RequestBody Venda venda) {
		venda = vendaService.alterar(venda);
		return ResponseEntity.ok(venda);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Venda>> listar()  {
		return ResponseEntity.ok(vendaService.listar());
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> excluir(@PathVariable Integer id)  {
		return ResponseEntity.ok(vendaService.excluir(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venda> consultar(@PathVariable Integer id)  {
		return ResponseEntity.ok(vendaService.consultar(id));
	}
}
