package com.itoshi_m_dev.lowbudgetproject.model.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EquipamentoNaoEncontrado.class)
    public ResponseEntity<ApiError> handleNotFound(EquipamentoNaoEncontrado e, HttpServletRequest http){
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Equipamento nao encontrado",
                e.getMessage(),
                http.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(ClienteCadastradoException.class)
    public ResponseEntity<ApiError> handleAlreadyExists (ClienteCadastradoException e, HttpServletRequest http){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Cliente ja existe",
                e.getMessage(),
                http.getRequestURI()
        );
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ImpossivelDeletarException.class)
    public ResponseEntity<ApiError> handleImpossibleToDeleteFromRent (ImpossivelDeletarException e, HttpServletRequest http){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Impossivel deletar um equipamento emprestado",
                e.getMessage(),
                http.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ImpossivelEmprestarException.class)
    public ResponseEntity<ApiError> handleImpossibleToLend (ImpossivelEmprestarException e, HttpServletRequest http){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Impossivel emprestar um equipamento ja emprestado",
                e.getMessage(),
                http.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(AluguelNaoEncontrado.class)
    public ResponseEntity<ApiError> handleRentNotFound(AluguelNaoEncontrado e, HttpServletRequest http){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Nao Foi encontrado nenhum Aluguel com esta informacao",
                e.getMessage(),
                http.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ClienteNaoEncontrado.class)
    public ResponseEntity<ApiError> handleClientNotFound(ClienteNaoEncontrado e, HttpServletRequest http){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Nenhum cliente encontrado",
                e.getMessage(),
                http.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ClienteComDadosIguais.class)
    public ResponseEntity<ApiError> handleSameClient(ClienteComDadosIguais e, HttpServletRequest http){
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "O cliente a ser atualizado tem dados iguais com o que voce deseja. ",
                e.getMessage(),
                http.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


}
