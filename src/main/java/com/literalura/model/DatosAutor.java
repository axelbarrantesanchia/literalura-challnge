package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String Nombre,
        @JsonAlias("birth_year") int anioDeNacimiento,
        @JsonAlias("death_year") int anioDeMuerte
) {
}
