package com.example.crud.controller;

import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import jakarta.validation.Valid;
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
    public Usuario crear(@Valid @RequestBody Usuario usuario) {
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
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("usuario.no.encontrado"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("usuario.no.encontrado"));

        existente.setNombre(usuario.getNombre());
        existente.setEmail(usuario.getEmail());

        return repository.save(existente);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("usuario.no.encontrado");
        }

        repository.deleteById(id);
    }
}