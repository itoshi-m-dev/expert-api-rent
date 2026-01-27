package com.itoshi_m_dev.lowbudgetproject.services;

import com.itoshi_m_dev.lowbudgetproject.model.dto.EquipamentosDTO;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
import com.itoshi_m_dev.lowbudgetproject.model.exceptions.EquipamentoNaoEncontrado;
import com.itoshi_m_dev.lowbudgetproject.repositories.ClienteRepository;
import com.itoshi_m_dev.lowbudgetproject.repositories.EquipamentosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosService {

    private final EquipamentosRepository repository;
    private final ClienteService clienteService;


    public List<Equipamentos> buscarEquipamentos(){
        return repository.findAll();
    }

    public Equipamentos buscarPorId(String id){
        UUID uuid = UUID.fromString(id);
        return repository.findById(uuid)
                .orElseThrow(() -> new EquipamentoNaoEncontrado("Equipamento nao encontrado"));
    }

    public Equipamentos buscarPorId(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new EquipamentoNaoEncontrado("Equipamento não encontrado"));
    }

    @Transactional
    public Equipamentos salvarEquipamentos(EquipamentosDTO dto) {
        if (dto == null) {
            throw new EquipamentoNaoEncontrado("Dados do equipamento nulos");
        }

        // 1. Mapeia o DTO para a Entidade
        Equipamentos equipamento = dto.mapearParaEquipamentos();

        // 2. Busca o cliente e associa (Garante que o ID enviado existe)
        if (dto.clienteId() != null) {
            Cliente cliente = clienteService.buscarPorId(dto.clienteId());
            equipamento.setCliente(cliente);
        }

        // 3. Salva a entidade já com o relacionamento preenchido
        return repository.save(equipamento);
    }

    @Transactional
    public Equipamentos atualizarEquipamentosPorId(EquipamentosDTO dto, String id) {
        UUID uuid = UUID.fromString(id);
        Equipamentos e1 = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));

        // Atualiza os dados básicos
        e1.setNome(dto.nome());
        e1.setTipoEquipamento(dto.tipoEquipamento());
        e1.setStatus(dto.status());

        // BUSCA E ATUALIZA O CLIENTE
        if (dto.clienteId() != null) {
            Cliente novoCliente = clienteService.buscarPorId(dto.clienteId());
            e1.setCliente(novoCliente);
        }

        // No Spring Data, com @Transactional, você nem precisaria do repository.save(e1)
        // pois ele salva automaticamente ao fim do método (Dirty Checking),
        // mas pode manter se preferir.
        return e1;
    }

     public void deletarEquipamentoPorId(String id){
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)){
            throw new EquipamentoNaoEncontrado("Equipamento nao encontrado");

        }
        repository.deleteById(uuid);
     }

     public List<Equipamentos> buscarPorCliente(Cliente cliente){
        List<Equipamentos> equipamentosLista = repository.findByCliente(cliente);
        if (equipamentosLista.isEmpty()){
            throw new EquipamentoNaoEncontrado("Nao foi encontrado nenhum equipamento com este cliente");

        }

        return equipamentosLista;

     }

     public List<Equipamentos> buscarPorStatus(StatusEquipamento status) {
         List<Equipamentos> stList = repository.findByStatus(status);
         if (stList.isEmpty()) {
             throw new EquipamentoNaoEncontrado("Nao foi encontrado nenhum equipamento com este status");

         }
         return stList;
     }
}
