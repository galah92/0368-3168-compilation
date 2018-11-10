JFlex_DIR         = FOLDER_0_JFlex
CUP_DIR           = FOLDER_1_CUP
SRC_DIR           = FOLDER_2_SRC
BIN_DIR           = FOLDER_3_BIN
INPUT_DIR         = FOLDER_4_INPUT
OUTPUT_DIR        = FOLDER_5_OUTPUT
EXTERNAL_JARS_DIR = FOLDER_7_EXTERNAL_JARS
MANIFEST_DIR      = FOLDER_8_MANIFEST

# FILES #
JFlex_GENERATED_FILE      = ${SRC_DIR}/Lexer.java
CUP_GENERATED_FILES       = ${SRC_DIR}/Parser.java ${SRC_DIR}/TokenNames.java
JFlex_CUP_GENERATED_FILES = ${JFlex_GENERATED_FILE} ${CUP_GENERATED_FILES}
SRC_FILES                 = ${SRC_DIR}/*.java ${SRC_DIR}/AST/*.java
EXTERNAL_JAR_FILES        = ${EXTERNAL_JARS_DIR}/java-cup-11b-runtime.jar
MANIFEST_FILE             = ${MANIFEST_DIR}/MANIFEST.MF

# DEFINITIONS :: JFlex #
JFlex_PROGRAM  = jflex
JFlex_FLAGS    = -q
JFlex_DEST_DIR = ${SRC_DIR}
JFlex_FILE     = ${JFlex_DIR}/LEX_FILE.lex

# DEFINITIONS :: CUP #
CUP_PROGRAM                    = java -jar ${EXTERNAL_JARS_DIR}/java-cup-11b.jar 
CUP_FILE                       = ${CUP_DIR}/CUP_FILE.cup
CUP_GENERATED_PARSER_NAME      = Parser
CUP_GENERATED_SYMBOLS_FILENAME = TokenNames

# DEFINITIONS :: CUP #
CUP_FLAGS =                                \
-nowarn                                    \
-parser  ${CUP_GENERATED_PARSER_NAME}      \
-symbols ${CUP_GENERATED_SYMBOLS_FILENAME} 

# DEFINITIONS :: PARSER #
INPUT    = ${INPUT_DIR}/Input.txt
OUTPUT   = ${OUTPUT_DIR}/ParseStatus.txt

# TARGET #
all: clean
	$(JFlex_PROGRAM) ${JFlex_FLAGS} -d ${JFlex_DEST_DIR} ${JFlex_FILE}
	$(CUP_PROGRAM) ${CUP_FLAGS} -destdir ${SRC_DIR} ${CUP_FILE}
	javac -cp ${EXTERNAL_JAR_FILES} -d ${BIN_DIR} ${SRC_FILES}
	jar cfm PARSER ${MANIFEST_FILE} -C ${BIN_DIR} .
	java -jar PARSER ${INPUT} ${OUTPUT}
	dot -Tjpeg -o${OUTPUT_DIR}/AST.jpeg ${OUTPUT_DIR}/AST_IN_GRAPHVIZ_DOT_FORMAT.txt
	cp ${OUTPUT_DIR}/AST.jpeg /mnt/c/Users/Gal/Downloads/
#	eog ${OUTPUT_DIR}/AST.jpeg &

clean:
	rm -rf PARSER ${JFlex_CUP_GENERATED_FILES} ${BIN_DIR}/*.class ${BIN_DIR}/AST/*.class

