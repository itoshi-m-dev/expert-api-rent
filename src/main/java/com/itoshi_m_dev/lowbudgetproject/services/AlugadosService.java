package com.itoshi_m_dev.lowbudgetproject.services;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Alugados;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.repositories.AlugadosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlugadosService {

    private final AlugadosRepository repository;

    public List<Alugados> buscarAlugados(){
        return repository.findAll();
    }

    public Alugados buscarAlugadosPorId(String id){
        UUID uuid = UUID.fromString(id);
       return repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Aluguel nao encontrado"));
    }

    public List<Alugados> buscarPorCliente(Cliente cliente){
        List<Alugados> aluguel = repository.findByCliente(cliente);
        if(aluguel.isEmpty()){
            throw new RuntimeException("Nenhum aluguel foi encontrado com este cliente");
        }
        return aluguel;
    }

    public Alugados salvarAlugados(Alugados alugados){
        return repository.save(alugados);
    }

    @Transactional
    public Alugados atualizarAlugadosPorId(Alugados a2, String id){
        Alugados a1 = buscarAlugadosPorId(id);

        a1.setCliente(a2.getCliente());
        a1.setEquipamento(a2.getEquipamento());
        a1.setStatus(a2.getStatus());
        a1.setDataDevolucao(a2.getDataDevolucao());

        return a1;

    }

    public void deletarAlugadosPorId(String id) {
        buscarAlugadosPorId(id);
        repository.deleteById(UUID.fromString(id));
    }
}
