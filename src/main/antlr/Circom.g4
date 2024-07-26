grammar Circom;

import LexerCircom;

circuit
    :   pragmaDeclaration+ includeDeclaration* blockDeclaration* componentMainDeclaration?
        EOF
    ;

pragmaDeclaration
    : PRAGMA CIRCOM VERSION ';'
    | PRAGMA CUSTOM_TEMPLATES ';'
    ;

includeDeclaration
    : INCLUDE PACKAGE_NAME ';'
    ;

blockDeclaration
    : functionDeclaration
    | templateDeclaration
    ;

functionDeclaration
    : FUNCTION ID '(' args* ')' functionStmt
    ;

functionStmt
    : '{' functionStmt* '}'
    | ID SELF_OP
    | varDeclaration
    | expression (ASSIGNMENT | ASSIGMENT_OP) expression
    | IF parExpression functionStmt (ELSE functionStmt)?
    | WHILE parExpression functionStmt
    | FOR '(' forControl ')' functionStmt
    | RETURN expression
    | functionStmt ';'
    ;

templateDeclaration
    : TEMPLATE ID '(' args* ')' statement*
    | TEMPLATE CUSTOM ID '(' args* ')' statement*
    ;

componentMainDeclaration
    : COMPONENT MAIN ('{' PUBLIC '[' args ']'  '}')? '=' ID '(' intSequence* ')' ';'
    ;

statement
    : '{' statement* '}'
    | ID SELF_OP
    | varDeclaration
    | signalDeclaration
    | componentDeclaration
    | blockInstantiation
    | expression (ASSIGNMENT | CONSTRAINT_EQ) expression
    | (primary | ((ID arrayDimension*) '.' (ID arrayDimension*))) (LEFT_ASSIGNMENT | ASSIGMENT_OP) expression
    | expression RIGHT_ASSIGNMENT primary
    | '_' (ASSIGNMENT | LEFT_ASSIGNMENT) (expression | blockInstantiation)
    | (expression | blockInstantiation) RIGHT_ASSIGNMENT '_'
    | '(' argsWithUnderscore ')' (ASSIGNMENT | LEFT_ASSIGNMENT) blockInstantiation
    | blockInstantiation RIGHT_ASSIGNMENT '(' argsWithUnderscore ')'
    | IF parExpression statement (ELSE statement)?
    | WHILE parExpression statement
    | FOR '(' forControl ')' statement
    | ASSERT parExpression
    | LOG parExpression
    | statement ';'
    ;

forControl: forInit ';' expression ';' forUpdate ;

forInit: varDefinition (ASSIGNMENT rhsValue)? ;

forUpdate: expression | (ID (SELF_OP | (ASSIGNMENT expression))) ;

parExpression: '(' expression ')' ;

expression
    : primary
    | expression '.' ID
    | ID '.' ID '[' expression ']'
    | expression '?' expression ':' expression
    | blockInstantiation
    | ('~' | '!' | '-') expression
    | expression '**' expression
    | expression ('*' | '/' | '\\' | '%') expression
    | expression ('+' | '-') expression
    | expression ('<<' | '>>') expression
    | expression ('&' | '^' | '|') expression
    | expression ('==' | '!=' | '>' | '<' | '<=' | '>=' | '&&' | '||') expression
    ;

primary
    : '(' expression ')'
    | '[' expression (',' expression)* ']'
    | INT
    | ID arrayDimension*
    | args
    | intSequence
    ;

componentDefinition: COMPONENT ID ;

componentDeclaration
    : componentDefinition arrayDimension* (ASSIGNMENT blockInstantiation)?
    ;

signalDefinition: SIGNAL SIGNAL_TYPE? ('{' args '}')? ID arrayDimension*;

signalDeclaration
    : signalDefinition (LEFT_ASSIGNMENT rhsValue)?
    | signalDefinition (',' ID arrayDimension*)*
    ;

varDefinition: VAR ID arrayDimension* ;

varDeclaration
    : varDefinition (ASSIGNMENT rhsValue)?
    | varDefinition (',' ID arrayDimension*)*
    ;

rhsValue: expression | blockInstantiation ;

componentCall
    : '(' expression (',' expression)* ')'
    | '(' ID LEFT_ASSIGNMENT expression (',' ID LEFT_ASSIGNMENT expression)* ')'
    | '(' expression RIGHT_ASSIGNMENT ID (',' expression RIGHT_ASSIGNMENT ID)* ')'
    ;

blockInstantiation: ID '(' ((expression)* | (expression (',' expression)*)) ')' componentCall? ;

arrayDimension: '[' (INT | ID | expression) ']' ;

argsWithUnderscore: ('_' | ID) (',' ('_' | ID) )* ;

args: ID (',' ID)* ;

intSequence: INT (',' INT)* ;
