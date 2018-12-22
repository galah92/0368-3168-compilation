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
OUTPUT   = ${OUTPUT_DIR}/SemanticStatus.txt

default: compile

clean:
	rm -rf COMPILER
	rm -rf ${JFlex_CUP_GENERATED_FILES} ${BIN_DIR}/*.class ${BIN_DIR}/AST/*.class ${OUTPUT_DIR}/*.txt ${OUTPUT_DIR}/*.jpeg

compile: clean
	$(JFlex_PROGRAM) ${JFlex_FLAGS} -d ${JFlex_DEST_DIR} ${JFlex_FILE}
	$(CUP_PROGRAM) ${CUP_FLAGS} -destdir ${SRC_DIR} ${CUP_FILE}
	javac -cp ${EXTERNAL_JAR_FILES} -d ${BIN_DIR} ${SRC_FILES}
	jar cfm COMPILER ${MANIFEST_FILE} -C ${BIN_DIR} .
	
everything: clean compile
	java -jar COMPILER ${INPUT} ${OUTPUT}
	dot -Tjpeg -o${OUTPUT_DIR}/AST.jpeg ${OUTPUT_DIR}/AST.txt
	dot -Tjpeg -o${OUTPUT_DIR}/SymbolTable.jpeg ${OUTPUT_DIR}/SymbolTable.txt
	sed -i 's/\bTemp_0\b/$$t0/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_1\b/$$t1/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_2\b/$$t2/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_3\b/$$t3/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_4\b/$$t4/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_5\b/$$t5/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_6\b/$$t6/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_7\b/$$t7/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_8\b/$$t0/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_9\b/$$t1/g'  ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_10\b/$$t2/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_11\b/$$t3/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_12\b/$$t4/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_13\b/$$t5/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_14\b/$$t6/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_15\b/$$t7/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_16\b/$$t0/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_17\b/$$t1/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_18\b/$$t2/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_19\b/$$t3/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_20\b/$$t4/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_21\b/$$t5/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_22\b/$$t6/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_23\b/$$t7/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_24\b/$$t0/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_25\b/$$t1/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_26\b/$$t2/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_27\b/$$t3/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_28\b/$$t4/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_29\b/$$t5/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_30\b/$$t6/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_31\b/$$t7/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_32\b/$$t0/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_33\b/$$t1/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_34\b/$$t2/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_35\b/$$t3/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_36\b/$$t4/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_37\b/$$t5/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_38\b/$$t6/g' ${OUTPUT_DIR}/MIPS.txt
	sed -i 's/\bTemp_39\b/$$t7/g' ${OUTPUT_DIR}/MIPS.txt
	spim -f ${OUTPUT_DIR}/MIPS.txt #> ${OUTPUT_DIR}/MIPS_OUTPUT.txt	
