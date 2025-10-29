# ToolsChallenge

### Introdução
Projeto implementando uma API de Pagamentos.

Tecnologias utilizadas nesse projeto:

Java 21

Spring Boot 3.4.5

JUnit 5

Maven

H2

Rodando localmente o endereço da applicação é: localhost:8080

### Endpoints
POST /transacao: Salva a transacao/processa o pagamento

GET /transacao: Busca por todas as transações salvas no banco de dados

GET /transacao/id: Busca uma transação especifica pelo id

PUT /transacao/id: Atualiza uma transação especifica baseado no id passado

DELETE /transacao/id: Deleta uma transação baseado no id passado

/PUT /transacao/estorno/id: processa o estorno de um pagamento baseado no id passado
