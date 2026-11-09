package br.com.suporte.chamados.model;
public record Chamado(
    Integer id,
    String titulo,
    String solicitante,
    String setor,
    Prioridade prioridade,
    Status status
) {}