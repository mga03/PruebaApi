package pruebaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pruebaapi.model.Tarea;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    // Método personalizado para obtener tareas filtradas por proyecto
    List<Tarea> findByProyectoId(Long proyectoId);
}