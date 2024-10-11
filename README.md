# Teste prático SIGEP

- Projeto Back-end

## Tecnologias utilizadas

- Java
- Spring boot
- SGBD MySQL
- JdbcTemplate
  
## Tabelas Banco de Dados

```sql
CREATE TABLE produto (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(150) NOT NULL,
  descricao VARCHAR(200) DEFAULT NULL,
  quantidade_disponivel INT NOT NULL,
  valor_unitario DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;


CREATE TABLE venda (
  id INT NOT NULL AUTO_INCREMENT,
  cliente VARCHAR(150) NOT NULL,
  valor_total DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (id)
)engine=InnoDB default charset=utf8;


CREATE TABLE venda_produto (
  venda_id INT NOT NULL,
  produto_id INT NOT NULL,
  quantidade INT NOT NULL,
  PRIMARY KEY (venda_id, produto_id),
  CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES produto (id),
  CONSTRAINT fk_venda FOREIGN KEY (venda_id) REFERENCES venda (id)
)engine=InnoDB default charset=utf8;
```


