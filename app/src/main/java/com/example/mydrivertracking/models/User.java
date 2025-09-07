package com.example.mydrivertracking.models;

public class User {
    private String nombre;
    private String email;
    private String clave;

    public User(String nombre, String email, String clave) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getClave() { return clave; }
}
