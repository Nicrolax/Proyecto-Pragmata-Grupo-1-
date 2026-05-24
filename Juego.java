import java.io.IOException;
import java.util.Scanner;

public class Juego {
    private Tablero tablero;
    private int filaJugador;
    private int colJugador;
    private int puntaje;
    private boolean terminado;

    // Estadísticas
    private int movimientosTotales;
    private int potenciadoresAgarrados;
    private int modificadoresAgarrados;

    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);
        this.filaJugador = 0;
        this.colJugador = 0;
        this.puntaje = 100; // Iniciamos con los 100 puntos estándar de hackeo
        this.terminado = false;
        this.movimientosTotales = 0;
        this.potenciadoresAgarrados = 0;
        this.modificadoresAgarrados = 0;
    }

    public void iniciar() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\n--- EL JUEGO HA COMENZADO ---");

        while (!terminado) {
            tablero.mostrarTablero(filaJugador, colJugador);
            System.out.println("Puntaje Acumulado: " + puntaje);
            System.out.println("Esquema de Entrada:");
            System.out.println("1. Controles Direccionales (WASD + ENTER)");
            System.out.println("2. Coordenadas de Matriz Numérica");
            System.out.print("Selecciona tu método de interfaz: ");

            String seleccion = entrada.nextLine();
            if (seleccion.equals("1")) {
                procesarMovimientoTeclas();
            } else if (seleccion.equals("2")) {
                procesarMovimientoCoordenadas(entrada);
            } else {
                System.out.println("Opción incorrecta. Reintente.");
            }
        }

        mostrarEstadisticasFinales();
    }

    private void procesarMovimientoTeclas() {
        System.out.println("Ingresa tu dirección (W: Arriba, S: Abajo, A: Izquierda, D: Derecha) y presiona ENTER:");
        try {
            int byteUno = System.in.read();
            int sigFila = filaJugador;
            int sigCol = colJugador;

            //  Movimientos (WASD)
            char caracter = Character.toUpperCase((char) byteUno);
            switch (caracter) {
                case 'W': sigFila--; break; // Dirección: Arriba
                case 'S': sigFila++; break; // Dirección: Abajo
                case 'A': sigCol--; break;  // Dirección: Izquierda
                case 'D': sigCol++; break;  // Dirección: Derecha
                default:
                    limpiarBuffer();
                    System.out.println("Dirección no reconocida por el sistema. Usa W, A, S o D.");
                    return;
            }

            // Limpia los bytes del ENTER (\r\n) para que no se tranque la consola
            limpiarBuffer();

            // Mover al jugador con las nuevas posiciones
            ejecutarDesplazamiento(sigFila, sigCol);

        } catch (IOException e) {
            System.out.println("Fallo crítico.");
        }
    }

    private void procesarMovimientoCoordenadas(Scanner entrada) {
        try {
            System.out.print("Ingresa Fila destino: ");
            int f = Integer.parseInt(entrada.nextLine());
            System.out.print("Ingresa Columna destino: ");
            int c = Integer.parseInt(entrada.nextLine());

            // Validación (Evita saltos lejanos y diagonales)
            int deltaFila = Math.abs(f - filaJugador);
            int deltaCol = Math.abs(c - colJugador);

            if ((deltaFila == 1 && deltaCol == 0) || (deltaFila == 0 && deltaCol == 1)) {
                ejecutarDesplazamiento(f, c);
            } else {
                System.out.println("[ERROR] Tránsito inválido. Solo desplazamientos perpendiculares continuos.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Formato incorrecto. Digite enteros correspondientes.");
        }
    }

    private void ejecutarDesplazamiento(int sigFila, int sigCol) {
        if (tablero.esMovimientoValido(sigFila, sigCol)) {
            filaJugador = sigFila;
            colJugador = sigCol;
            movimientosTotales++;

            Celda casillaActual = tablero.getGrilla()[filaJugador][colJugador];

            // Verificación
            if (!casillaActual.isVisitada()) {
                if (casillaActual.getTipo().equals("POTENCIADOR")) {
                    this.puntaje *= 2; // Efecto de duplicación
                    potenciadoresAgarrados++;
                    System.out.println(">> ALERTA: ¡POTENCIADOR RECONOCIDO! Puntos Duplicados.");
                } else if (casillaActual.getTipo().equals("MODIFICADOR")) {
                    this.puntaje += 50; // Inyección directa de 50 puntos
                    modificadoresAgarrados++;
                    System.out.println(">> ALERTA: ¡MODIFICADOR RECONOCIDO! +50 Puntos.");
                }
                casillaActual.setVisitada(true);
            }

            // Comprobación de llegada a la meta
            if (filaJugador == tablero.getFilaObjetivo() && colJugador == tablero.getColObjetivo()) {
                terminado = true;
                System.out.println("\n*** HAZ SUPERADO ESTE DESAFIO ***");
            }
        } else {
            System.out.println("[ERROR] Coordenadas fuera del perímetro de la grilla .");
        }
    }

    private void limpiarBuffer() throws IOException {
        while (System.in.available() > 0) {
            System.in.read();
        }
    }

    private void mostrarEstadisticasFinales() {
        System.out.println("\n=======================================");
        System.out.println("       MÉTRICAS LOGRADAS EN ACCIÓN     ");
        System.out.println("=======================================");
        System.out.println(" -> Rendimiento Final  : " + puntaje + " CamePoints");
        System.out.println(" -> Acciones de Avance : " + movimientosTotales);
        System.out.println(" -> Multiplicadores    : " + potenciadoresAgarrados);
        System.out.println(" -> Modificaciones     : " + modificadoresAgarrados);
        System.out.println("=======================================\n");
    }
}