package com.itoshi_m_dev.lowbudgetproject.controllers;

import com.itoshi_m_dev.lowbudgetproject.model.dto.EquipamentosDTO;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
import com.itoshi_m_dev.lowbudgetproject.services.ClienteService;
import com.itoshi_m_dev.lowbudgetproject.services.EquipamentosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/equipamentos")
@RequiredArgsConstructor
public class EquipamentosController {

    private final EquipamentosService service;
    private final ClienteService clienteService;


    @GetMapping
    public ResponseEntity<List<EquipamentosDTO>> buscarEquipamentos(){
        List<Equipamentos> equipamentosList = service.buscarEquipamentos();
        List<EquipamentosDTO> dtoList = equipamentosList.stream()
                .map(EquipamentosDTO::fromEntity)
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipamentosDTO> buscarEquipamentosPorId(@PathVariable("id")String id){
        Equipamentos equipamento = service.buscarPorId(id);
        EquipamentosDTO dto = EquipamentosDTO.fromEntity(equipamento);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/cliente/{clientId}/equipamentos")
    public ResponseEntity<List<EquipamentosDTO>> listarEquipamentosDoCliente(
            @PathVariable("clientId") String clientId){
        Cliente cliente = clienteService.buscarPorId(clientId);
        List<Equipamentos> listaequipamentos = service.buscarPorCliente(cliente);
        List<EquipamentosDTO> dtos = listaequipamentos.stream().map(EquipamentosDTO::fromEntity).toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping
    public ResponseEntity<List<EquipamentosDTO>> listarEquipamentosPorStatus(
            @RequestParam(required = false)StatusEquipamento status){
        List<Equipamentos> list;
        if (status != null){
            list = service.buscarPorStatus(status);
        }
        else {
            list = service.buscarEquipamentos();
        }
        List<EquipamentosDTO> dtos = list.stream().map(EquipamentosDTO::fromEntity).toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EquipamentosDTO> atualizarEquipamentosPorId(@PathVariable("id")String id,
                                                                      @RequestBody @Valid EquipamentosDTO dto){

    Equipamentos equipamento = service.atualizarEquipamentosPorId(dto.mapearParaEquipamentos(), id);
    EquipamentosDTO equipamentoDTO = EquipamentosDTO.fromEntity(equipamento);

    return ResponseEntity.ok(equipamentoDTO);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarEquipamentoPorId(@PathVariable("id") String id){
        service.deletarEquipamentoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EquipamentosDTO> cadastarEquipamento(@RequestBody @Valid EquipamentosDTO dto){
        Equipamentos e = service.salvarEquipamentos(dto.mapearParaEquipamentos());
        EquipamentosDTO response = EquipamentosDTO.fromEntity(e);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(e.getId()).toUri();

        return ResponseEntity.created(uri).body(response);

    }









}
