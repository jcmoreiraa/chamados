package br.com.suporte.chamados.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.suporte.chamados.model.Chamado;
import br.com.suporte.chamados.model.Prioridade;
import br.com.suporte.chamados.model.Status;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final List<Chamado> chamados = new ArrayList<>(
        List.of(
            new Chamado(1, "Impressora não imprime", "Ana Souza", "Financeiro", Prioridade.ALTA, Status.ABERTO),
            new Chamado(2, "Sem acesso à rede", "Bruno Lima", "RH", Prioridade.ALTA, Status.EM_ANDAMENTO),
            new Chamado(3, "Troca de senha", "Carla Dias", "Comercial", Prioridade.BAIXA, Status.RESOLVIDO),
            new Chamado(4, "Computador fora da tomada", "Diego Reis", "Financeiro", Prioridade.ALTA, Status.ABERTO),
            new Chamado(5, "Conta bloqueada", "Elisa Prado", "Jurídico", Prioridade.ALTA, Status.RESOLVIDO),
            new Chamado(6, "Impressora sem toner", "Fábio Melo", "RH", Prioridade.BAIXA, Status.ABERTO),
            new Chamado(7, "Wi-Fi instável", "Gabi Nunes", "Marketing", Prioridade.MEDIA, Status.EM_ANDAMENTO),
            new Chamado(8, "Notebook não liga", "Hugo Alves", "Comercial", Prioridade.ALTA, Status.ABERTO),
            new Chamado(9, "Criar conta de acesso", "Iara Costa", "Marketing", Prioridade.MEDIA, Status.RESOLVIDO),
            new Chamado(10, "VPN não conecta", "João Pires", "TI", Prioridade.MEDIA, Status.ABERTO)
        )
    );
    private int proxId = 11;
    


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chamado criarChamado(@RequestBody Chamado chamado) {
        Chamado novoChamado = new Chamado(
            proxId,
            chamado.titulo(),
            chamado.solicitante(),
            chamado.setor(),
            chamado.prioridade(),
            chamado.status()
        );
        proxId++;
        chamados.add(novoChamado);
        return novoChamado;
    }

    @GetMapping
    public List<Chamado> listarChamados() {
        return chamados;
    }


    
}