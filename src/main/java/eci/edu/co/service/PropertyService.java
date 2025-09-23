package eci.edu.co.service;

import eci.edu.co.model.Property;
import eci.edu.co.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private final PropertyRepository repo;
    public PropertyService(PropertyRepository repo){ this.repo = repo; }

    public Property save(Property p){ return repo.save(p); }
    public List<Property> findAll(){ return repo.findAll(); }
    public Optional<Property> findById(Long id){ return repo.findById(id); }
    public void delete(Long id){ repo.deleteById(id); }
}

