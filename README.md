# API de Chamados

## Testes

### 1. Consulta de chamado por ID

**Requisição**

```bash
curl -i http://localhost:8080/chamados/4
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"}
```

**Explicação:** A aplicação já inicia com um chamado com id igual a 4 criado, então a API retorna um 200 com o JSON equivalente.

---

### 2. Consulta de ID inexistente

**Requisição**

```bash
curl -i http://localhost:8080/chamados/67
```

**Resposta**

```http
HTTP/1.1 404
Content-Length: 0
```

**Explicação:** Nenhum chamado possui esse ID, por isso o retorno 404 da requisição.

---

### 3. Cadastro de chamado

**Requisição**

```bash
curl -i -X POST http://localhost:8080/chamados \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}'
```

**Resposta**

```http
HTTP/1.1 201
Content-Type: application/json

{"id":11,"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}
```

**Explicação:** A API cria o novo chamado, guarda-o e retorna 201.

---

### 4. Verificação do chamado cadastrado

**Requisição**

```bash
curl -i http://localhost:8080/chamados/11
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

{"id":11,"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}
```

**Explicação:** o chamado criado no teste anterior é encontrado pelo ID gerado, o que mostra que ele foi salvo.

---

### 5. Resumo por status

**Requisição**

```bash
curl -i http://localhost:8080/chamados/resumo
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

{"abertos": 6,"emAndamento": 2,"resolvidos": 3,"total": 11}
```

**Explicação:** a API conta os chamados de cada status e retorna a quantidade respectiva de cada categoria, além do total.

---

### 6. Listagem de todos os chamados

**Requisição**

```bash
curl -i http://localhost:8080/chamados
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Sem acesso à rede","solicitante":"Bruno Lima","setor":"RH","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":3,"titulo":"Troca de senha","solicitante":"Carla Dias","setor":"Comercial","prioridade":"BAIXA","status":"RESOLVIDO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":5,"titulo":"Conta bloqueada","solicitante":"Elisa Prado","setor":"Jurídico","prioridade":"ALTA","status":"RESOLVIDO"},{"id":6,"titulo":"Impressora sem toner","solicitante":"Fábio Melo","setor":"RH","prioridade":"BAIXA","status":"ABERTO"},{"id":7,"titulo":"Wi-Fi instável","solicitante":"Gabi Nunes","setor":"Marketing","prioridade":"MEDIA","status":"EM_ANDAMENTO"},{"id":8,"titulo":"Notebook não liga","solicitante":"Hugo Alves","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"},{"id":9,"titulo":"Criar conta de acesso","solicitante":"Iara Costa","setor":"Marketing","prioridade":"MEDIA","status":"RESOLVIDO"},{"id":10,"titulo":"VPN não conecta","solicitante":"João Pires","setor":"TI","prioridade":"MEDIA","status":"ABERTO"},{"id":11,"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}]
```

**Explicação:** Retorna os 10 chamados iniciais mais o chamado 11 cadastrado no teste 3.

---

### 7. Filtro por setor (sem diferenciar maiúsculas)

**Requisição**

```bash
curl -i "http://localhost:8080/chamados/setor?nome=financeiro"
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"}]
```

**Explicação:** Mesmo digitando `financeiro` em minúsculas, a API encontra os chamados do setor `Financeiro` (ids 1 e 4).

---

### 8. Filtro por prioridade

**Requisição**

```bash
curl -i "http://localhost:8080/chamados/prioridade?nivel=alta"
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Sem acesso à rede","solicitante":"Bruno Lima","setor":"RH","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":5,"titulo":"Conta bloqueada","solicitante":"Elisa Prado","setor":"Jurídico","prioridade":"ALTA","status":"RESOLVIDO"},{"id":8,"titulo":"Notebook não liga","solicitante":"Hugo Alves","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"}]
```

**Explicação:** O valor `alta` é convertido para o enum `ALTA`. Retorna os 5 chamados de prioridade alta (ids 1, 2, 4, 5 e 8), independentemente do status.

---

### 9. Busca pelo título

**Requisição**

```bash
curl -i "http://localhost:8080/chamados/busca?texto=IMPRESSORA"
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":6,"titulo":"Impressora sem toner","solicitante":"Fábio Melo","setor":"RH","prioridade":"BAIXA","status":"ABERTO"}]
```

**Explicação:** A busca ignora maiúsculas e minúsculas: `IMPRESSORA` encontra os títulos que contêm "Impressora" (ids 1 e 6).

---

### 10. Chamados críticos

**Requisição**

```bash
curl -i http://localhost:8080/chamados/criticos
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Sem acesso à rede","solicitante":"Bruno Lima","setor":"RH","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":8,"titulo":"Notebook não liga","solicitante":"Hugo Alves","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"}]
```

**Explicação:** Combina prioridade `ALTA` e status diferente de `RESOLVIDO`. O chamado 5 tem prioridade alta, mas já foi resolvido, por isso não aparece.

---

### 11. Filtro por setor sem correspondência

**Requisição**

```bash
curl -i "http://localhost:8080/chamados/setor?nome=logistica"
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[]
```

**Explicação:** Nenhum chamado pertence ao setor Logística, então a API retorna 200 com lista vazia.

---

### 12. Busca por título sem correspondência

**Requisição**

```bash
curl -i "http://localhost:8080/chamados/busca?texto=teclado"
```

**Resposta**

```http
HTTP/1.1 200
Content-Type: application/json

[]
```

**Explicação:** Nenhum título contém "teclado", então a API retorna 200 com lista vazia.

---

## Análise técnica

### Como o Spring Boot recebe e processa requisições HTTP

O Spring Boot inicia um servidor web (Tomcat), que fica escutando na porta desejada, frequentemente 8080. Quando chega uma requisição, é consultado os mapeamentos (`@GetMapping`, `@PostMapping`, `@RequestMapping`) para descobrir qual método do controller atende aquele verbo e aquele caminho, por exemplo `GET /chamados/4`. Em seguida prepara os argumentos do método (valor do `@PathVariable`, parâmetros de `@RequestParam`, corpo do `@RequestBody`), executa o método e, com o valor retornado, monta a resposta HTTP com o possível corpo de retorno.

### Função do Controller

O `ChamadoController` é a camada que expõe a API, ele define as rotas, recebe os dados da requisição, executa a lógica de cada método (neste projeto, as consultas e filtros sobre a lista em memória e o cadastro) e devolve o resultado, juntamente ao status HTTP referente.

### Conversão entre objetos Java e JSON

A conversão é feita pelo Jackson, biblioteca que o Spring Boot inclui e configura por padrão, por meio dos `HttpMessageConverter`. Na entrada, o `@RequestBody` faz o Jackson ler o JSON recebido e criar um objeto `Chamado`, associando cada campo pelo nome, textos como `"ALTA"` viram o enum `Prioridade.ALTA`, e valores inválidos geram erro 400. Na saída, o objeto ou a lista retornados pelo método (como `Chamado`, `List<Chamado>` ou `ResumoChamados`) são transformados em JSON (serialização) e enviados com `Content-Type: application/json`. Como `Chamado` e `ResumoChamados` são `records`, o Jackson usa os nomes dos componentes do record como nomes dos campos do JSON.

### Por que os chamados cadastrados não permanecem após reiniciar a aplicação

Os chamados ficam em uma lista (`ArrayList`) que é um atributo do controller, ou seja, existe apenas na memória RAM da JVM enquanto a aplicação está rodando. Não há banco de dados nem arquivo (por exemplo, JSON) onde os dados sejam gravados.
