grammar CcmFol;

program
   : statement +
   ;

statement
   // | '{' statement* '}'
   : ';'
   | term_def ';'
   | relation_dec ';'
   | relation_def ';'
   | predicate_def ';'
   | constraint_dec ';'
 //  | soft_constraint_dec ';'   
   | optimize_statement ';'
 //  | eval_statement ';'
   ;
eval_statement
    : Identifier
    | Identifier Identifier '?'
    ;

/**
Defining terms.
**/

term_def
    : TERM termSeq    
    ;

termSeq
    : termName (',' termName )*   // term n1,n2,n3,n4 ;
    | termName '[' INT '-' INT ']' // term n[1-4] ;
    ;

termName : Identifier;

/**
End Defining terms.
**/


relation_dec
    : RELATION Identifier
    ;

relation_def
    : Identifier Identifier '=' Identifier*
    ;

predicate_def
    : PREDICATE Identifier
    | MUTEX_PREDICATE (',' Identifier)*
    ;

constraint_dec
    : CONSTRAINT Identifier '=' constraint_def
    | SOFT_CONSTRAINT Identifier '=' constraint_def
    ;

constraint_def
    : '{' constraint_def '}'
    | indicatorVar
    | constraint_def (OR constraint_def)+
    | constraint_def (AND constraint_def)+
    | NOT constraint_def
    | atMost '->' constraint_def
    | atLeast '->' constraint_def
    | exist '->' constraint_def
    | forall '->' constraint_def     
    ;

optimize_statement
    : opt_op poly_obj SUBJECT_TO (Identifier)*
    ;

poly_obj
    :  number indicatorVar
    |  '-' number indicatorVar
    |  '+' number indicatorVar
    ;

opt_op
    : MIN
    | MAX
    ;

number
    : INT
    | INT '.' INT
    ;

indicatorVar
    : Identifier '(' Identifier ')'
    ;

// Quantifiers
forall
    : FORALL Identifier IN termSeq 
    ;

exist
    : EXIST Identifier IN termSeq
    ;

atLeast
    : ATLEAST INT Identifier IN  termSeq
    ;

atMost
    : ATMOST INT Identifier IN  termSeq
    ;

// Lexical items:

TERM : 'term';

RELATION : 'relation';
CONSTRAINT : 'constraint';
SOFT_CONSTRAINT : 'soft_constraint';

PREDICATE : 'predicate';
MUTEX_PREDICATE : 'mutex_predicate';

MIN : 'min' ;
MAX : 'max' ;
SUBJECT_TO: 'subjectTo' ;

IN : 'in';

// Operators
AND : 'and';
OR : 'or';
NOT : 'not';
FORALL : 'forall';
EXIST : 'exist';
ATLEAST : 'atLeast';
ATMOST : 'atMost';


// Literals

Identifier
    :   JavaLetter JavaLetterOrDigit*
    ;

fragment
JavaLetter
    :   [a-zA-Z$_] // these are the "java letters" below 0x7F
//    |   // covers all characters above 0x7F which are not a surrogate
//       ~[\u0000-\u007F\uD800-\uDBFF]
//        {Character.isJavaIdentifierStart(_input.LA(-1))}?
//    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
//        [\uD800-\uDBFF] [\uDC00-\uDFFF]
//        {Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;

fragment
JavaLetterOrDigit
    :   [a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
 //   |   // covers all characters above 0x7F which are not a surrogate
 //       ~[\u0000-\u007F\uD800-\uDBFF]
 //       {Character.isJavaIdentifierPart(_input.LA(-1))}?
 //   |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
 //       [\uD800-\uDBFF] [\uDC00-\uDFFF]
 //       {Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;



// Common
STRING
   : [a-z]+
   ;


INT
   : [0-9] +
   ;

WS
   : [ \r\n\t] -> skip
   ;
