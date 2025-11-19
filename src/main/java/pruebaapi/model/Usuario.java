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

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    // 1. Corrección clave: Mapeamos el campo 'password' de Java
    //    a la columna 'password_hash' de MySQL, que es la que contiene el dato.
    @Column(name = "password_hash")
    private String password;
    
    // NOTA: Todos los getters/setters y constructores son generados
    //       automáticamente por @Data, @NoArgsConstructor y @AllArgsConstructor
    //       (Lombok). No necesitas escribirlos aquí.
}