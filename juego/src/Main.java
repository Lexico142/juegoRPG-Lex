import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static Scanner src = new Scanner(System.in);

    public static boolean resultadoCombate = true;
    public static int danoCalculado = 0;
    public static double probCriticaCalculada = 0;

    public static int vidaMax = 0;
    public static int vida = 0;
    public static int cambioVida = 0;

    public static int staminaMax = 0;
    public static int stamina = 0;

    public static float multiplicadorDano = 0;

    public static int danoFisico = 0;

    public static int danoMagico = 0;

    public static int oro = 0;
    public static int cambioOro = 0;

    public static String nombre = "";
    public static String clase = "";

    public static String[] inventario = new String[10];
    public static int itemsEnInventario = 0;

    public static String[] mobs = {"goblin", "slime", "esqueleto", "zombie", "guardian"};
    public static String[] recompensas = {"Poción de vida", "Poción Mágica", "Poción de daño", "Poción de daño extremo", "Amuleto Crítico"};
    public static ArrayList<String> interaccion = new ArrayList<String>();

    public static int numerosCombates = 0;
    public static int amuletoCriticoComprados = 0;

    public static void main(String[] args) throws InterruptedException {

        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("crítico");
        interaccion.add("esquive");
        interaccion.add("debil");

        boolean bucleInicial = true;
        boolean salir = false;

        System.out.println("==================================");
        System.out.println("==== Bienvenido/a a Dunventure ===");
        System.out.println("==================================\n");

        while (!salir) {
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

                seleccionClase();

                boolean seguro = false;
                while (!seguro) {
                    System.out.println("\n¿Cómo llamarás a tu Personaje?");
                    src.nextLine();
                    nombre = src.nextLine();

                    System.out.println("Tu personaje se llama " + nombre + " y es un " + clase + ".");
                    System.out.println("¿Estás seguro? (true/false)");
                    seguro = src.nextBoolean();
                }

                System.out.println("\nQue empiece la aventura!");
                Thread.sleep(2000);

                boolean vivo = true;
                while (vivo) {

                    inicioCombate();
                    vivo = resultadoCombate;

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
                        int probTaberna = (int) (Math.random() * 100) + 1;
                        if (probTaberna >= 50) taberna();

                    }
                }
            }
        }

        System.out.println("\n¡Gracias por jugar a Dunventure!");
        src.close();
    }

    public static void tutorial() {
        System.out.println("\n================");
        System.out.println("=== TUTORIAL ===");
        System.out.println("================");
        System.out.println("En Dunventure explorarás mazmorras y lucharás contra enemigos.");
        System.out.println("- Elige tu clase para determinar tus estadísticas iniciales.");
        System.out.println("- En combate puedes Atacar, usar Ataque Mágico, usar Objetos o Rendirte.");
        System.out.println("- Gana combates para obtener oro y objetos.");
        System.out.println("- Visita la taberna entre combates para comprar mejoras.");
        System.out.println("- Consigue Amuletos Críticos para aumentar tu probabilidad de crítico");
        System.out.println("- Si tu vida llega a 0, pierdes.");
        System.out.println("================\n");
    }

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
            case 1:
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
            case 2:
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
            case 3:
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
            case 4:
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
            case 5:
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
            case 0:
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

    public static void inicioCombate() throws InterruptedException {
        System.out.println("\n========================================");
        System.out.println("Estás explorando las mazmorras...");
        Thread.sleep(1500);

        int mobNum = (int) (Math.random() * mobs.length);
        String mob = mobs[mobNum];

        System.out.println("¡Un " + mob.toUpperCase() + " se cruza ante ti!");
        Thread.sleep(1000);

        int enemigoVida = 0;
        int enemigoDanoBase = 0;
        int enemigoOro = 0;

        switch (mob) {
            case "goblin":
                enemigoVida = 40 + (numerosCombates * 5);
                enemigoDanoBase = 8;
                enemigoOro = 15;
                break;
            case "slime":
                enemigoVida = 20 + (numerosCombates * 5);
                enemigoDanoBase = 3;
                enemigoOro = 5;
                break;
            case "esqueleto":
                enemigoVida = 60 + (numerosCombates * 5);
                enemigoDanoBase = 12;
                enemigoOro = 25;
                break;
            case "zombie":
                enemigoVida = 90 + (numerosCombates * 5);
                enemigoDanoBase = 6;
                enemigoOro = 20;
                break;
            case "guardian":
                enemigoVida = 150 + (numerosCombates * 5);
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

        while (enCombate) {

            mostrarEstadoCombate(mob, enemigoVida);

            System.out.println("\n¿Qué harás?");
            System.out.println("[1] Atacar");
            System.out.println("[2] Ataque Mágico (25 PM)");
            System.out.println("[3] Objetos");
            System.out.println("[4] Rendirse");

            int opcion = src.nextInt();
            boolean turnoEnemigo = true;

            switch (opcion) {
                case 1:
                    atacar();
                    enemigoVida -= danoCalculado;
                    break;
                case 2:
                    if (stamina >= 25) {
                        ataqueMagico();
                        enemigoVida -= danoCalculado;
                    } else {
                        System.out.println("¡No tienes suficiente stamina!");
                        turnoEnemigo = false;
                    }
                    break;
                case 3:
                    usarObjeto();
                    turnoEnemigo = false;
                    break;
                case 4:
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

            if (enCombate && enemigoVida <= 0) {
                System.out.println("\n¡Has derrotado al " + mob + "!");
                Thread.sleep(1000);

                cambioOro = (int) (Math.random() * 5) + 1 + enemigoOro;
                oro += cambioOro;
                System.out.println("Has ganado " + cambioOro + " de oro. Total: " + oro);

                String objetoGanado = recompensas[(int) (Math.random() * recompensas.length)];
                agregarAlInventario(objetoGanado);
                System.out.println("¡Has obtenido: " + objetoGanado + "!");

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

            if (enCombate && turnoEnemigo) {
                System.out.println("\n--- Turno del enemigo ---");
                Thread.sleep(1000);

                int danoEnemigo = (int) (Math.random() * (20 - 10 + 1)) + 10;
                String interaccionEnemigo = interaccion.get((int) (Math.random() * interaccion.size()));

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

                stamina += 10;
                if (stamina > staminaMax) stamina = staminaMax;

                Thread.sleep(1500);

                if (vida <= 0) {
                    System.out.println("\n¡Has sido derrotado!");
                    resultadoCombate = false;
                    enCombate = false;
                }
            }
        }
    }

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
            case "crítico":
                danoCalculado = (int) (danoBase * 1.5);
                System.out.println("¡CRÍTICO! Haces " + danoCalculado + " de daño.");
                break;
            case "debil":
                danoCalculado = (int) (danoBase * 0.5);
                System.out.println("Golpe débil: " + danoCalculado + " de daño.");
                break;
        }
    }

    public static void ataqueMagico() throws InterruptedException {
        stamina -= 25;
        danoCalculado = (int) (danoMagico * multiplicadorDano);

        System.out.println("\n¡Lanzas un hechizo devastador!");
        Thread.sleep(1000);
        System.out.println("Haces " + danoCalculado + " de daño mágico.");
        System.out.println("Stamina restante: " + stamina);
    }

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

                switch (objeto) {
                    case "Poción de vida":
                        vida += 25;
                        if (vida > vidaMax) vida = vidaMax;
                        System.out.println("Usas una Poción de vida. Vida +25. Vida actual: " + vida);
                        break;
                    case "Poción Mágica":
                        stamina += 25;
                        if (stamina > staminaMax) stamina = staminaMax;
                        System.out.println("Usas una Poción Mágica. Stamina +25. Stamina actual: " + stamina);
                        break;
                    case "Poción de daño":
                        danoFisico += 5;
                        System.out.println("Usas una Poción de daño. Daño físico permanentemente +5.");
                        break;
                    case "Poción de daño extremo":
                        multiplicadorDano += 0.05f;
                        System.out.println("Usas una Poción de daño extremo. Multiplicador +0.05.");
                        break;
                    case "Amuleto Crítico":
                        interaccion.add("crítico");
                        amuletoCriticoComprados++;
                        calcularProbabilidadCritico();
                        System.out.println("¡Usas el Amuleto Crítico!");
                        System.out.println("Se añade 1 'crítico' más al ArrayList de interacciones.");
                        System.out.println("Probabilidad de crítico aumentada permanentemente!");
                        System.out.println("Amuletos usados: " + amuletoCriticoComprados);
                        break;
                }

                for (int i = opcion - 1; i < itemsEnInventario - 1; i++) {
                    inventario[i] = inventario[i + 1];
                }
                inventario[itemsEnInventario - 1] = null;
                itemsEnInventario--;

                Thread.sleep(1500);
            }
        }
    }

    public static void taberna() throws InterruptedException {
        System.out.println("\n========================================");
        System.out.println("Tras una larga batalla, el héroe ve");
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

            String[] objetosTienda = new String[3];
            int[] preciosTienda = new int[3];

            for (int i = 0; i < 3; i++) {
                objetosTienda[i] = recompensas[(int) (Math.random() * recompensas.length)];

                if (objetosTienda[i].equals("Amuleto Crítico")) {
                    preciosTienda[i] = (int) (Math.random() * 20) + 40; // 40-60 oro
                } else {
                    preciosTienda[i] = (int) (Math.random() * 30) + 10; // 10-40 oro
                }

                System.out.print("[" + (i + 1) + "] " + objetosTienda[i] + " - " + preciosTienda[i] + " oro");

                if (objetosTienda[i].equals("Amuleto Crítico")) {
                    System.out.print("(Aumenta probabilidad de crítico)");
                }
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

    public static void agregarAlInventario(String objeto) {
        if (itemsEnInventario < 10) {
            inventario[itemsEnInventario] = objeto;
            itemsEnInventario++;
        } else {
            System.out.println("Tu inventario está lleno.");
        }
    }

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

        for (int i = 0; i < inventario.length; i++) {
            inventario[i] = null;
        }

        interaccion.clear();
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("normal");
        interaccion.add("crítico");
        interaccion.add("esquive");
        interaccion.add("debil");
    }
}