package com.example.maria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maria.entity.PersonalInformation;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
}
