package com.example.maria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.entity.User;
import com.example.maria.repository.PersonalInformationRepository;

@Service
public class PersonalInformationService {

    @Autowired
    private PersonalInformationRepository repository;

    public void savePersonalInformation(PersonalInformation personalInformation) {
        repository.save(personalInformation);
    }
    public List<PersonalInformation> getAllPersonalInformation() {
        return repository.findAll();
    }
    public void deletePersonalInformation(Long id) {
        repository.deleteById(id); // Delete the record by ID
    }
    public PersonalInformation findByUser(User user) {
        return repository.findByUser(user);
    }
}
