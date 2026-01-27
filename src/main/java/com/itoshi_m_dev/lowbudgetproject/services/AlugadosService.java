package com.itoshi_m_dev.lowbudgetproject.services;

import com.itoshi_m_dev.lowbudgetproject.model.dto.EquipamentosDTO;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Alugados;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusAluguel;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
import com.itoshi_m_dev.lowbudgetproject.model.exceptions.AluguelNaoEncontrado;
import com.itoshi_m_dev.lowbudgetproject.model.exceptions.ImpossivelEmprestarException;
import com.itoshi_m_dev.lowbudgetproject.repositories.AlugadosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlugadosService {

    private final AlugadosRepository repository;
    private final ClienteService clienteService;
    private final EquipamentosService equipamentosService;

    public List<Alugados> buscarAlugados(){
        return repository.findAll();
    }

    public Alugados buscarAlugadosPorId(String id){
        UUID uuid = UUID.fromString(id);
       return repository.findById(uuid)
                .orElseThrow(() -> new AluguelNaoEncontrado("Aluguel nao encontrado"));
    }

    public List<Alugados> buscarPorCliente(UUID clienteId){
        List<Alugados> aluguel = repository.findByClienteId(clienteId);
        if(aluguel.isEmpty()){
            throw new AluguelNaoEncontrado("Nenhum aluguel foi encontrado com este cliente");
        }
        return aluguel;
    }

    public Alugados salvarAlugados(Alugados alugados){
        if (alugados == null){
            throw new AluguelNaoEncontrado("Aluguel nao encontrado");
        }
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

    @Transactional
    public void alugar(UUID equipamentoId, UUID clienteId){
        Equipamentos equipamento = equipamentosService.buscarPorId(equipamentoId);
        Cliente cliente = clienteService.buscarPorId(clienteId);
        if(equipamento.getStatus() == StatusEquipamento.DISPONIVEL){
            Alugados a = new Alugados(cliente,equipamento, LocalDate.parse("2027-01-01"), StatusAluguel.EM_ANDAMENTO);
            equipamento.setStatus(StatusEquipamento.ALUGADO);
            repository.save(a);
            Equipamentos dto = equipamentosService.salvarEquipamentos(new EquipamentosDTO(equipamento.getNome(),
                    equipamento.getTipoEquipamento(), equipamento.getStatus(), equipamento.getCliente().getId()));
        }
        else {
            throw new ImpossivelEmprestarException("Equipamento indisponivel para aluguel");
        }
    }
}
