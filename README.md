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

### 6. Listagem de todos os chamados

**Requisição**
```bash
curl -i http://localhost:8080/chamados
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Sem acesso à rede","solicitante":"Bruno Lima","setor":"RH","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":3,"titulo":"Troca de senha","solicitante":"Carla Dias","setor":"Comercial","prioridade":"BAIXA","status":"RESOLVIDO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":5,"titulo":"Conta bloqueada","solicitante":"Elisa Prado","setor":"Jurídico","prioridade":"ALTA","status":"RESOLVIDO"},{"id":6,"titulo":"Impressora sem toner","solicitante":"Fábio Melo","setor":"RH","prioridade":"BAIXA","status":"ABERTO"},{"id":7,"titulo":"Wi-Fi instável","solicitante":"Gabi Nunes","setor":"Marketing","prioridade":"MEDIA","status":"EM_ANDAMENTO"},{"id":8,"titulo":"Notebook não liga","solicitante":"Hugo Alves","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"},{"id":9,"titulo":"Criar conta de acesso","solicitante":"Iara Costa","setor":"Marketing","prioridade":"MEDIA","status":"RESOLVIDO"},{"id":10,"titulo":"VPN não conecta","solicitante":"João Pires","setor":"TI","prioridade":"MEDIA","status":"ABERTO"},{"id":11,"titulo":"Mouse quebrado","solicitante":"Maria","setor":"TI","prioridade":"BAIXA","status":"ABERTO"}]
```

**Explicação:** Retorna os 10 chamados iniciais mais o chamado 11 cadastrado no teste 3.

### 7. Filtro por setor (sem diferenciar maiúsculas)

**Requisição**
```bash
curl -i "http://localhost:8080/chamados/setor?nome=financeiro"
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"}]
```

**Explicação:** Mesmo digitando `financeiro` em minúsculas, a API encontra os chamados do setor `Financeiro` (ids 1 e 4).

### 8. Filtro por prioridade

**Requisição**
```bash
curl -i "http://localhost:8080/chamados/prioridade?nivel=alta"
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Sem acesso à rede","solicitante":"Bruno Lima","setor":"RH","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":5,"titulo":"Conta bloqueada","solicitante":"Elisa Prado","setor":"Jurídico","prioridade":"ALTA","status":"RESOLVIDO"},{"id":8,"titulo":"Notebook não liga","solicitante":"Hugo Alves","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"}]
```

**Explicação:** O valor `alta` é convertido para o enum `ALTA`. Retorna os 5 chamados de prioridade alta (ids 1, 2, 4, 5 e 8), independentemente do status.

### 9. Busca pelo título

**Requisição**
```bash
curl -i "http://localhost:8080/chamados/busca?texto=IMPRESSORA"
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":6,"titulo":"Impressora sem toner","solicitante":"Fábio Melo","setor":"RH","prioridade":"BAIXA","status":"ABERTO"}]
```

**Explicação:** A busca ignora maiúsculas e minúsculas: `IMPRESSORA` encontra os títulos que contêm "Impressora" (ids 1 e 6).

### 10. Chamados críticos

**Requisição**
```bash
curl -i http://localhost:8080/chamados/criticos
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"titulo":"Impressora não imprime","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Sem acesso à rede","solicitante":"Bruno Lima","setor":"RH","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":4,"titulo":"Computador fora da tomada","solicitante":"Diego Reis","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":8,"titulo":"Notebook não liga","solicitante":"Hugo Alves","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"}]
```

**Explicação:** Combina prioridade `ALTA` e status diferente de `RESOLVIDO`. O chamado 5 tem prioridade alta, mas já foi resolvido, por isso não aparece.

### 11. Filtro por setor sem correspondência

**Requisição**
```bash
curl -i "http://localhost:8080/chamados/setor?nome=logistica"
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[]
```

**Explicação:** Nenhum chamado pertence ao setor Logística, então a API retorna 200 com lista vazia.

### 12. Busca por título sem correspondência

**Requisição**
```bash
curl -i "http://localhost:8080/chamados/busca?texto=teclado"
```

**Resposta**
```
HTTP/1.1 200
Content-Type: application/json

[]
```

**Explicação:** Nenhum título contém "teclado", então a API retorna 200 com lista vazia.