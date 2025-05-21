package src;

public class TAC {
    public final String resultado;
    public final String operando1;
    public final String operador;
    public final String operando2;

    public TAC(String resultado, String operando1, String operador, String operando2) {
        this.resultado = resultado;
        this.operando1 = operando1;
        this.operador = operador;
        this.operando2 = operando2;
    }

    @Override
    public String toString() {
        if (operador == null) {
            return resultado + " = " + operando1;
        } else {
            return resultado + " = " + operando1 + " " + operador + " " + operando2;
        }
    }
}

