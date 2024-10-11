# Teste prático SIGEP

- Projeto Back-end

## Tecnologias utilizadas

- Java
- Spring boot
- SGBD MySQL
- JdbcTemplate
  
## Tabelas Banco de Dados

CREATE TABLE `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `quantidade_disponivel` int NOT NULL,
  `valor_unitario` decimal(13,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `venda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente` varchar(150) NOT NULL,
  `valor_total` decimal(13,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `venda_produto` (
  `venda_id` int NOT NULL,
  `produto_id` int NOT NULL,
  `quantidade` int NOT NULL,
  PRIMARY KEY (`venda_id`,`produto_id`),
  KEY `fk_produto` (`produto_id`),
  CONSTRAINT `fk_produto` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`),
  CONSTRAINT `fk_venda` FOREIGN KEY (`venda_id`) REFERENCES `venda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;



