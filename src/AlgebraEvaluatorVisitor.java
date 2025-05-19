package src;
import src.Parser.AlgebraBaseVisitor;
import src.Parser.AlgebraParser;

import java.util.*;

public class AlgebraEvaluatorVisitor extends AlgebraBaseVisitor<Double>{
    private final Map<String, Double> memoria = new HashMap<>();
    @Override
    public Double visitProgram(AlgebraParser.ProgramContext ctx) {
        for (AlgebraParser.InstruccionContext instr : ctx.instruccion()) {
            visit(instr); // evaluar cada instrucción
        }
        return 0.0;
    }

    @Override
    public Double visitAsignacion(AlgebraParser.AsignacionContext ctx) {
        String variable = ctx.IDENTIFICADOR().getText();
        Double valor = visit(ctx.expresion());
        memoria.put(variable, valor);
        System.out.println("Asignado: " + variable + " = " + valor);
        return valor;
    }

    @Override
    public Double visitExpresion(AlgebraParser.ExpresionContext ctx) {
        return visit(ctx.sumaResta());
    }

    @Override
    public Double visitSumaResta(AlgebraParser.SumaRestaContext ctx) {
        Double resultado = visit(ctx.multiplicacionDivision(0));
        for (int i = 1; i < ctx.multiplicacionDivision().size(); i++) {
            Double siguiente = visit(ctx.multiplicacionDivision(i));
            String operador = ctx.getChild(2 * i - 1).getText();
            if (operador.equals("+")) {
                resultado += siguiente;
            } else {
                resultado -= siguiente;
            }
        }
        return resultado;
    }

    @Override
    public Double visitMultiplicacionDivision(AlgebraParser.MultiplicacionDivisionContext ctx) {
        Double resultado = visit(ctx.agrupacion(0));
        for (int i = 1; i < ctx.agrupacion().size(); i++) {
            Double siguiente = visit(ctx.agrupacion(i));
            String operador = ctx.getChild(2 * i - 1).getText();
            switch (operador) {
                case "*": resultado *= siguiente; break;
                case "/": resultado /= siguiente; break;
                case "#": resultado %= siguiente; break;
            }
        }
        return resultado;
    }

    @Override
    public Double visitAgrupacion(AlgebraParser.AgrupacionContext ctx) {
        if (ctx.expresion() != null) {
            return visit(ctx.expresion());
        } else if (ctx.literal() != null) {
            return visit(ctx.literal());
        } else if (ctx.IDENTIFICADOR() != null) {
            String id = ctx.IDENTIFICADOR().getText();
            return memoria.getOrDefault(id, 0.0);
        }
        return 0.0;
    }

    @Override
    public Double visitLiteral(AlgebraParser.LiteralContext ctx) {
        if (ctx.INT() != null) {
            return Double.parseDouble(ctx.INT().getText());
        } else {
            return Double.parseDouble(ctx.DOUBLE().getText());
        }
    }

    public Map<String, Double> getMemoria() {
        return memoria;
    }
}
