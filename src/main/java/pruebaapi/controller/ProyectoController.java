package pruebaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pruebaapi.model.Proyecto;
import pruebaapi.repository.ProyectoRepository;

import java.util.List;
import java.util.Optional;

// Indica que esta clase es un controlador REST y define la ruta base
@RestController
@RequestMapping("/api/proyectos") 
public class ProyectoController {

    // Inyecta el repositorio para acceder a la base de datos
    @Autowired
    private ProyectoRepository proyectoRepository;

    // 1. SELECT (READ) - Obtener todos los proyectos
    // Método: GET
    // URL: /api/proyectos
    @GetMapping
    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }
    
    // Método para obtener un proyecto por ID
    // URL: /api/proyectos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        if (proyecto.isPresent()) {
            return new ResponseEntity<>(proyecto.get(), HttpStatus.OK); // Código 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Código 404
        }
    }

    // 2. INSERT (CREATE) - Crear un nuevo proyecto
    // Método: POST
    // URL: /api/proyectos
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Devuelve un código de estado 201 (Created)
    public Proyecto createProyecto(@RequestBody Proyecto proyecto) {
        // El método .save() de JPA inserta la entidad si el ID es nulo
        return proyectoRepository.save(proyecto);
    }

    // 3. UPDATE (UPDATE) - Actualizar un proyecto existente
    // Método: PUT
    // URL: /api/proyectos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoDetails) {
        
        // Primero, verifica si el proyecto existe
        Optional<Proyecto> proyectoExistente = proyectoRepository.findById(id);
        
        if (proyectoExistente.isPresent()) {
            Proyecto proyecto = proyectoExistente.get();
            
            // Actualiza los campos con los datos recibidos en el cuerpo (body) de la petición
            proyecto.setNombre(proyectoDetails.getNombre());
            proyecto.setDescripcion(proyectoDetails.getDescripcion());
            proyecto.setEstado(proyectoDetails.getEstado());
            proyecto.setPrioridad(proyectoDetails.getPrioridad());
            // Nota: Aquí debes actualizar todos los campos necesarios que fueron mapeados con @Column
            
            // Guarda los cambios. Como el objeto tiene un ID, JPA realiza un UPDATE.
            Proyecto proyectoActualizado = proyectoRepository.save(proyecto);
            return new ResponseEntity<>(proyectoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // 4. DELETE (DELETE) - Eliminar un proyecto por ID
    // Método: DELETE
    // URL: /api/proyectos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProyecto(@PathVariable Long id) {
        try {
            // Elimina la entidad por su ID
            proyectoRepository.deleteById(id);
            // Devuelve un código 204 (No Content) para indicar éxito sin cuerpo de respuesta
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } catch (Exception e) {
            // Manejo básico de errores si el ID no existe o hay problemas en DB
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}