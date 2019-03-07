JFlex_DIR         = FOLDER_0_JFlex
CUP_DIR           = FOLDER_1_CUP
SRC_DIR           = FOLDER_2_SRC
BIN_DIR           = FOLDER_3_BIN
INPUT_DIR         = FOLDER_4_INPUT
OUTPUT_DIR        = FOLDER_5_OUTPUT
EXTERNAL_JARS_DIR = FOLDER_7_EXTERNAL_JARS
MANIFEST_DIR      = FOLDER_8_MANIFEST

JFlex_CUP_GENERATED_FILES = ${SRC_DIR}/Lexer.java ${SRC_DIR}/Parser.java ${SRC_DIR}/TokenNames.java
SRC_FILES                 = ${SRC_DIR}/*.java ${SRC_DIR}/IR/*.java ${SRC_DIR}/AST/*.java ${SRC_DIR}/TYPES/*.java
EXTERNAL_JAR_FILES        = ${EXTERNAL_JARS_DIR}/java-cup-11b-runtime.jar
MANIFEST_FILE             = ${MANIFEST_DIR}/MANIFEST.MF

JFlex_PROGRAM  = jflex
JFlex_FLAGS    = -q
JFlex_DEST_DIR = ${SRC_DIR}
JFlex_FILE     = ${JFlex_DIR}/LEX_FILE.lex

CUP_PROGRAM = java -jar ${EXTERNAL_JARS_DIR}/java-cup-11b.jar
CUP_FLAGS   = -nosummary -nowarn -parser Parser -symbols TokenNames
CUP_FILE    = ${CUP_DIR}/CUP_FILE.cup

INPUT    = ${INPUT_DIR}/Input.txt
# INPUT    = ${INPUT_DIR}/TEST_01_Print_Primes.txt
# INPUT    = ${INPUT_DIR}/TEST_02_Bubble_Sort.txt
# INPUT    = ${INPUT_DIR}/TEST_03_Merge_Lists.txt
# INPUT    = ${INPUT_DIR}/TEST_04_Matrices.txt
OUTPUT   = ${OUTPUT_DIR}/MIPS.txt

default: compile

clean:
	rm -rf COMPILER
	rm -rf ${JFlex_CUP_GENERATED_FILES} ${BIN_DIR}/*.class ${BIN_DIR}/AST/*.class ${OUTPUT_DIR}/*.txt ${OUTPUT_DIR}/*.jpeg

compile: clean
	$(JFlex_PROGRAM) ${JFlex_FLAGS} -d ${JFlex_DEST_DIR} ${JFlex_FILE}
	$(CUP_PROGRAM) ${CUP_FLAGS} -destdir ${SRC_DIR} ${CUP_FILE}
	javac -cp ${EXTERNAL_JAR_FILES} -d ${BIN_DIR} ${SRC_FILES}
	jar cfm COMPILER ${MANIFEST_FILE} -C ${BIN_DIR} .
	
test:
	java -jar COMPILER ${INPUT} ${OUTPUT}
	@# dot -Tjpeg -o${OUTPUT_DIR}/AST.jpeg ${OUTPUT_DIR}/AST.txt
	@# dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable.jpeg ${OUTPUT_DIR}/SymbolTable.txt
	spim -f ${OUTPUT_DIR}/MIPS.txt #> ${OUTPUT_DIR}/MIPS_OUTPUT.txt	

everything: clean compile test
