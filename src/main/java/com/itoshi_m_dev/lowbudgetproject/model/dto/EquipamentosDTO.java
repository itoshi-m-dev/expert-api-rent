package com.itoshi_m_dev.lowbudgetproject.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import com.itoshi_m_dev.lowbudgetproject.model.enums.StatusEquipamento;
import com.itoshi_m_dev.lowbudgetproject.model.enums.TipoEquipamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EquipamentosDTO (

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome do equipamento deve ser entre 3 e 100 caracteres.")
        String nome,

        @NotNull(message = "Tipo do equipamento é obrigatório")
        TipoEquipamento tipoEquipamento,

        @NotNull(message = "Status é obrigatório")
        StatusEquipamento status,

        @JsonProperty("clienteId")
        UUID clienteId
)  {

    public Equipamentos mapearParaEquipamentos() {
        Equipamentos novoEquipamento = new Equipamentos();
        novoEquipamento.setNome(this.nome);
        novoEquipamento.setTipoEquipamento(this.tipoEquipamento);
        novoEquipamento.setStatus(this.status);
        return novoEquipamento;

    }

    public static EquipamentosDTO fromEntity(Equipamentos e){
        return new EquipamentosDTO(
                e.getNome(),
                e.getTipoEquipamento(),
                e.getStatus(),
                e.getCliente().getId()
        );
    }
}
