package com.example.mydrivertracking.models;

public class AuthResponse {
    private String message;  // opcional
    private String token;    // JWT
    private Usuario usuario; // opcional

    public String getMessage() { return message; }
    public String getToken() { return token; }
    public Usuario getUsuario() { return usuario; }

    public static class Usuario {
        private String id;
        private String email;
        private String nombre;

        public String getId() { return id; }
        public String getEmail() { return email; }
        public String getNombre() { return nombre; }
    }
}
