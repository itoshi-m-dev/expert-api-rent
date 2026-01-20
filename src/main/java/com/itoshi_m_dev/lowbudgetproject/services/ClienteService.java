package com.itoshi_m_dev.lowbudgetproject.services;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteServices {

    private final ClienteRepository repository;

    public Cliente salvarClientes(Cliente cliente){
       return repository.save(cliente);
    }

    public List<Cliente> buscarClientes(){
        return repository.findAll();
    }

    public Cliente buscarPorId(String id) {
        UUID uuid = UUID.fromString(id);
        return repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente atualizarClientePorId(Cliente c3, String id){
        Cliente c2 = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        c2.setNome(c3.getNome());
        c2.setCpf(c3.getCpf());
        c2.setEmail(c3.getEmail());
        c2.setDataNascimento(c3.getDataNascimento());
        c2.setEndereco(c3.getEndereco());

        return c2;
    }

    public void deletarClientePorId(String id){
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            throw new RuntimeException("Cliente nao encontrado");
        }
        repository.deleteById(uuid);
    }

}
