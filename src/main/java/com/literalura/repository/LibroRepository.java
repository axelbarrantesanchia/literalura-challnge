package com.literalura.repository;

import com.literalura.model.Autor;
import com.literalura.model.DatosLibro;
import com.literalura.model.Idiomas;
import com.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libros, Long> {
    List<Libros> findAll();

    Optional<Libros> findByTituloContainsIgnoreCase(String nombreLibro);

    @Query("SELECT l FROM Libros l WHERE LOWER(l.idioma) LIKE LOWER(CONCAT('%', :idioma, '%'))")
    List<Libros> findByIdiomaContainsIgnoreCase(@Param("idioma") String idioma);

    @Query("SELECT a FROM Libros l JOIN l.autores a WHERE a.anioNacimiento <= :anio AND (a.anioMuerte IS NULL OR a.anioMuerte > :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") int anio);

    @Query("SELECT a FROM Libros l JOIN l.autores a")
    List<Autor> findAllAutores();

}
