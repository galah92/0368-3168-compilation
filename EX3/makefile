JFlex_DIR         = FOLDER_0_JFlex
CUP_DIR           = FOLDER_1_CUP
SRC_DIR           = FOLDER_2_SRC
BIN_DIR           = FOLDER_3_BIN
INPUT_DIR         = FOLDER_4_INPUT
OUTPUT_DIR        = FOLDER_5_OUTPUT
EXTERNAL_JARS_DIR = FOLDER_7_EXTERNAL_JARS
MANIFEST_DIR      = FOLDER_8_MANIFEST

JFlex_GENERATED_FILE      = ${SRC_DIR}/Lexer.java
CUP_GENERATED_FILES       = ${SRC_DIR}/Parser.java ${SRC_DIR}/TokenNames.java
JFlex_CUP_GENERATED_FILES = ${JFlex_GENERATED_FILE} ${CUP_GENERATED_FILES}
SRC_FILES                 = ${SRC_DIR}/*.java              \
                            ${SRC_DIR}/AST/*.java          \
                            ${SRC_DIR}/TYPES/*.java        \
                            ${SRC_DIR}/SymbolTable/*.java
EXTERNAL_JAR_FILES        = ${EXTERNAL_JARS_DIR}/java-cup-11b-runtime.jar
MANIFEST_FILE             = ${MANIFEST_DIR}/MANIFEST.MF

JFlex_PROGRAM  = jflex
JFlex_FLAGS    = -q
JFlex_DEST_DIR = ${SRC_DIR}
JFlex_FILE     = ${JFlex_DIR}/LEX_FILE.lex

CUP_PROGRAM                    = java -jar ${EXTERNAL_JARS_DIR}/java-cup-11b.jar 
CUP_FILE                       = ${CUP_DIR}/CUP_FILE.cup
CUP_GENERATED_PARSER_NAME      = Parser
CUP_GENERATED_SYMBOLS_FILENAME = TokenNames

CUP_FLAGS =                                \
-nowarn                                    \
-parser  ${CUP_GENERATED_PARSER_NAME}      \
-symbols ${CUP_GENERATED_SYMBOLS_FILENAME} 

INPUT    = ${INPUT_DIR}/Input.txt
OUTPUT   = ${OUTPUT_DIR}/SemanticStatus.txt

default: compile

clean:
	rm -rf COMPILER
	rm -rf ${JFlex_CUP_GENERATED_FILES} ${BIN_DIR}/*.class ${BIN_DIR}/AST/*.class

compile: clean
	$(JFlex_PROGRAM) ${JFlex_FLAGS} -d ${JFlex_DEST_DIR} ${JFlex_FILE}
	$(CUP_PROGRAM) ${CUP_FLAGS} -destdir ${SRC_DIR} ${CUP_FILE}
	javac -cp ${EXTERNAL_JAR_FILES} -d ${BIN_DIR} ${SRC_FILES}
	jar cfm COMPILER ${MANIFEST_FILE} -C ${BIN_DIR} .
	
everything: clean compile
	java -jar COMPILER ${INPUT} ${OUTPUT}
	dot -Tjpeg -o${OUTPUT_DIR}/AST.jpeg ${OUTPUT_DIR}/AST_IN_GRAPHVIZ_DOT_FORMAT.txt
#	eog ${OUTPUT_DIR}/AST.jpeg &
	dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_0.jpeg  ${OUTPUT_DIR}/SymbolTable_0_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_1.jpeg  ${OUTPUT_DIR}/SymbolTable_1_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_2.jpeg  ${OUTPUT_DIR}/SymbolTable_2_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_3.jpeg  ${OUTPUT_DIR}/SymbolTable_3_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_4.jpeg  ${OUTPUT_DIR}/SymbolTable_4_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_5.jpeg  ${OUTPUT_DIR}/SymbolTable_5_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_6.jpeg  ${OUTPUT_DIR}/SymbolTable_6_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_7.jpeg  ${OUTPUT_DIR}/SymbolTable_7_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_8.jpeg  ${OUTPUT_DIR}/SymbolTable_8_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_9.jpeg  ${OUTPUT_DIR}/SymbolTable_9_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_10.jpeg ${OUTPUT_DIR}/SymbolTable_10_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_11.jpeg ${OUTPUT_DIR}/SymbolTable_11_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_12.jpeg ${OUTPUT_DIR}/SymbolTable_12_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_13.jpeg ${OUTPUT_DIR}/SymbolTable_13_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_14.jpeg ${OUTPUT_DIR}/SymbolTable_14_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_15.jpeg ${OUTPUT_DIR}/SymbolTable_15_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_16.jpeg ${OUTPUT_DIR}/SymbolTable_16_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_17.jpeg ${OUTPUT_DIR}/SymbolTable_17_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_18.jpeg ${OUTPUT_DIR}/SymbolTable_18_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_19.jpeg ${OUTPUT_DIR}/SymbolTable_19_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_20.jpeg ${OUTPUT_DIR}/SymbolTable_20_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_21.jpeg ${OUTPUT_DIR}/SymbolTable_21_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_22.jpeg ${OUTPUT_DIR}/SymbolTable_22_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_23.jpeg ${OUTPUT_DIR}/SymbolTable_23_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_24.jpeg ${OUTPUT_DIR}/SymbolTable_24_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_25.jpeg ${OUTPUT_DIR}/SymbolTable_25_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_26.jpeg ${OUTPUT_DIR}/SymbolTable_26_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_27.jpeg ${OUTPUT_DIR}/SymbolTable_27_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_28.jpeg ${OUTPUT_DIR}/SymbolTable_28_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_29.jpeg ${OUTPUT_DIR}/SymbolTable_29_IN_GRAPHVIZ_DOT_FORMAT.txt
	@dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable_30.jpeg ${OUTPUT_DIR}/SymbolTable_30_IN_GRAPHVIZ_DOT_FORMAT.txt
#	eog ${OUTPUT_DIR}/SymbolTable_0.jpeg &
	
