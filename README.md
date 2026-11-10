# API de Chamados

## Testes

Requisições feitas com a aplicação recém-iniciada, nesta ordem.

### 1. Consulta de chamado por ID

**Requisição**
```bash
curl -i http://localhost:8080/chamados/4
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"}
```

**Explicação:** A aplicação já inicia com um chamado com id igual a 4 criado, então a API retorna um 200 com o JSON equivalente. 

### 2. Consulta de ID inexistente

**Requisição**
```bash
curl -i http://localhost:8080/chamados/67
```

**Resposta**
```
HTTP/1.1 404
Content-Length: 0
```

**Explicação:** Nenhum chamado possui esse ID, por isso o retorno 404 da requisição.

### 3. Cadastro de chamado

**Requisição**
```bash
curl -i -X POST http://localhost:8080/chamados \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}'
```

**Resposta**
```
HTTP/1.1 201
Content-Type: application/json

{"id":11,"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}
```

**Explicação:** A API cria o novo chamado, guarda-o e retorna 201. 

### 4. Verificação do chamado cadastrado

**Requisição**
```bash
curl -i http://localhost:8080/chamados/11
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

{"id":11,"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}
```

**Explicação:** o chamado criado no teste anterior é encontrado pelo ID gerado, o que mostra que ele foi salvo. 

### 5. Resumo por status

**Requisição**
```bash
curl -i http://localhost:8080/chamados/resumo
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

{"abertos": 6,"emAndamento": 2,"resolvidos": 3,"total": 11}
```

**Explicação:** a API conta os chamados de cada status e retorna a quantidade respectiva de cada categoria, além do total. 
