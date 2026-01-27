package com.itoshi_m_dev.lowbudgetproject.model.dto;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Alugados;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusAluguel;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AlugadosDTO(

        @NotNull(message = "Para existir um aluguel é necessário um cliente")
        UUID clienteId,
        @NotNull(message = "Para existir um aluguel é necessário um equipamento")
        UUID equipamentosId,
        @NotNull(message = "Qual a data de devolução do equipamento?")
        LocalDate dataDevolucao,
        @NotNull(message = "Qual é o status do aluguel?")
        StatusAluguel statusAluguel)
    {
        }
