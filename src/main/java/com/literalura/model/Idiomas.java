package com.literalura.model;

import java.util.List;

public enum Idiomas {
    ENGLISH("en", "Ingles"),
    FRANCE("fr", "Francés"),
    ESPANOL("es", "Español");

    private final String idioma;
    private final String idiomasEspanol;

    Idiomas(String idioma, String idiomaEspanol) {
        this.idioma = idioma;
        this.idiomasEspanol = idiomaEspanol;
    }

    public static Idiomas fromStrings(List<String> texts) {
        for (String text : texts) {
            for (Idiomas idioma : Idiomas.values()) {
                if (idioma.getCodigo().equalsIgnoreCase(text)) {
                    return idioma;
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
