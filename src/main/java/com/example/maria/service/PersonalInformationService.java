package com.example.maria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.repository.PersonalInformationRepository;

@Service
public class PersonalInformationService {

    @Autowired
    private PersonalInformationRepository repository;

    public void savePersonalInformation(PersonalInformation info) {
        repository.save(info);
    }
}
