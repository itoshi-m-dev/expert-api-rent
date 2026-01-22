package com.itoshi_m_dev.lowbudgetproject.services;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
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


    public List<Equipamentos> buscarEquipamentos(){
        return repository.findAll();
    }

    public Equipamentos buscarPorId(String id){
        UUID uuid = UUID.fromString(id);
        return repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));
    }

    public Equipamentos salvarEquipamentos(Equipamentos e){
         return repository.save(e);
    }

    @Transactional
    public Equipamentos atualizarEquipamentosPorId(Equipamentos e3, String id){
           UUID uuid = UUID.fromString(id);
           Equipamentos e1 = repository.findById(uuid)
                   .orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));

           e1.setNome(e3.getNome());
           e1.setTipoEquipamento(e3.getTipoEquipamento());
           e1.setStatus(e3.getStatus());
           e1.setCliente(e3.getCliente());

           return e1;

    }

     public void deletarEquipamentoPorId(String id){
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)){
            throw new RuntimeException("Equipamento nao encontrado");

        }
        repository.deleteById(uuid);
     }

     public List<Equipamentos> buscarPorCliente(Cliente cliente){
        List<Equipamentos> equipamentosLista = repository.findByCliente(cliente);
        if (equipamentosLista.isEmpty()){
            throw new RuntimeException("Nao foi encontrado nenhum equipamento com este cliente");

        }

        return equipamentosLista;

     }

     public List<Equipamentos> buscarPorStatus(StatusEquipamento status) {
         List<Equipamentos> stList = repository.findByStatus(status);
         if (stList.isEmpty()) {
             throw new RuntimeException("Nao foi encontrado nenhum equipamento com este cliente");

         }
         return stList;
     }
}
