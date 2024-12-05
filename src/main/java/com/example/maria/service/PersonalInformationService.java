package com.example.maria.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.entity.User;
import com.example.maria.repository.PersonalInformationRepository;

@Service
public class PersonalInformationService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalInformationService.class);

    @Autowired
    private PersonalInformationRepository repository;

    // Save personal information
    public void savePersonalInformation(PersonalInformation personalInformation) {
        try {
            repository.save(personalInformation);
        } catch (Exception e) {
            logger.error("Error saving personal information", e);
            throw new RuntimeException("Error saving personal information", e);
        }
    }

    public List<PersonalInformation> getAllPersonalInformation() {
        return repository.findAll();
    }

    public boolean deleteUserById(Long id) {
        Optional<PersonalInformation> user = repository.findById(id);
        if (user.isPresent()) {
            repository.delete(user.get());  // Deletes the user based on the ID
            return true;
        }
        return false;  // User not found
    }

    // Find personal information by user
    public PersonalInformation findByUser(User user) {
        try {
            return repository.findByUser(user);
        } catch (Exception e) {
            logger.error("Error finding personal information for user: " + user, e);
            throw new RuntimeException("Error finding personal information for user", e);
        }
    }
}
