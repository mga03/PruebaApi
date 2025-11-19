package pruebaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // --- READ (GET) ---
    @GetMapping
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaRepository.findById(id);
        if (tarea.isPresent()) {
            return new ResponseEntity<>(tarea.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 1. --- CREATE (POST) ---
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Código de respuesta 201
    public Tarea createTarea(@RequestBody Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    // 2. --- UPDATE (PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTarea(@PathVariable Long id, @RequestBody Tarea tareaDetails) {
        
        Optional<Tarea> tareaExistente = tareaRepository.findById(id);
        
        if (tareaExistente.isPresent()) {
            Tarea tarea = tareaExistente.get();
            
            // Actualiza los campos que se reciben en la petición
            tarea.setTitulo(tareaDetails.getTitulo());
            tarea.setDescripcion(tareaDetails.getDescripcion());
            tarea.setFechaLimite(tareaDetails.getFechaLimite());
            tarea.setPrioridad(tareaDetails.getPrioridad());
            tarea.setEstado(tareaDetails.getEstado());
            // Nota: Aquí se actualizarían también claves foráneas como proyectoId, creadorId, etc., si las envías.
            tarea.setProyectoId(tareaDetails.getProyectoId());
            tarea.setCreadorId(tareaDetails.getCreadorId());
            tarea.setAsignadoAId(tareaDetails.getAsignadoAId());
            
            Tarea tareaActualizada = tareaRepository.save(tarea);
            return new ResponseEntity<>(tareaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // 3. --- DELETE (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTarea(@PathVariable Long id) {
        try {
            tareaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}