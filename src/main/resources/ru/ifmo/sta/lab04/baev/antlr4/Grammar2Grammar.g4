grammar Grammar2Grammar;
s: a;
a: PLUS a a
 | MINUS a a
 | NUMBER;

PLUS: '+';
MINUS: '-';
NUMBER: [0-9]+;