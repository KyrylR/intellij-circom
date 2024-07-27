grammar Circom;

import LexerCircom;

circuit
    :   pragmaDeclaration+ includeDeclaration* blockDeclaration* componentMainDeclaration?
        EOF
    ;

pragmaDeclaration
    : PRAGMA CIRCOM VERSION SEMICOLON
    | PRAGMA CUSTOM_TEMPLATES SEMICOLON
    ;

includeDeclaration
    : INCLUDE PACKAGE_NAME SEMICOLON
    ;

blockDeclaration
    : functionDeclaration
    | templateDeclaration
    ;

functionDeclaration
    : FUNCTION ID PARENTHESIS_OPEN args* PARENTHESIS_CLOSE functionStmt
    ;

functionStmt
    : CURLY_BRACKET_OPEN functionStmt* CURLY_BRACKET_CLOSE
    | ID SELF_OP
    | varDeclaration
    | expression (ASSIGNMENT | ASSIGMENT_OP) expression
    | IF parExpression functionStmt (ELSE functionStmt)?
    | WHILE parExpression functionStmt
    | FOR PARENTHESIS_OPEN forControl PARENTHESIS_CLOSE functionStmt
    | RETURN expression
    | functionStmt SEMICOLON
    ;

templateDeclaration
    : TEMPLATE ID PARENTHESIS_OPEN args* PARENTHESIS_CLOSE statement*
    | TEMPLATE CUSTOM ID PARENTHESIS_OPEN args* PARENTHESIS_CLOSE statement*
    ;

componentMainDeclaration
    : COMPONENT MAIN
        (
            CURLY_BRACKET_OPEN PUBLIC SQUER_BRACKET_OPEN args SQUER_BRACKET_CLOSE CURLY_BRACKET_CLOSE
        )?
        ASSIGNMENT ID PARENTHESIS_OPEN intSequence* PARENTHESIS_CLOSE SEMICOLON
    ;

statement
    : CURLY_BRACKET_OPEN statement* CURLY_BRACKET_CLOSE
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
    | PARENTHESIS_OPEN argsWithUnderscore PARENTHESIS_CLOSE (ASSIGNMENT | LEFT_ASSIGNMENT) blockInstantiation
    | blockInstantiation RIGHT_ASSIGNMENT PARENTHESIS_OPEN argsWithUnderscore PARENTHESIS_CLOSE
    | IF parExpression statement (ELSE statement)?
    | WHILE parExpression statement
    | FOR PARENTHESIS_OPEN forControl PARENTHESIS_CLOSE statement
    | ASSERT parExpression
    | LOG parExpression
    | statement SEMICOLON
    ;

forControl: forInit SEMICOLON expression SEMICOLON forUpdate ;

forInit: varDefinition (ASSIGNMENT rhsValue)? ;

forUpdate: expression | (ID (SELF_OP | (ASSIGNMENT expression))) ;

parExpression: PARENTHESIS_OPEN expression PARENTHESIS_CLOSE ;

expression
    : primary
    | expression '.' ID
    | ID '.' ID SQUER_BRACKET_OPEN expression SQUER_BRACKET_CLOSE
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
    : PARENTHESIS_OPEN expression PARENTHESIS_CLOSE
    | SQUER_BRACKET_OPEN expression (COMMA expression)* SQUER_BRACKET_CLOSE
    | INT
    | ID arrayDimension*
    | args
    | intSequence
    ;

componentDefinition: COMPONENT ID ;

componentDeclaration
    : componentDefinition arrayDimension* (ASSIGNMENT blockInstantiation)?
    ;

signalDefinition: SIGNAL SIGNAL_TYPE? (CURLY_BRACKET_OPEN args CURLY_BRACKET_CLOSE)? ID arrayDimension*;

signalDeclaration
    : signalDefinition (LEFT_ASSIGNMENT rhsValue)?
    | signalDefinition (COMMA ID arrayDimension*)*
    ;

varDefinition: VAR ID arrayDimension* ;

varDeclaration
    : varDefinition (ASSIGNMENT rhsValue)?
    | varDefinition (COMMA ID arrayDimension*)*
    ;

rhsValue: expression | blockInstantiation ;

componentCall
    : PARENTHESIS_OPEN expression (COMMA expression)* PARENTHESIS_CLOSE
    | PARENTHESIS_OPEN ID LEFT_ASSIGNMENT expression (COMMA ID LEFT_ASSIGNMENT expression)* PARENTHESIS_CLOSE
    | PARENTHESIS_OPEN expression RIGHT_ASSIGNMENT ID (COMMA expression RIGHT_ASSIGNMENT ID)* PARENTHESIS_CLOSE
    ;

blockInstantiation: ID PARENTHESIS_OPEN ((expression)* | (expression (COMMA expression)*)) PARENTHESIS_CLOSE componentCall? ;

arrayDimension: SQUER_BRACKET_OPEN (INT | ID | expression) SQUER_BRACKET_CLOSE ;

argsWithUnderscore: ('_' | ID) (COMMA ('_' | ID) )* ;

args: ID (COMMA ID)* ;

intSequence: INT (COMMA INT)* ;
