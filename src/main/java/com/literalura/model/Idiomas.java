package com.literalura.model;

import java.util.List;

public enum Idiomas {
    ENGLISH("en", "Inglés"),
    FRANCE("fr", "Francés"),
    ESPANOL("es", "Español");

    private final String idioma; // Código del idioma
    private final String idiomasEspanol; // Nombre en español

    Idiomas(String idioma, String idiomaEspanol) {
        this.idioma = idioma;
        this.idiomasEspanol = idiomaEspanol;
    }

    public static Idiomas fromStrings(List<String> texts) {
        for (String text : texts) {
            for (Idiomas idioma : Idiomas.values()) {
                if (idioma.idiomasEspanol.equalsIgnoreCase(text)) { // Busca por nombre en español
                    return idioma; // Devuelve el objeto Idiomas en lugar del nombre
                }
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada en la lista: " + texts);
    }

    public String getCodigo() {
        return this.idioma;
    }

    public String getIdiomasEspanol() {
        return this.idiomasEspanol;
    }
}
