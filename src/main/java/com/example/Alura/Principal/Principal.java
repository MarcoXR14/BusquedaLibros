package com.example.Alura.Principal;

import com.example.Alura.Service.ConsumoAPI;
import com.example.Alura.Service.ConvierteDatos;
import com.example.Alura.model.Datos;
import com.example.Alura.model.DatosLibros;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    Scanner scanner = new Scanner(System.in);
    
    public void muestraMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println("Datos: " + datos);

        //Top 10 libros más descargados
        System.out.println("Estos son los 10 libros más descargados segun Gutendex:");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda de libro por nombre
        System.out.println("Ingresa el nombre de algún libro: ");
        var nombreLibro = scanner.nextLine();

        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var resultadoBusqueda = conversor.obtenerDatos(json, Datos.class);
        System.out.println(resultadoBusqueda);

        Optional<DatosLibros> libroBuscado = resultadoBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado: " + libroBuscado.get());
        } else {
            System.out.println("Libro no disponible");
        }

        //Estadisticas de los libros
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        System.out.println("Cantidad media de descargas: " + est.getAverage() +
                "Cantidad máxima de descargas: " + est.getMax() +
                "Cantidad minima de descargas: " + est.getMin());
    }
}
