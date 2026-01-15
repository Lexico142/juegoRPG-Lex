package tpv;

import java.util.Scanner;

public class Main {
    // Scanner global para leer datos del usuario
    public static Scanner src = new Scanner(System.in);

    // Array con los nombres de los productos disponibles
    public static String[] productos = {"Pan", "Huevos", "Aceite", "Harina"};

    // Array con los precios correspondientes a cada producto
    public static float[] precio = {1.15f, 2.54f, 4.99f, 0.99f };

    // Array que almacena los códigos de productos añadidos al carrito
    public static int[] carrito = new int[50];

    // Contador de productos en el carrito
    public static int cosas = 0;

    public static void main(String[] args) throws InterruptedException {
        // Bucle principal del programa
        while(true) {
            menu();
            escoger();
        }
    }

    // Muestra el menú principal
    static void menu(){
        System.out.println("\n===== SUPERMERCADO =====");
        System.out.println(" 1. Ver productos");
        System.out.println(" 2. Añadir producto al carrito");
        System.out.println(" 3. Ver carrito");
        System.out.println(" 4. Finalizar compra");
        System.out.println(" 5. Salir");
        System.out.print("Elige una opcion: ");
    }

    // Procesa la opción elegida por el usuario
    static void escoger() throws InterruptedException {
        try {
            int opcion = src.nextInt();
            src.nextLine();

            switch (opcion){
                case 1:
                    verProductos();
                    break;
                case 2:
                    anadirProducto();
                    break;
                case 3:
                    verCarrito();
                    break;
                case 4:
                    finalizarCompra();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    Thread.sleep(1000);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion incorrecta");
                    Thread.sleep(800);
            }
        } catch (Exception e) {
            System.out.println("Error: Entrada invalida. Por favor, ingresa un numero.");
            src.nextLine(); // Limpiar el buffer del scanner
        }
    }

    // Muestra todos los productos disponibles con su precio
    static void verProductos() throws InterruptedException {
        System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
        for (int i = 0; i < productos.length ; i++) {
            System.out.println((i + 1) + "- " + productos[i] + " (" + precio[i] + "€)");
            Thread.sleep(200);
        }
        Thread.sleep(500);
    }

    // Permite añadir productos al carrito
    static void anadirProducto() throws InterruptedException {
        verProductos();

        while(true) {
            // Verificar si el carrito está lleno
            if (cosas >= 50) {
                System.out.println("El carrito esta lleno.");
                Thread.sleep(1000);
                break;
            }

            System.out.print("\nIntroduce el numero del producto (-1 para volver): ");
            int seleccion = src.nextInt();

            // Salir del bucle si el usuario quiere volver
            if (seleccion == -1) {
                System.out.println("Volviendo al menu...");
                Thread.sleep(800);
                break;
            }

            // Validar que el producto existe
            if (seleccion > 0 && seleccion <= productos.length) {
                // Convertir el número del usuario (1-4) al índice del array (0-3)
                int pos = seleccion - 1;

                // Guardar el producto en el carrito
                carrito[cosas] = pos;

                // Incrementar el contador de productos
                cosas++;

                System.out.println("✓ Añadido: " + productos[pos]);
                Thread.sleep(600);
            } else {
                System.out.println("✗ Ese producto no existe.");
                Thread.sleep(800);
            }
        }
    }

    // Muestra el contenido actual del carrito
    static void verCarrito() throws InterruptedException {
        System.out.println("\n=== TU CARRITO ===");

        if (cosas == 0) {
            System.out.println("El carrito esta vacio.");
        } else {
            // Recorrer todos los productos en el carrito
            for (int i = 0; i < cosas; i++) {
                int codigoProducto = carrito[i];
                System.out.println("- " + productos[codigoProducto] + " : " + precio[codigoProducto] + "€");
                Thread.sleep(300);
            }
        }
        Thread.sleep(1500);
    }

    // Genera el ticket final y vacía el carrito
    static void finalizarCompra() throws InterruptedException {
        System.out.println("\n=== TICKET DE COMPRA ===");
        Thread.sleep(500);

        float total = 0;

        // Calcular el total sumando todos los productos
        for (int i = 0; i < cosas; i++) {
            int codigoProducto = carrito[i];
            System.out.println(productos[codigoProducto] + " -- " + precio[codigoProducto] + "€");
            total = total + precio[codigoProducto];
            Thread.sleep(400);
        }

        System.out.println("=====================");
        Thread.sleep(300);
        System.out.println("TOTAL A PAGAR: " + total + "€");
        System.out.println("=====================");
        Thread.sleep(1000);

        // Vaciar el carrito después de la compra
        cosas = 0;

        System.out.println("\n✓ Gracias por su visita.");
        Thread.sleep(1500);
    }
}