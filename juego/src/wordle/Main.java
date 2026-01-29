package wordle;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {
    // Scanner para leer lo que escribe el usuario
    public static Scanner sc = new Scanner(System.in);

    // Códigos de colores ANSI para la consola (Reset, Verde, Amarillo, Gris)
    public static final String RESET = "\u001B[0m";
    public static final String VERDE = "\u001B[42;30m";
    public static final String AMARILLO = "\u001B[43;30m";
    public static final String GRIS = "\u001B[47;30m";

    // Variables globales: intento actual, palabra a resolver y el tablero
    public static String guess = "";
    public static String solve = "";
    public static char[][] tabla;

    public static void main(String[] args) {
        // Obtenemos la palabra aleatoria desde la API
        solve = PalabraAleatoria();

        // Llenamos el tablero con guiones bajos
        iniciarTabla();
        boolean ganado = false;

        System.out.println("=== WORDLE ===");

        // Bucle principal: 6 intentos
        for (int intento = 0; intento < 6; intento++) {
            // Imprime el tablero con los colores de los intentos anteriores
            imprimirTabla(intento);

            // Pide al usuario que escriba
            pedirPalabra(intento);

            // Comprueba si ha acertado
            if (correcta()) {
                ganado = true;
                break; // Sale del bucle si gana
            }
        }

        // Imprime el resultado final
        imprimirTabla(6);
        finalizarJuego(ganado);
    }

    // Método para descargar una palabra aleatoria de una API
    public static String PalabraAleatoria() {
        String palabra = null;
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word?lang=es&length=5");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            String respuesta = br.readLine();
            br.close();

            palabra = respuesta
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\"", "");

        } catch (Exception e) {
            System.out.println("error obteniendo pal");
        }

        System.out.println(palabra);
        return palabra;
    }

    // Inicializa la matriz de 6 filas x 5 columnas con '_'
    static void iniciarTabla() {
        tabla = new char[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tabla[i][j] = '_';
            }
        }
    }

    // Lógica principal de pintado y colores
    static void imprimirTabla(int intentoActual) {
        System.out.println();
        for (int i = 0; i < 6; i++) { // Recorre cada fila (intento)

            int[] resultados = new int[5]; // 0: Gris, 1: Amarillo, 2: Verde

            // Si es una fila ya jugada, calculamos los colores
            if (i < intentoActual) {
                char[] letrasSecretas = solve.toCharArray();
                char[] intentoUsuario = tabla[i];

                // Contamos frecuencia de letras para manejar duplicados correctamente
                int[] frecuencias = new int[500];
                for (int k = 0; k < letrasSecretas.length; k++) {
                    char c = letrasSecretas[k];
                    frecuencias[c]++;
                }

                // Marcar los verdes
                for (int j = 0; j < 5; j++) {
                    char letra = intentoUsuario[j];
                    if (letra == letrasSecretas[j]) {
                        resultados[j] = 2; // Verde
                        frecuencias[letra]--; // Restamos del contador
                    }
                }

                // Marcar los amarillos
                for (int j = 0; j < 5; j++) {
                    if (resultados[j] != 2) { // Si no es verde ya...
                        char letra = intentoUsuario[j];
                        if (frecuencias[letra] > 0) {
                            resultados[j] = 1; // Amarillo
                            frecuencias[letra]--;
                        } else {
                            resultados[j] = 0; // Gris
                        }
                    }
                }
            }

            // Dibujar la fila con los colores calculados
            for (int j = 0; j < 5; j++) {
                char letra = tabla[i][j];

                if (i < intentoActual && letra != '_') {
                    switch (resultados[j]) {
                        case 2:
                            System.out.print(VERDE + " " + letra + " " + RESET);
                            break;
                        case 1:
                            System.out.print(AMARILLO + " " + letra + " " + RESET);
                            break;
                        default:
                            System.out.print(GRIS + " " + letra + " " + RESET);
                            break;
                    }
                } else {
                    // Si la fila no se ha jugado aún, se imprime normal
                    System.out.print(" " + letra + " ");
                }
                System.out.print(" "); // Espacio entre letras
            }
            System.out.println();
        }
        System.out.println();
    }

    // Pide la palabra y valida que tenga 5 letras
    static void pedirPalabra(int intento) {
        System.out.print("Intento " + (intento + 1) + "/6 > ");
        guess = sc.nextLine();

        while (guess.length() != 5) {
            System.out.print("Debe ser de 5 letras > ");
            guess = sc.nextLine();
        }

        // Guarda la palabra en la matriz del tablero
        for (int j = 0; j < 5; j++) {
            tabla[intento][j] = guess.charAt(j);
        }
    }

    // Comprueba si la palabra es exactamente igual a la solución
    static boolean correcta() {
        return guess.equals(solve);
    }

    // Mensaje de fin de juego
    static void finalizarJuego(boolean ganado) {
        if (ganado) {
            System.out.println("Has acertado.");
        } else {
            System.out.println("Has perdido... La palabra era: " + solve);
        }
    }
}