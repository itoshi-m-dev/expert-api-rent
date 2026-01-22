package com.itoshi_m_dev.lowbudgetproject.controllers;

import com.itoshi_m_dev.lowbudgetproject.model.dto.ClienteDTO;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarClientes(){
        List<Cliente> clientes = service.buscarClientes();

        List<ClienteDTO> dtos = clientes.stream().map(ClienteDTO::fromEntity).toList();

        return ResponseEntity.ok().body(dtos);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("id")String id){
        Cliente c1 = service.buscarPorId(id);

        ClienteDTO dto = ClienteDTO.fromEntity(c1);

        return ResponseEntity.ok().body(dto);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> atualizarClientePorId(@PathVariable("id") String id, @RequestBody @Valid ClienteDTO dto){
        Cliente clienteAtualizado = service.atualizarClientePorId(dto.mapearParaCliente(),id);
        ClienteDTO novoDTO = ClienteDTO.fromEntity(clienteAtualizado);

        return ResponseEntity.ok(novoDTO);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarClientePorId(@PathVariable("id") String id){
        service.deletarClientePorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody @Valid ClienteDTO dto){
        Cliente clienteSalvo = service.salvarClientes(dto.mapearParaCliente());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(ClienteDTO.fromEntity(clienteSalvo));
    }




}
