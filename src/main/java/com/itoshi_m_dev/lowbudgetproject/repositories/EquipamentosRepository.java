package com.itoshi_m_dev.lowbudgetproject.repositories;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, UUID> {

    List<Equipamentos> findByCliente(Cliente cliente);

    List<Equipamentos> findByStatus(StatusEquipamento status);
}
