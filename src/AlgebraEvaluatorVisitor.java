package src;

import src.Parser.AlgebraBaseVisitor;
import src.Parser.AlgebraParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgebraEvaluatorVisitor extends AlgebraBaseVisitor<Number> {

    private final Map<String, Number> memoria = new HashMap<>();
    private final Map<String, String> tipos = new HashMap<>();
    private final List<String> acciones = new ArrayList<>();
    private final List<TAC> instruccionesTAC = new ArrayList<>();
    private int tempCounter = 1;


    private String nuevaTemp() {
        return "t" + (tempCounter++);
    }

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

            String mensaje = "Declarado: " + tipo + " " + id + " = " + valor;
            acciones.add(mensaje);
            System.out.println(mensaje);

            // Generar TAC para inicialización
            if (declarador.expresion() != null) {
                String temp = generarTACDesdeExpr(declarador.expresion());
                instruccionesTAC.add(new TAC(id, temp, null, null));
            } else {
                instruccionesTAC.add(new TAC(id, tipo.equals("double") ? "0.0" : "0", null, null));
            }
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
        String mensaje = "Asignado: " + id + " = " + valor;
        acciones.add(mensaje);
        System.out.println(mensaje);

        // Generar TAC para asignación
        String temp = generarTACDesdeExpr(ctx.expresion());
        instruccionesTAC.add(new TAC(id, temp, null, null));

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

    public void generarTAC(AlgebraParser.ProgramContext ctx) {
        for (AlgebraParser.InstruccionContext instr : ctx.instruccion()) {
            generarTACDesdeInstruccion(instr);
        }
    }

    private void generarTACDesdeInstruccion(AlgebraParser.InstruccionContext instr) {
        if (instr.asignacion() != null) {
            String id = instr.asignacion().IDENTIFICADOR().getText();
            String temp = generarTACDesdeExpr(instr.asignacion().expresion());
            instruccionesTAC.add(new TAC(id, temp, null, null));
        } else if (instr.declaracion() != null) {
            String tipo = instr.declaracion().tipo().getText();
            for (AlgebraParser.DeclaradorContext d : instr.declaracion().listaDeclaradores().declarador()) {
                String id = d.IDENTIFICADOR().getText();
                if (d.expresion() != null) {
                    String temp = generarTACDesdeExpr(d.expresion());
                    instruccionesTAC.add(new TAC(id, temp, null, null));
                } else {
                    instruccionesTAC.add(new TAC(id, tipo.equals("double") ? "0.0" : "0", null, null));
                }
            }
        }
    }

    private String generarTACDesdeExpr(AlgebraParser.ExpresionContext ctx) {
        return generarDesdeSumaResta(ctx.sumaResta());
    }

    private String generarDesdeSumaResta(AlgebraParser.SumaRestaContext ctx) {
        String izq = generarDesdeMultiplicacionDivision(ctx.multiplicacionDivision(0));
        for (int i = 1; i < ctx.multiplicacionDivision().size(); i++) {
            String der = generarDesdeMultiplicacionDivision(ctx.multiplicacionDivision(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String temp = nuevaTemp();
            instruccionesTAC.add(new TAC(temp, izq, op, der));
            izq = temp;
        }
        return izq;
    }

    private String generarDesdeMultiplicacionDivision(AlgebraParser.MultiplicacionDivisionContext ctx) {
        String izq = generarDesdeAgrupacion(ctx.agrupacion(0));
        for (int i = 1; i < ctx.agrupacion().size(); i++) {
            String der = generarDesdeAgrupacion(ctx.agrupacion(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String temp = nuevaTemp();
            instruccionesTAC.add(new TAC(temp, izq, op, der));
            izq = temp;
        }
        return izq;
    }

    private String generarDesdeAgrupacion(AlgebraParser.AgrupacionContext ctx) {
        if (ctx.expresion() != null) return generarTACDesdeExpr(ctx.expresion());
        if (ctx.literal() != null) return ctx.literal().getText();
        if (ctx.IDENTIFICADOR() != null) return ctx.IDENTIFICADOR().getText();
        return "0";
    }

    public void imprimirTAC() {
        System.out.println("\n[CÓDIGO DE TRES DIRECCIONES]");
        for (TAC t : instruccionesTAC) {
            System.out.println(t);
        }
    }

    public Map<String, Number> getMemoria() {
        return memoria;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }
    public List<String> getAcciones() {
        return acciones;
    }

    public List<String> getTAC() {
        List<String> tacStrings = new ArrayList<>();
        for (TAC t : instruccionesTAC) {
            tacStrings.add(t.toString());
        }
        return tacStrings;
    }

}