package eci.edu.co.repository;

import eci.edu.co.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> { }
