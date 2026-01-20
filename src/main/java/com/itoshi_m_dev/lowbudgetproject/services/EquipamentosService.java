package com.itoshi_m_dev.lowbudgetproject.services;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.repositories.EquipamentosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosServices {

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


}
