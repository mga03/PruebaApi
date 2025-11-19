package pruebaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pruebaapi.model.Proyecto;
import pruebaapi.repository.ProyectoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @GetMapping // SELECT ALL
    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }

    @GetMapping("/{id}") // SELECT ONE
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id); 
        
        return proyecto.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @PostMapping // INSERT
    public Proyecto createProyecto(@RequestBody Proyecto proyecto) {
        return proyectoRepository.save(proyecto); 
    }

    @PutMapping("/{id}") // UPDATE
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoDetails) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);

        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setNombre(proyectoDetails.getNombre());
            proyecto.setDescripcion(proyectoDetails.getDescripcion());
            proyecto.setEstado(proyectoDetails.getEstado());
            proyecto.setFechaInicio(proyectoDetails.getFechaInicio());

            Proyecto updatedProyecto = proyectoRepository.save(proyecto); 
            return ResponseEntity.ok(updatedProyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        if (proyectoRepository.existsById(id)) {
            proyectoRepository.deleteById(id); 
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}