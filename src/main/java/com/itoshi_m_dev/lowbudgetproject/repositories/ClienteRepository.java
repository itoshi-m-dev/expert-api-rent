package com.itoshi_m_dev.lowbudgetproject.repositories;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
}
