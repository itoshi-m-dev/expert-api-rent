package com.itoshi_m_dev.lowbudgetproject.model.entities;

import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
import com.itoshi_m_dev.lowbudgetproject.model.enums.TipoEquipamento;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Equipamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "tipoEquipamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEquipamento tipoEquipamento;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEquipamento status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Equipamentos(){

    }

    public Equipamentos(String nome,
                        TipoEquipamento tipoEquipamento,
                        StatusEquipamento status) {
        this.nome = nome;
        this.tipoEquipamento = tipoEquipamento;
        this.status = status;
    }

    public Equipamentos(String nome,
                        TipoEquipamento tipoEquipamento,
                        StatusEquipamento status,
                        Cliente cliente) {
        this(nome, tipoEquipamento, status);
        this.cliente = cliente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEquipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public StatusEquipamento getStatus() {
        return status;
    }

    public void setStatus(StatusEquipamento status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Equipamentos{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipoEquipamento=" + tipoEquipamento +
                ", status=" + status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equipamentos that = (Equipamentos) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
