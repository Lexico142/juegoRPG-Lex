package juego;

import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    // Scanner global
    public static Scanner src = new Scanner(System.in);

    // Variables de combate
    public static boolean resultadoCombate = true;  // Determina si el jugador ganó o perdió el combate
    public static int danoCalculado = 0;  // Daño infligido en el turno actual
    public static double probCriticaCalculada = 0;  // Probabilidad actual de golpe crítico (en porcentaje)

    // Estadísticas del jugador - Vida
    public static int vidaMax = 0;  // Vida máxima del personaje
    public static int vida = 0;  // Vida actual del personaje
    public static int cambioVida = 0;  // Variable auxiliar para mostrar cambios en la vida

    // Estadísticas del jugador - Stamina (energía para habilidades mágicas)
    public static int staminaMax = 0;  // Stamina máxima del personaje
    public static int stamina = 0;  // Stamina actual del personaje

    // Estadísticas del jugador - Daño
    public static float multiplicadorDano = 0;  // Multiplicador de daño (bonus porcentual)
    public static int danoFisico = 0;  // Daño base de ataques físicos
    public static int danoMagico = 0;  // Daño base de ataques mágicos

    // Economía del juego
    public static int oro = 0;  // Oro actual del jugador
    public static int cambioOro = 0;  // Variable auxiliar para mostrar cambios en el oro

    // Datos del personaje
    public static String nombre = "";  // Nombre elegido por el jugador
    public static String clase = "";  // Clase elegida por el jugador

    // Sistema de inventario
    public static String[] inventario = new String[10];  // Array de objetos (máximo 10)
    public static int itemsEnInventario = 0;  // Contador de objetos actuales

    // Datos del juego
    public static String[] mobs = {"goblin", "slime", "esqueleto", "zombie", "guardian"};  // Tipos de enemigos
    public static String[] recompensas = {"Poción de vida", "Poción Mágica", "Poción de daño", "Poción de daño extremo", "Amuleto Crítico"};  // Objetos obtenibles
    public static ArrayList<String> interaccion = new ArrayList<String>();  // Lista de posibles resultados de ataque (determina probabilidades)

    // Estadísticas de progreso
    public static int numerosCombates = 0;  // Contador de combates ganados (aumenta dificultad)
    public static int amuletoCriticoComprados = 0;  // Contador de amuletos críticos usados

    // Método principal del juego
    public static void main(String[] args) throws InterruptedException {

        // Inicialización del sistema de interacciones (probabilidades de ataque)
        // 3 normales, 1 crítico, 1 esquive, 1 débil = 50% normal, 16.6% cada otro tipo
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("crítico");
        interaccion.add("esquive");
        interaccion.add("debil");

        boolean bucleInicial = true;
        boolean salir = false;

        // Pantalla de bienvenida
        System.out.println("==================================");
        System.out.println("==== Bienvenido/a a Dunventure ===");
        System.out.println("==================================\n");

        // Bucle principal del juego
        while (!salir) {
            // Menú inicial
            while (bucleInicial) {
                System.out.println("Introduce el número correspondiente a tu opción");
                System.out.println("1. Empezar");
                System.out.println("2. Tutorial");
                System.out.println("3. Salir\n");

                int opcion = src.nextInt();

                switch (opcion) {
                    case 1:
                        bucleInicial = false;
                        break;
                    case 2:
                        tutorial();
                        break;
                    case 3:
                        System.out.println("Saliendo del programa...");
                        Thread.sleep(500);
                        bucleInicial = false;
                        salir = true;
                        break;
                    default:
                        System.out.println("Elige un número válido.");
                        break;
                }
                Thread.sleep(500);
            }

            if (!salir) {

                // Creación del personaje
                seleccionClase();

                boolean seguro = false;
                while (!seguro) {
                    System.out.println("\n¿Cómo llamarás a tu Personaje?");
                    src.nextLine();
                    nombre = src.nextLine();

                    System.out.println("Tu personaje se llama " + nombre + " y es un " + clase + "." + " Estás seguro? (true/false)");
                    seguro = src.nextBoolean();
                }

                System.out.println("\nQue empiece la aventura!");
                Thread.sleep(2000);

                // Bucle de combates (dura mientras el jugador esté vivo)
                boolean vivo = true;
                while (vivo) {

                    inicioCombate();
                    vivo = resultadoCombate;

                    // Game Over
                    if (!vivo) {
                        System.out.println("\n=================================");
                        System.out.println("    GAME OVER");
                        System.out.println("=================================");
                        System.out.println("¿Quieres crear otro personaje? (true/false)");
                        boolean continuar = src.nextBoolean();

                        if (continuar) {
                            bucleInicial = false;
                            reiniciarJuego();
                            vivo = false;
                        } else {
                            salir = true;
                            vivo = false;
                        }
                    } else {
                        // 50% de probabilidad de encontrar una taberna después de ganar un combate
                        if (Math.random() < 0.5) {
                            taberna();
                        }
                    }
                }
            }
        }

        System.out.println("\n¡Gracias por jugar a Dunventure!");
    }

    // Muestra las instrucciones básicas del juego
    public static void tutorial() {
        System.out.println("\n================");
        System.out.println("=== TUTORIAL ===");
        System.out.println("================");
        System.out.println("En Dunventure explorarás mazmorras y lucharás contra enemigos.");
        System.out.println("- Elige tu clase para determinar tus estadísticas iniciales.");
        System.out.println("- En combate puedes Atacar, usar Ataque Mágico, usar Objetos o Rendirte.");
        System.out.println("- Gana combates para obtener oro y objetos.");
        System.out.println("- Visita la taberna entre combates para comprar mejoras.");
        System.out.println("- Si tu vida llega a 0, pierdes.");
        System.out.println("================\n");
    }

    // Permite al jugador elegir su clase
    // Cada clase tiene estadísticas únicas que determinan el estilo de juego
    public static void seleccionClase() {
        System.out.println("\nElige tu clase (0-5):");
        System.out.println("1. Humano");
        System.out.println("2. Elfo");
        System.out.println("3. Demonio");
        System.out.println("4. Dragón");
        System.out.println("5. Enano");
        System.out.println("0. Ver estadísticas");

        int opcion = src.nextInt();

        switch (opcion) {
            case 1:  // Humano: Clase equilibrada, ideal para principiantes
                clase = "Humano";
                vidaMax = 100;
                vida = 100;
                staminaMax = 100;
                stamina = 100;
                danoFisico = 10;
                danoMagico = 10;
                multiplicadorDano = 1.0f;
                oro = 50;
                break;
            case 2:  // Elfo: Especializado en magia, baja vida pero alta stamina
                clase = "Elfo";
                vidaMax = 80;
                vida = 80;
                staminaMax = 150;
                stamina = 150;
                danoFisico = 5;
                danoMagico = 20;
                multiplicadorDano = 1.0f;
                oro = 0;
                break;
            case 3:  // Demonio: Berserker con bonus de daño del 20%
                clase = "Demonio";
                vidaMax = 90;
                vida = 90;
                staminaMax = 100;
                stamina = 100;
                danoFisico = 15;
                danoMagico = 15;
                multiplicadorDano = 1.2f;
                oro = 0;
                break;
            case 4:  // Dragón: Tanque con mucha vida y daño físico, pero poca stamina
                clase = "Dragón";
                vidaMax = 150;
                vida = 150;
                staminaMax = 60;
                stamina = 60;
                danoFisico = 22;
                danoMagico = 0;
                multiplicadorDano = 1.0f;
                oro = 100;
                break;
            case 5:  // Enano: Guerrero resistente con bonus de daño del 10%
                clase = "Enano";
                vidaMax = 120;
                vida = 120;
                staminaMax = 90;
                stamina = 90;
                danoFisico = 18;
                danoMagico = 0;
                multiplicadorDano = 1.1f;
                oro = 75;
                break;
            case 0:  // Muestra comparativa de estadísticas
                System.out.println("\n--- ESTADÍSTICAS INICIALES ---");
                System.out.println("1. HUMANO:  Equilibrado. [Vida: 100 | Stamina: 100 | Oro: 50]");
                System.out.println("2. ELFO:    Mago Ágil.   [Vida: 80  | Stamina: 150 | Daño Mágico Alto]");
                System.out.println("3. DEMONIO: Berserker.   [Vida: 90  | Daño x1.2 (20% extra)]");
                System.out.println("4. DRAGÓN:  Tanque.      [Vida: 150 | Stamina: 60  | Daño Físico Alto | Oro: 100]");
                System.out.println("5. ENANO:   Guerrero.    [Vida: 120 | Stamina: 90  | Oro: 75]");
                System.out.println("------------------------------\n");
                seleccionClase();
                break;
            default:
                System.out.println("Opción no válida.");
                seleccionClase();
                break;
        }
    }

    // Inicia y gestiona un combate completo contra un enemigo aleatorio
    // Los enemigos escalan su dificultad basándose en el número de combates ganados
    public static void inicioCombate() throws InterruptedException {
        System.out.println("\n========================================");
        System.out.println("Estás explorando las mazmorras...");
        Thread.sleep(1500);

        // Selección aleatoria de enemigo
        int mobNum = (int) (Math.random() * mobs.length);
        String mob = mobs[mobNum];

        System.out.println("¡Un " + mob.toUpperCase() + " se cruza ante ti!");
        Thread.sleep(1000);

        // Inicialización de estadísticas del enemigo
        int enemigoVida = 0;
        int enemigoDanoBase = 0;
        int enemigoOro = 0;

        // Configuración de enemigo según su tipo
        // La vida escala +5 por cada combate ganado para aumentar dificultad
        switch (mob) {
            case "goblin":  // Enemigo básico equilibrado
                enemigoVida = 20 + (numerosCombates * 5);
                enemigoDanoBase = 8;
                enemigoOro = 15;
                break;
            case "slime":  // Enemigo débil pero da poco oro
                enemigoVida = 10 + (numerosCombates * 5);
                enemigoDanoBase = 3;
                enemigoOro = 5;
                break;
            case "esqueleto":  // Enemigo medio-fuerte
                enemigoVida = 40 + (numerosCombates * 5);
                enemigoDanoBase = 12;
                enemigoOro = 25;
                break;
            case "zombie":  // Tanque con bajo daño
                enemigoVida = 70 + (numerosCombates * 5);
                enemigoDanoBase = 6;
                enemigoOro = 20;
                break;
            case "guardian":  // Boss/enemigo más difícil
                enemigoVida = 120 + (numerosCombates * 5);
                enemigoDanoBase = 20;
                enemigoOro = 100;
                break;
        }

        System.out.println("----------------------------------------");
        System.out.println("ENEMIGO: " + mob.toUpperCase());
        System.out.println("Vida: " + enemigoVida + " | Daño base: " + enemigoDanoBase);
        System.out.println("----------------------------------------");
        Thread.sleep(1000);

        boolean enCombate = true;
        resultadoCombate = true;

        // Bucle principal del combate por turnos
        while (enCombate) {

            mostrarEstadoCombate(mob, enemigoVida);

            // Turno del jugador
            System.out.println("\n¿Qué harás?");
            System.out.println("[1] Atacar");
            System.out.println("[2] Ataque Mágico (25 PM)");
            System.out.println("[3] Objetos");
            System.out.println("[4] Rendirse");

            int opcion = src.nextInt();
            boolean turnoEnemigo = true;

            switch (opcion) {
                case 1:  // Ataque físico básico
                    atacar();
                    enemigoVida -= danoCalculado;
                    break;
                case 2:  // Ataque mágico (cuesta 25 de stamina)
                    if (stamina >= 25) {
                        ataqueMagico();
                        enemigoVida -= danoCalculado;
                    } else {
                        System.out.println("¡No tienes suficiente stamina!");
                        turnoEnemigo = false;
                    }
                    break;
                case 3:  // Usar objeto del inventario (no consume turno)
                    usarObjeto();
                    turnoEnemigo = false;
                    break;
                case 4:  // Huir del combate (penalización: -5 oro)
                    System.out.println("Te rindes y huyes del combate...");
                    oro -= 5;
                    if (oro < 0) oro = 0;
                    System.out.println("Pierdes 5 de oro. Oro actual: " + oro);
                    Thread.sleep(1500);

                    if (oro <= 0) {
                        System.out.println("Te has quedado sin oro y sin esperanza...");
                        resultadoCombate = false;
                    } else {
                        resultadoCombate = true;
                    }
                    enCombate = false;
                    turnoEnemigo = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    turnoEnemigo = false;
                    break;
            }

            // Victoria del jugador
            if (enCombate && enemigoVida <= 0) {
                System.out.println("\n¡Has derrotado al " + mob + "!");
                Thread.sleep(1000);

                // Recompensas por victoria
                cambioOro = (int) (Math.random() * 5) + 1 + enemigoOro;
                oro += cambioOro;
                System.out.println("Has ganado " + cambioOro + " de oro. Total: " + oro);

                String objetoGanado = recompensas[(int) (Math.random() * recompensas.length)];
                agregarAlInventario(objetoGanado);
                System.out.println("¡Has obtenido: " + objetoGanado + "!");

                // Recuperación de vida aleatoria entre 25 y (vidaMax-50)
                cambioVida = (int) (Math.random() * (vidaMax - 50 - 25 + 1)) + 25;
                vida += cambioVida;
                if (vida > vidaMax) vida = vidaMax;
                System.out.println("Recuperas " + cambioVida + " de vida.");

                numerosCombates++;
                Thread.sleep(2000);

                resultadoCombate = true;
                enCombate = false;
                turnoEnemigo = false;
            }

            // Turno del enemigo (si corresponde)
            if (enCombate && turnoEnemigo) {
                System.out.println("\n--- Turno del enemigo ---");
                Thread.sleep(1000);

                // Cálculo de daño enemigo aleatorio
                int danoEnemigo = (int) (Math.random() * (20 - enemigoDanoBase + 1)) + enemigoDanoBase;
                String interaccionEnemigo = interaccion.get((int) (Math.random() * interaccion.size()));

                // Tipos de ataque enemigo
                switch (interaccionEnemigo) {
                    case "normal":
                        vida -= danoEnemigo;
                        System.out.println("El " + mob + " te ataca y hace " + danoEnemigo + " de daño.");
                        break;
                    case "esquive":
                        System.out.println("¡El " + mob + " falla su ataque!");
                        break;
                    case "crítico":
                        int danoCritico = danoEnemigo * 2;
                        vida -= danoCritico;
                        System.out.println("¡CRÍTICO! El " + mob + " te hace " + danoCritico + " de daño.");
                        break;
                    case "debil":
                        int danoDebil = (int) (danoEnemigo * 0.5);
                        vida -= danoDebil;
                        System.out.println("Ataque débil. El " + mob + " te hace " + danoDebil + " de daño.");
                        break;
                }

                // Regeneración de stamina cada turno (+10)
                stamina += 10;
                if (stamina > staminaMax) stamina = staminaMax;

                Thread.sleep(1500);

                // Derrota del jugador
                if (vida <= 0) {
                    System.out.println("\n¡Has sido derrotado!");
                    resultadoCombate = false;
                    enCombate = false;
                }
            }
        }
    }

    // Ejecuta un ataque físico básico
    // El resultado puede ser: normal, crítico, débil o esquive (basado en probabilidades)
    public static void atacar() throws InterruptedException {
        String tipoInteraccion = interaccion.get((int) (Math.random() * interaccion.size()));
        int danoBase = (int) (danoFisico * multiplicadorDano);
        danoCalculado = 0;

        System.out.println("\n¡Atacas con tu arma!");
        Thread.sleep(1000);

        switch (tipoInteraccion) {
            case "normal":
                danoCalculado = danoBase;
                System.out.println("Golpe normal: " + danoCalculado + " de daño.");
                break;
            case "esquive":
                System.out.println("¡El enemigo esquiva tu ataque!");
                danoCalculado = 0;
                break;
            case "crítico":  // 150% de daño
                danoCalculado = (int) (danoBase * 1.5);
                System.out.println("¡CRÍTICO! Haces " + danoCalculado + " de daño.");
                break;
            case "debil":  // 50% de daño
                danoCalculado = (int) (danoBase * 0.5);
                System.out.println("Golpe débil: " + danoCalculado + " de daño.");
                break;
        }
    }

    // Ejecuta un ataque mágico
    // Siempre hace daño base de danoMagico (sin variaciones aleatorias)
    // Cuesta 25 de stamina
    public static void ataqueMagico() throws InterruptedException {
        stamina -= 25;
        danoCalculado = (int) (danoMagico * multiplicadorDano);

        System.out.println("\n¡Lanzas un hechizo devastador!");
        Thread.sleep(1000);
        System.out.println("Haces " + danoCalculado + " de daño mágico.");
        System.out.println("Stamina restante: " + stamina);
    }


    // Permite al jugador usar un objeto de su inventario
    // Los objetos tienen efectos variados: curar, recuperar stamina, mejorar estadísticas
    public static void usarObjeto() throws InterruptedException {
        if (itemsEnInventario == 0) {
            System.out.println("No tienes objetos en tu inventario.");
            Thread.sleep(1000);
        } else {
            System.out.println("\n--- INVENTARIO ---");
            for (int i = 0; i < itemsEnInventario; i++) {
                System.out.println("[" + (i + 1) + "] " + inventario[i]);
            }
            System.out.println("[0] Volver");

            int opcion = src.nextInt();

            if (opcion != 0 && opcion <= itemsEnInventario) {
                String objeto = inventario[opcion - 1];
                calcularProbabilidadCritico();

                // Efectos de cada tipo de objeto
                switch (objeto) {
                    case "Poción de vida":  // Cura 25 HP
                        vida += 25;
                        if (vida > vidaMax) vida = vidaMax;
                        System.out.println("Usas una Poción de vida. Vida +25. Vida actual: " + vida);
                        break;
                    case "Poción Mágica":  // Recupera 25 stamina
                        stamina += 25;
                        if (stamina > staminaMax) stamina = staminaMax;
                        System.out.println("Usas una Poción Mágica. Stamina +25. Stamina actual: " + stamina);
                        break;
                    case "Poción de daño":  // Aumenta permanentemente daño físico
                        danoFisico += 5;
                        System.out.println("Usas una Poción de daño. Daño físico permanentemente +5.");
                        break;
                    case "Poción de daño extremo":  // Aumenta permanentemente multiplicador
                        multiplicadorDano += 0.05f;
                        System.out.println("Usas una Poción de daño extremo. Multiplicador +0.05.");
                        break;
                    case "Amuleto Crítico":  // Aumenta probabilidad de crítico permanentemente
                        interaccion.add("crítico");
                        amuletoCriticoComprados++;
                        calcularProbabilidadCritico();
                        System.out.println("¡Usas el Amuleto Crítico!");
                        System.out.println("Se añade 1 'crítico' más al ArrayList de interacciones.");
                        System.out.println("Probabilidad de crítico aumentada permanentemente!");
                        System.out.println("Amuletos usados: " + amuletoCriticoComprados);
                        break;
                }

                // Eliminar objeto usado del inventario
                for (int i = opcion - 1; i < itemsEnInventario - 1; i++) {
                    inventario[i] = inventario[i + 1];
                }
                inventario[itemsEnInventario - 1] = null;
                itemsEnInventario--;

                Thread.sleep(1500);
            }
        }
    }

    // Tienda donde el jugador puede comprar objetos aleatorios
    // Aparece aleatoriamente (50% probabilidad) después de ganar un combate
    public static void taberna() throws InterruptedException {
        System.out.println("\n========================================");
        System.out.println("Tras una larga batalla, ves");
        System.out.println("a lo lejos una taberna...");
        System.out.println("========================================");
        Thread.sleep(1500);

        System.out.println("¿Entrar? (SI/NO)");
        src.nextLine();
        String respuesta = src.nextLine();

        if (!respuesta.equalsIgnoreCase("SI")) {
            System.out.println("Decides continuar tu camino...");
            Thread.sleep(1000);
        } else {
            calcularProbabilidadCritico();
            System.out.println("\nEntras en la taberna. El posadero te ofrece:");
            System.out.println("--- TIENDA ---");
            System.out.println("Tu oro: " + oro);

            // Generar 3 objetos aleatorios con precios aleatorios
            String[] objetosTienda = new String[3];
            int[] preciosTienda = new int[3];

            for (int i = 0; i < 3; i++) {
                objetosTienda[i] = recompensas[(int) (Math.random() * recompensas.length)];

                // Amuletos Críticos son más caros (40-60 oro)
                if (objetosTienda[i].equals("Amuleto Crítico")) {
                    preciosTienda[i] = (int) (Math.random() * 20) + 40;
                } else {
                    preciosTienda[i] = (int) (Math.random() * 30) + 10;
                }

                System.out.print("[" + (i + 1) + "] " + objetosTienda[i] + " - " + preciosTienda[i] + " oro");

                System.out.println();
            }
            System.out.println("[0] No comprar nada");

            int opcion = src.nextInt();

            if (opcion >= 1 && opcion <= 3) {
                int index = opcion - 1;
                if (oro >= preciosTienda[index]) {
                    oro -= preciosTienda[index];
                    agregarAlInventario(objetosTienda[index]);
                    System.out.println("Has comprado " + objetosTienda[index] + ".");
                    System.out.println("Oro restante: " + oro);
                } else {
                    System.out.println("¡No tienes suficiente oro!");
                }
            } else {
                System.out.println("Sales de la taberna sin comprar nada.");
            }

            Thread.sleep(2000);
        }
    }

    //Añade un objeto al inventario si hay espacio disponible
    public static void agregarAlInventario(String objeto) {
        if (itemsEnInventario < 10) {
            inventario[itemsEnInventario] = objeto;
            itemsEnInventario++;
        } else {
            System.out.println("Tu inventario está lleno.");
        }
    }

    // Muestra el estado actual del combate: info del enemigo y estadísticas del jugador
    public static void mostrarEstadoCombate(String enemigo, int vidaEnemigo) {
        calcularProbabilidadCritico();
        System.out.println("\n========================================");
        System.out.println("ENEMIGO: " + enemigo.toUpperCase() + " | Vida: " + vidaEnemigo);
        System.out.println("----------------------------------------");
        System.out.println(nombre + " (" + clase + ")");
        System.out.println("Vida: " + vida + "/" + vidaMax + " | Stamina: " + stamina + "/" + staminaMax);
        System.out.println("Oro: " + oro + " | Objetos: " + itemsEnInventario + "/10");
        System.out.println("========================================");
    }

    // Calcula la probabilidad actual de golpe crítico
    // Se basa en la cantidad de elementos "crítico" en el ArrayList de interacciones
    public static void calcularProbabilidadCritico() {
        int totalInteracciones = interaccion.size();
        int criticos = 0;

        for (String inter : interaccion) {
            if (inter.equals("crítico")) {
                criticos++;
            }
        }

        probCriticaCalculada = ((double) criticos / totalInteracciones) * 100;
    }

    // Reinicia todas las variables del juego a sus valores iniciales
    // Se usa cuando el jugador quiere crear un nuevo personaje después de perder
    public static void reiniciarJuego() {
        vida = 0;
        stamina = 0;
        danoFisico = 0;
        danoMagico = 0;
        multiplicadorDano = 0;
        oro = 0;
        itemsEnInventario = 0;
        numerosCombates = 0;
        amuletoCriticoComprados = 0;

        // Limpiar inventario
        for (int i = 0; i < inventario.length; i++) {
            inventario[i] = null;
        }

        // Resetear probabilidades de interacción
        interaccion.clear();
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("crítico");
        interaccion.add("esquive");
        interaccion.add("debil");
    }
}