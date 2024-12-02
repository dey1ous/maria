package com.example.maria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.entity.User;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
    Optional<PersonalInformation> findByUserId(Long userId);
    PersonalInformation findByUser(User user);
    
}
