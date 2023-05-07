# Documentação

## Classes

### CustomDotParser

Usada para interpretar um arquivo .dot para um Graph da aplicação.

#### static Graph parse(String path)

Recebe um caminho como parâmetro e retorna um Grapho parseado. Pode lançar algumas exceções caso não seja um grafo direcionado.

### McCabeRunner

Usara para executar o critério de McCabe e retornar os caminhos do grafo.

#### public List<Path> run()

Executa o critério com base no grafo passado para o construtor e retorna uma lista de Path.

### Graph

Abstração que representa um grafo.

### Node

Abstração que representa um nó do grafo.

### Link

Abstração que representa uma aresta do grafo.

### Path

Abstração que representa um caminho.

## Testes

Existem testes automatizados implementados através do JUnit. Todos eles estão localizados no diretório [src/test](./src/test).
