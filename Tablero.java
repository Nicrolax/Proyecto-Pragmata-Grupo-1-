import java.util.Random;

public class Tablero {
    private Celda[][] grilla;
    private int filas;
    private int columnas;
    private int filaObjetivo;
    private int colObjetivo;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grilla = new Celda[filas][columnas];
        inicializarTablero();
    }

    private void inicializarTablero() {
        // Hacemos la matriz con celdas vacías básicas
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                grilla[f][c] = new Celda("VACIA");
            }
        }

        // 2. Establecemos el punto de partida en la coordenada 0;0
        grilla[0][0].setTipo("INICIO");
        grilla[0][0].setVisitada(true);

        Random aleatorio = new Random();

        // 3. Posicionamos la Celda Objetivo asegurando que no se superponga al inicio
        do {
            this.filaObjetivo = aleatorio.nextInt(filas);
            this.colObjetivo = aleatorio.nextInt(columnas);
        } while (this.filaObjetivo == 0 && this.colObjetivo == 0);
        grilla[filaObjetivo][colObjetivo].setTipo("OBJETIVO");

        // 4. Repartimos Potenciadores y Modificadores de forma aleatoria
        int elementos = Math.max(1, (filas * columnas) / 8);
        colocarElementosAleatorios("POTENCIADOR", elementos, aleatorio);
        colocarElementosAleatorios("MODIFICADOR", elementos, aleatorio);
    }

    private void colocarElementosAleatorios(String tipo, int cantidad, Random aleatorio) {
        int colocados = 0;
        while (colocados < cantidad) {
            int f = aleatorio.nextInt(filas);
            int c = aleatorio.nextInt(columnas);

            // Verificamos si la celda esta libre
            if (grilla[f][c].getTipo().equals("VACIA")) {
                grilla[f][c].setTipo(tipo);
                colocados++;
            }
        }
    }

    public boolean esMovimientoValido(int f, int c) {
        return f >= 0 && f < filas && c >= 0 && c < columnas;
    }

    public void mostrarTablero(int filaJugador, int colJugador) {
        // Cabecera numérica superior
        System.out.print("    ");
        for (int c = 0; c < columnas; c++) {
            System.out.print("  " + c + "   ");
        }
        System.out.println("\n    " + "------".repeat(columnas));

        // Impresión de matriz
        for (int f = 0; f < filas; f++) {
            System.out.print(f + " | ");
            for (int c = 0; c < columnas; c++) {
                if (f == filaJugador && c == colJugador) {
                    System.out.print("[VOS]"); // Ubicación actual en tiempo real
                } else if (grilla[f][c].isVisitada() && !grilla[f][c].getTipo().equals("INICIO")) {
                    System.out.print("[X  ]"); // Indicador de celda ya pisada/validada
                } else {
                    System.out.print(grilla[f][c].toString());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("    " + "------".repeat(columnas));
    }

    public Celda[][] getGrilla() { return grilla; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public int getFilaObjetivo() { return filaObjetivo; }
    public int getColObjetivo() { return colObjetivo; }
}