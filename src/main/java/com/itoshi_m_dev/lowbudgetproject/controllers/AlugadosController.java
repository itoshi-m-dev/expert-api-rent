package com.itoshi_m_dev.lowbudgetproject.controllers;

import com.itoshi_m_dev.lowbudgetproject.model.dto.AlugadosDTO;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Alugados;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.repositories.ClienteRepository;
import com.itoshi_m_dev.lowbudgetproject.repositories.EquipamentosRepository;
import com.itoshi_m_dev.lowbudgetproject.services.AlugadosService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/alugados")
@RequiredArgsConstructor
public class AlugadosController {

    private final AlugadosService service;
    private final ClienteRepository clienteRepository;
    private final EquipamentosRepository equipamentosRepository;


    @GetMapping
    public ResponseEntity<List<AlugadosDTO>> buscarAlugueis(){
        List<Alugados> listalugueis = service.buscarAlugados();
        List<AlugadosDTO> dtos = listalugueis.stream().map(a -> new AlugadosDTO(a.getCliente().getId(),
                a.getEquipamento().getId(), a.getDataDevolucao(), a.getStatus()))
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlugadosDTO> buscarAlugueisPorId(@PathVariable("id") String id){
        Alugados a = service.buscarAlugadosPorId(id);
        AlugadosDTO dto = new AlugadosDTO(a.getCliente().getId(),
                a.getEquipamento().getId(),
                a.getDataDevolucao(),
                a.getStatus());

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AlugadosDTO>> buscarAlugueisDocliente(@RequestParam UUID clienteId){
        List<Alugados> a = service.buscarPorCliente(clienteId);
        List<AlugadosDTO> dtos = a.stream().map(x-> new AlugadosDTO(x.getCliente().getId(),
                        x.getEquipamento().getId(), x.getDataDevolucao(), x.getStatus()))
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<AlugadosDTO> cadastrarAlugados(@RequestBody @Valid AlugadosDTO dto){
            Cliente cliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(()-> new RuntimeException("Cliente nao encontrado"));

            Equipamentos equipamentos = equipamentosRepository.findById(dto.equipamentosId())
                    .orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));

            Alugados a = service.salvarAlugados(new Alugados(cliente, equipamentos, dto.dataDevolucao(), dto.statusAluguel()));

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}").
                    buildAndExpand(a.getCliente().getId())
                    .toUri();

            return ResponseEntity.created(uri).body(new AlugadosDTO(a.getCliente().getId(),
                    a.getEquipamento().getId(), a.getDataDevolucao(), a.getStatus()));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarAlugadoPorId(@PathVariable String id){
       service.deletarAlugadosPorId(id);
       return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlugadosDTO> atualizarAlugadoPorId(@PathVariable String id, @Valid @RequestBody AlugadosDTO dtoRequest){

        Cliente cliente = clienteRepository.findById(dtoRequest.clienteId())
                .orElseThrow(()-> new RuntimeException("Cliente nao encontrado"));

        Equipamentos equipamentos = equipamentosRepository.findById(dtoRequest.equipamentosId())
                .orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));

        Alugados a = service.buscarAlugadosPorId(id);
        a.setCliente(cliente);
        a.setEquipamento(equipamentos);
        a.setDataDevolucao(dtoRequest.dataDevolucao());
        a.setStatus(dtoRequest.statusAluguel());

        AlugadosDTO dtoResponse = new AlugadosDTO(a.getCliente().getId(), a.getEquipamento().getId(),
                a.getDataDevolucao(), a.getStatus());

        return ResponseEntity.ok(dtoResponse);

    }

    @PostMapping(value = "/alugar")
    public ResponseEntity<Void> alugar(@RequestBody @Valid AlugadosDTO dtoRequest) {
        service.alugar(dtoRequest.equipamentosId(), dtoRequest.clienteId());

        return ResponseEntity.noContent().build();
    }

}
