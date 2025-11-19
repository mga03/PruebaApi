package pruebaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pruebaapi.model.Tarea;
import pruebaapi.repository.TareaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping // SELECT ALL
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    @GetMapping("/{id}") // SELECT ONE
    public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaRepository.findById(id); 
        
        return tarea.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build()); 
    }
    
    @GetMapping("/proyecto/{proyectoId}") // SELECT BY PROYECTO ID
    public List<Tarea> getTareasByProyectoId(@PathVariable Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
    }

    @PostMapping // INSERT
    public Tarea createTarea(@RequestBody Tarea tarea) {
        return tareaRepository.save(tarea); 
    }

    @PutMapping("/{id}") // UPDATE
    public ResponseEntity<Tarea> updateTarea(@PathVariable Long id, @RequestBody Tarea tareaDetails) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(id);

        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();
            tarea.setTitulo(tareaDetails.getTitulo());
            tarea.setDescripcion(tareaDetails.getDescripcion());
            tarea.setEstado(tareaDetails.getEstado());
            tarea.setFechaLimite(tareaDetails.getFechaLimite());
            tarea.setProyectoId(tareaDetails.getProyectoId());

            Tarea updatedTarea = tareaRepository.save(tarea); 
            return ResponseEntity.ok(updatedTarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id); 
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}