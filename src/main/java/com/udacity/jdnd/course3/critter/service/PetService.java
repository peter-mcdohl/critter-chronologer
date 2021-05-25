package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Pet petFromDTO(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO petIntoDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    public PetDTO save(PetDTO petDTO) {
        Pet newPet = petFromDTO(petDTO);

        Customer customer = customerRepository.findById(petDTO.getOwnerId()).orElseThrow(CustomerNotFoundException::new);
        newPet.setOwner(customer);
        newPet = petRepository.save(newPet);

        customer.addPet(newPet);

        return petIntoDTO(newPet);
    }

    public PetDTO findPetById(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(PetNotFoundException::new);
        return petIntoDTO(pet);
    }

    private List<PetDTO> petListIntoDTO(List<Pet> petList) {
        List<PetDTO> petDTOList = new ArrayList<>();
        for(int i = 0; i < petList.size(); i ++) {
            petDTOList.add(petIntoDTO(petList.get(i)));
        }
        return petDTOList;
    }

    public List<PetDTO> findPetByOwnerId(Long ownerId) {
        Customer customer = customerRepository.findById(ownerId).orElseThrow(CustomerNotFoundException::new);
        List<Pet> petList = petRepository.findByOwner(customer);

        return petListIntoDTO(petList);
    }

    public List<PetDTO> findAllPets() {
        List<Pet> petList = petRepository.findAll();
        return petListIntoDTO(petList);
    }
}
