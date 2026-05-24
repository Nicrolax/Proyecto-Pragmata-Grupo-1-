import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=================================================");
        System.out.println(" SISTEMA PRAGMATA   ");
        System.out.println("=================================================");

while (!salir) {
System.out.println("--- MENÚ DE OPERACIONES ---");
System.out.println("1- Jugar con: Secuencia Estándar (Matriz 4x4)");
System.out.println("2. Jugar con: Secuencia Personalizada (Definir Grilla)");
System.out.println("3. Desplegar Bitácora de Instrucciones");
System.out.println("4. Terminar Ejecución");
System.out.print("Elija su procedimiento: ");

String opcion = entrada.nextLine();
switch (opcion) {
                case "1":
                    Juego SesionPorDefecto = new Juego(4, 4);
                    SesionPorDefecto.iniciar();
                    break;
                case "2":
                    try {
                        System.out.print("Ingrese cantidad de filas (Mínimo 4): ");
                        int filas = Integer.parseInt(entrada.nextLine());
                        System.out.print("Ingrese cantidad de columnas (Mínimo 4): ");
                        int columnas = Integer.parseInt(entrada.nextLine());

                        if (filas >= 4 && columnas >= 4) {
                            Juego sesionPersonalizada = new Juego(filas, columnas);
                            sesionPersonalizada.iniciar();
                        } else {
                            System.out.println("[!] Se requiere una grila de mínimo 4x4.\n");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Error crítico: Digite exclusivamente valores enteros válidos.\n");
                    }
                    break;
                case "3":
                    mostrarManual();
                    break;
                case "4":
                    salir = true;
                    System.out.println("Cerrando sesión....");
                    break;
                default:
                    System.out.println("[!]Debe ingresar un numero del 1 al 4. Reintente.\n");
            }
        }
        entrada.close();
    }

    private static void mostrarManual() {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("                  MANUAL DE JUEGO                ");
        System.out.println("------------------------------------------------------------");
        System.out.println(" * Lleve su indicador [VOS] a la celda objetivo [?! ].");
        System.out.println(" * Comienza con un capital de 100 puntos en la casilla [0,0].");
        System.out.println(" * Cruzar un Potenciador [X2 ] MULTIPLICA por dos sus puntos.");
        System.out.println(" * Cruzar un Modificador [+50] añade +50 puntos de inmediato.");
        System.out.println(" * El rastro de celdas resueltas se marcará con una cruz [X  ].");
        System.out.println(" * No se autoriza el paso en diagonales ni saltos compuestos.");
        System.out.println("------------------------------------------------------------\n");
    }
}