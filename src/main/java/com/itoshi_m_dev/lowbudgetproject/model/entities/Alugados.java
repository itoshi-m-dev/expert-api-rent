package com.itoshi_m_dev.lowbudgetproject.model.entities;

import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusAluguel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Alugados {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamentos equipamento;

    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    private StatusAluguel status;

    public Alugados(){}

    public Alugados(Cliente cliente, Equipamentos equipamento, LocalDate dataDevolucao, StatusAluguel status) {
        this.cliente = cliente;
        this.equipamento = equipamento;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Equipamentos getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamentos equipamento) {
        this.equipamento = equipamento;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusAluguel status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Alugados{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", equipamento=" + equipamento +
                ", dataDevolucao=" + dataDevolucao +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Alugados alugados = (Alugados) o;
        return Objects.equals(id, alugados.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
