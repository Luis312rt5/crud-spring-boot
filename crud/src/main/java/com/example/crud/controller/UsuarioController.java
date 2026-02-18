package com.example.crud.controller;

import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }

    // READ ALL
    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existente = repository.findById(id).orElse(null);

        if (existente != null) {
            existente.setNombre(usuario.getNombre());
            existente.setEmail(usuario.getEmail());
            return repository.save(existente);
        }

        return null;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
