package com.literalura.principal;

import com.literalura.model.*;
import com.literalura.repository.LibroRepository;
import com.literalura.service.ConsumoAPI;
import com.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private List<Libros> libros;
    private final String URL = "https://gutendex.com/books/?search=";
    private LibroRepository libroRepository;


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Buscar autor
                    3 - Listar libros registrados
                    4 - Listar autores registrados
                    5 - Listar autores vivos en determinado año
                    6 - Listar libros por idioma
                    
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarAutor();
                    break;
                case 3:
                    listarLibrosRegistrados();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    listarAutoresVivosEnDeterminadoAnio();
                    break;
                case 6:
                    listarLibrosPorIdioma();
                    break;

                default:
                    System.out.println("Digite por favor una ópcion válida.");


            }
        }
    }



    private List<DatosLibro> getDatosLibro() {
        System.out.println("Digite el titulo que desea buscar: ");
        var libro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL + libro.replace(" ", "%20"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        datosLibro = datos.results();
        return datosLibro;
    }

    private void buscarLibroPorTitulo() {
        datosLibro = getDatosLibro();
        libros = datosLibro.stream()
                .map(l -> new Libros(l))
                .limit(1)
                .collect(Collectors.toList());


        for (Libros libroNuevo : libros) {

            Optional<Libros> libroGuardado = libroRepository.findByTituloContainsIgnoreCase(libroNuevo.getTitulo());

            if (libroGuardado.isEmpty()) {

                libroRepository.save(libroNuevo);
                System.out.println("Libro guardado: " + libroNuevo.toString());
            } else {

                Libros libroExistente = libroGuardado.get();
                System.out.println(libroExistente.toString());


                for (Autor autorNuevo : libroNuevo.getAutores()) {
                    boolean autorExists = libroExistente.getAutores().stream()
                            .anyMatch(autor -> autor.getNombre().equals(autorNuevo.getNombre()));

                    if (!autorExists) {
                        libroExistente.getAutores().add(autorNuevo);
                        System.out.println("Autor agregado: " + autorNuevo.getNombre());
                    }
                }

                libroRepository.save(libroExistente);
            }
        }
    }



    private List<DatosLibro> getDatosAutores() {
        System.out.println("Ingrese el nombre del autor: ");
        var autor = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL + autor.replace(" ", "%20"));
        var datos = conversor.obtenerDatos(json, Datos.class);
        datosLibro = datos.results();
        return datosLibro;
    }


    private void buscarAutor(){
        datosLibro = getDatosAutores();
        autores = datosLibro.stream()
                .flatMap(d -> d.autores().stream()
                        .map(Autor::new))
                .limit(1)
                .collect(Collectors.toList());

        autores.sort(Comparator.comparing(Autor::getNombre));
        autores.forEach(System.out::println);

    }

    private void listarLibrosPorIdioma() {
        System.out.println("Escriba el idioma de la serie que desea buscar (Ejemplo: ESPANOL): ");
        var idioma = teclado.nextLine().trim();

        try {
            var categoria = Idiomas.fromStrings(Collections.singletonList(idioma));


            List<Libros> librosPorIdioma = libroRepository.findByIdiomaContainsIgnoreCase(categoria.getCodigo());

            System.out.println("Los libros del idioma: " + idioma);
            librosPorIdioma.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    private void listarAutoresVivosEnDeterminadoAnio() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese el año que desea consultar: ");
        int anio = teclado.nextInt();


        List<Autor> autoresVivos = libroRepository.findAutoresVivosEnAnio(anio);


        System.out.println("Autores vivos en el año " + anio + ":");
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en este año.");
        } else {
            autoresVivos.forEach(autor -> System.out.println(autor.getNombre()));
        }

    }

    private void listarLibrosRegistrados() {
        List<Libros> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }


    private void listarAutoresRegistrados() {
        List<Autor> autores = libroRepository.findAllAutores();
        autores.forEach(System.out::println);
    }




     public Principal(LibroRepository libroRepository) {
            this.libroRepository = libroRepository;
        }

    }