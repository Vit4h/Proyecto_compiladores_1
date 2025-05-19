grammar Algebra;

// Tokens
ASIGNACION: '=>';
PUNTO_Y_COMA: ';';
COMA: ',';
MAS: '+';
MENOS: '-';
POR: '*';
DIVIDIDO: '/';
MODULO: '#';
LLAVE_IZQUIERDA: '{';
LLAVE_DERECHA: '}';
INT_LITERAL: [0-9]+;
DOUBLE_LITERAL: [0-9]+ '.' [0-9]+;
INT_TIPO: 'int';
DOUBLE_TIPO: 'double';
IDENTIFICADOR: [a-zA-Z_][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;

// Reglas
program: (instruccion PUNTO_Y_COMA)* EOF ;

instruccion
    : declaracion
    | asignacion
    ;

asignacion
    : IDENTIFICADOR ASIGNACION expresion
    ;

declaracion
    : tipo listaDeclaradores
    ;

tipo
    : INT_TIPO
    | DOUBLE_TIPO
    ;

listaDeclaradores
    : declarador (COMA declarador)*
    ;

declarador
    : IDENTIFICADOR (ASIGNACION expresion)?
    ;

expresion
    : sumaResta
    ;

sumaResta
    : multiplicacionDivision ((MAS | MENOS) multiplicacionDivision)*
    ;

multiplicacionDivision
    : agrupacion ((POR | DIVIDIDO | MODULO) agrupacion)*
    ;

agrupacion
    : LLAVE_IZQUIERDA expresion LLAVE_DERECHA
    | literal
    | IDENTIFICADOR
    ;

literal
    : INT_LITERAL
    | DOUBLE_LITERAL
    ;
