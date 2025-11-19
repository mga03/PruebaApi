package pruebaapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarea") // Asegúrate de que el nombre de la tabla sea correcto: 'tarea'
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    // 1. Mapeo de la fecha límite
    @Column(name = "fecha_limite")
    private LocalDate fechaLimite; 

    private String prioridad;
    private String estado;

    // 2. Mapeo de claves foráneas y IDs
    @Column(name = "proyecto_id")
    private Long proyectoId;

    @Column(name = "creador_id")
    private Long creadorId;

    @Column(name = "asignado_a_id")
    private Long asignadoAId;

    // 3. Mapeo de la fecha de creación (con hora)
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion; 
    
    // Si tienes alguna relación de entidad (e.g., ManyToOne con Proyecto o Usuario), 
    // esa lógica iría aquí, pero para mapear los campos, esto es suficiente.
}