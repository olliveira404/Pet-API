package com.jader.api.pet_api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import com.jader.api.pet_api.model.Pet;
import com.jader.api.pet_api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pet", description = "Endpoints para gerenciamento de Pets.")
@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Operation(summary = "Listar todos os pets", description = "Retorna uma lista com todos os pets cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de pets retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Pet>> listarTodos() {
        List<Pet> pets = petService.listarTodos();
        return ResponseEntity.ok(pets);
    }

    @Operation(summary = "Buscar pet por ID", description = "Retorna os dados de um pet específico com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        return petService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar um novo pet", description = "Cria um novo registro de pet com nome, espécie, raça, idade e tutor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Pet> criarPet(@Valid @RequestBody Pet pet) {
        Pet novoPet = petService.salvar(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPet);
    }

    @Operation(summary = "Atualizar um pet", description = "Atualiza as informações de um pet existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizarPet(@PathVariable Long id, @Valid @RequestBody Pet pet) {
        try {
            Pet atualizado = petService.atualizar(id, pet);
            return ResponseEntity.ok(atualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();

        }
    }

    @Operation(summary = "Excluir um pet", description = "Remove o pet do sistema com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPet(@PathVariable Long id) {
        try {
            petService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
