package src;

import src.Parser.AlgebraBaseVisitor;
import src.Parser.AlgebraParser;

import java.util.HashMap;
import java.util.Map;

public class AlgebraEvaluatorVisitor extends AlgebraBaseVisitor<Number> {

    private final Map<String, Number> memoria = new HashMap<>();
    private final Map<String, String> tipos = new HashMap<>();

    @Override
    public Number visitProgram(AlgebraParser.ProgramContext ctx) {
        for (AlgebraParser.InstruccionContext instr : ctx.instruccion()) {
            visit(instr);
        }
        return 0;
    }

    @Override
    public Number visitDeclaracion(AlgebraParser.DeclaracionContext ctx) {
        String tipo = ctx.tipo().getText();

        for (AlgebraParser.DeclaradorContext declarador : ctx.listaDeclaradores().declarador()) {
            String id = declarador.IDENTIFICADOR().getText();
            Number valor = 0;

            if (declarador.expresion() != null) {
                valor = visit(declarador.expresion());
            }

            if (tipo.equals("int") && valor instanceof Double) {
                double val = valor.doubleValue();
                if (val != Math.floor(val)) {
                    throw new RuntimeException("No se puede asignar un double con decimales a una variable int: " + id);
                }
                valor = (int) val;
            }
            if (tipo.equals("double") && valor instanceof Integer) {
                valor = ((Integer) valor).doubleValue();
            }
            memoria.put(id, valor);
            tipos.put(id, tipo);
            System.out.println("Declarado: " + tipo + " " + id + " = " + valor);
        }

        return 0;
    }

    @Override
    public Number visitAsignacion(AlgebraParser.AsignacionContext ctx) {
        String id = ctx.IDENTIFICADOR().getText();
        if (!tipos.containsKey(id)) {
            throw new RuntimeException("Variable no declarada: " + id);
        }

        Number valor = visit(ctx.expresion());
        String tipo = tipos.get(id);

        // Forzar al tipo
        if (tipo.equals("int") && valor instanceof Double) {
            double val = valor.doubleValue();
            if (val != Math.floor(val)) {
                throw new RuntimeException("No se puede asignar un double con decimales a una variable int: " + id);
            }
            valor = (int) val;
        } else if (tipo.equals("double") && valor instanceof Integer) {
            valor = ((Integer) valor).doubleValue();
        }

        memoria.put(id, valor);
        System.out.println("Asignado: " + id + " = " + valor);
        return valor;
    }

    @Override
    public Number visitExpresion(AlgebraParser.ExpresionContext ctx) {
        return visit(ctx.sumaResta());
    }

    @Override
    public Number visitSumaResta(AlgebraParser.SumaRestaContext ctx) {
        Number resultado = visit(ctx.multiplicacionDivision(0));
        for (int i = 1; i < ctx.multiplicacionDivision().size(); i++) {
            Number derecha = visit(ctx.multiplicacionDivision(i));
            String op = ctx.getChild(2 * i - 1).getText();
            resultado = operar(resultado, derecha, op);
        }
        return resultado;
    }

    @Override
    public Number visitMultiplicacionDivision(AlgebraParser.MultiplicacionDivisionContext ctx) {
        Number resultado = visit(ctx.agrupacion(0));
        for (int i = 1; i < ctx.agrupacion().size(); i++) {
            Number derecha = visit(ctx.agrupacion(i));
            String op = ctx.getChild(2 * i - 1).getText();
            resultado = operar(resultado, derecha, op);
        }
        return resultado;
    }

    @Override
    public Number visitAgrupacion(AlgebraParser.AgrupacionContext ctx) {
        if (ctx.expresion() != null) return visit(ctx.expresion());
        if (ctx.literal() != null) return visit(ctx.literal());
        if (ctx.IDENTIFICADOR() != null) {
            String id = ctx.IDENTIFICADOR().getText();
            if (!memoria.containsKey(id))
                throw new RuntimeException("Variable no declarada: " + id);
            return memoria.get(id);
        }
        return 0;
    }

    @Override
    public Number visitLiteral(AlgebraParser.LiteralContext ctx) {
        if (ctx.INT_LITERAL() != null)
            return Integer.parseInt(ctx.INT_LITERAL().getText());
        else
            return Double.parseDouble(ctx.DOUBLE_LITERAL().getText());
    }

    private Number operar(Number izq, Number der, String operador) {
        boolean isDouble = izq instanceof Double || der instanceof Double;
        double a = izq.doubleValue();
        double b = der.doubleValue();
        double resultado;

        switch (operador) {
            case "+": resultado = a + b; break;
            case "-": resultado = a - b; break;
            case "*": resultado = a * b; break;
            case "/": resultado = a / b; break;
            case "#": resultado = a % b; break;
            default: resultado = 0;
        }

        if (!isDouble && resultado == Math.floor(resultado)) return (int) resultado;
        return resultado;
    }

    public Map<String, Number> getMemoria() {
        return memoria;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }
}
