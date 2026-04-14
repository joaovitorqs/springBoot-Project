package com.springProject.project.controller;

import com.springProject.project.model.Usuario;
import com.springProject.project.security.JwtUtil;
import com.springProject.project.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request){
        Usuario usuario = usuarioService.registarUsuario(request.get("userName"), "password");
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> login (@RequestBody Map<String, String> request){
        Optional<Usuario> usuario = usuarioService.buscarPorUserName(request.get("userName"));
        if (usuario.isPresent() && usuario.get().getPassword().equals(request.get("password"))){
            String token = JwtUtil.generateToken(usuario.get().getUserName());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
