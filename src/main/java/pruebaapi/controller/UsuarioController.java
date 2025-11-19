package pruebaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pruebaapi.model.Usuario;
import pruebaapi.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios") 
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- READ (GET) ---
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- CREATE (POST) ---
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Código de respuesta 201
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        // En una aplicación real, aquí encriptarías la contraseña antes de guardar.
        return usuarioRepository.save(usuario);
    }

    // --- UPDATE (PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            
            // Actualiza los campos (recuerda que tu modelo solo tiene nombre, email, password)
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setPassword(usuarioDetails.getPassword()); 
            
            // Aquí puedes añadir la lógica para actualizar otros campos si existen
            
            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // --- DELETE (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}