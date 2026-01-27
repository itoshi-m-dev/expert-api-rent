package com.itoshi_m_dev.lowbudgetproject.repositories;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Alugados;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlugadosRepository extends JpaRepository<Alugados, UUID> {

    List<Alugados> findByClienteId(UUID clienteId);
}
