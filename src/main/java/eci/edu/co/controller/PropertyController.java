package eci.edu.co.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.util.List;
import eci.edu.co.service.PropertyService;
import eci.edu.co.model.Property;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*") 
public class PropertyController {
    private final PropertyService service;
    public PropertyController(PropertyService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<Property> create(@RequestBody Property p){
        return ResponseEntity.ok(service.save(p));
    }
    @GetMapping
    public List<Property> list(){ return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Property> get(@PathVariable Long id){
        return service.findById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> update(@PathVariable Long id, @RequestBody Property p){
        return service.findById(id).map(existing -> {
            existing.setAddress(p.getAddress());
            existing.setPrice(p.getPrice());
            existing.setSize(p.getSize());
            existing.setDescription(p.getDescription());
            return ResponseEntity.ok(service.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
