package com.literalura.model;

import jakarta.persistence.*;

import java.util.List;

//

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int anioNacimiento;
    private int anioMuerte;


    @ManyToOne
    private Libros libro;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.Nombre();
        this.anioNacimiento = datosAutor.anioDeNacimiento();
        this.anioMuerte = datosAutor.anioDeMuerte();
    }

    public Autor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public int getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(int anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public Libros getLibro() {
        return libro;
    }

    public void setLibro(Libros libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Autor:" +
                "\n  Nombre: " + nombre +
                "\n  Año de Nacimiento: " + anioNacimiento +
                "\n  Año de Muerte: " + anioMuerte +
                "\n";
    }

}