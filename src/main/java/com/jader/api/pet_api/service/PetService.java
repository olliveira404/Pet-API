package com.jader.api.pet_api.service;

import com.jader.api.pet_api.model.Pet;
import com.jader.api.pet_api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<Pet> listarTodos() {
        return petRepository.findAll();
    }

    public Optional<Pet> buscarPorId(Long id) {
        return petRepository.findById(id);
    }

    public Pet salvar(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet atualizar(Long id, Pet petAtualizado) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com o ID: "+ id));

        pet.setNome(petAtualizado.getNome());
        pet.setEspecie(petAtualizado.getEspecie());
        pet.setRaca(petAtualizado.getRaca());
        pet.setIdade(petAtualizado.getIdade());
        pet.setTutor(petAtualizado.getTutor());

        return petRepository.save(pet);

    }

    public void deletar(Long id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet não encontrado com ID: "+ id);
        }
        petRepository.deleteById(id);
    }
}
