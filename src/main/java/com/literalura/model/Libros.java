package com.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private Double descargas;
    @Enumerated(EnumType.STRING)
    private Idiomas idioma;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;

    public Libros(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        try {
            this.descargas = datosLibro.descargas();
        }catch (Exception e){
            this.descargas = 0.0;
        }
        this.autores = datosLibro.autores().stream()
                .map(datosAutor -> {
                    Autor autor = new Autor(datosAutor);
                    autor.setLibro(this);
                    return autor;
                }).collect(Collectors.toList());
        try {
            this.idioma = Idiomas.fromStrings(datosLibro.idiomas());
        }catch (IllegalArgumentException e) {
            this.idioma = null;
        }
    }

    public Libros() {}


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public Double getDescargas() {
        return descargas;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return  "\n**************************"+
                "\nLibro:" +
                "\nTitulo:" + titulo +
                "\nDescargas: " + descargas +
                "\nAutores:" + autores +
                "\nIdiomas: " + idioma +"\n"+
                "**************************";
    }


}