public class Celda {
    // Almacena los siguientes estados que puede tener una celda "INICIO", "VACIA", "OBJETIVO", "POTENCIADOR" o "MODIFICADOR"
    private String tipo;
    private boolean visitada;

    public Celda(String tipo) {
        this.tipo = tipo;
        this.visitada = false;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    @Override
    public String toString() {
        // Simplificacion para una buena adaptacion visual
        switch (tipo) {
            case "INICIO": return "[INI]";
            case "OBJETIVO": return "[ ?!]"; // Celda Objetivo (Verde)
            case "POTENCIADOR": return "[ X2]"; // Potenciador (Azul)
            case "MODIFICADOR": return "[+50]"; // Modificador (Amarillo)
            default: return "[   ]"; // Celda vacía
        }
    }
}