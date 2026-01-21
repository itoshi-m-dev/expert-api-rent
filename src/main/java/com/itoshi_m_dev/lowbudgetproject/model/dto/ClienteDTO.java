package com.itoshi_m_dev.lowbudgetproject.model.dto;

import com.itoshi_m_dev.lowbudgetproject.model.entities.Cliente;
import com.itoshi_m_dev.lowbudgetproject.model.entities.Equipamentos;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClienteDTO(

                         @NotBlank(message = "Nome é obrigatório")
                         @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
                         String nome,

                         @NotBlank(message = "CPF é obrigatório")
                         @CPF
                         String cpf,

                         @NotBlank(message = "Email é obrigatório")
                         @Email
                         String email,

                         @NotNull(message = "Data de nascimento é obrigatória")
                         @Past(message = "Data de nascimento deve ser no passado")
                         LocalDate dataNascimento,

                         @NotBlank(message = "Endereço é obrigatório")
                         @Size(min = 5, max = 255)
                         String endereco,

                         List<Equipamentos> equipamentosAlugadosList ) {

    public Cliente mapearParaCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setEmail(this.email);
        cliente.setDataNascimento(this.dataNascimento);
        cliente.setEndereco(this.endereco);

        return cliente;
    }

    public static ClienteDTO fromEntity(Cliente cliente) {
        return new ClienteDTO(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getDataNascimento(),
                cliente.getEndereco(),
                cliente.getEquipamentosAlugadosList()
        );
    }

}
