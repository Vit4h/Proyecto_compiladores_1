grammar Algebra;

ASIGNACION: '=>';
PUNTO_Y_COMA: ';';
MAS: '+';
MENOS: '-';
POR: '*';
DIVIDIDO: '/';
MODULO: '#';
LLAVE_IZQUIERDA: '{';
LLAVE_DERECHA: '}';
INT: [0-9]+;
DOUBLE: [0-9]+ '.' [0-9]+;
IDENTIFICADOR: [a-zA-Z_][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;

program: (instruccion PUNTO_Y_COMA)* EOF;
instruccion: asignacion | expresion;
asignacion: IDENTIFICADOR ASIGNACION expresion;
expresion: sumaResta;
sumaResta: multiplicacionDivision ((MAS | MENOS) multiplicacionDivision)*;
multiplicacionDivision: agrupacion ((POR | DIVIDIDO | MODULO) agrupacion)*;
agrupacion: LLAVE_IZQUIERDA expresion LLAVE_DERECHA
          | literal
          | IDENTIFICADOR;
literal: INT | DOUBLE;
