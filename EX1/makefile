JFlex_DIR		:= FOLDER_0_JFlex
SRC_DIR			:= FOLDER_2_SRC
BIN_DIR			:= FOLDER_3_BIN
INPUT_DIR		:= FOLDER_4_INPUT
OUTPUT_DIR		:= FOLDER_5_OUTPUT
EXPECTED_OUTPUT_DIR	:= FOLDER_6_EXPECTED_OUTPUT
EXTERNAL_JARS_DIR	:= FOLDER_7_EXTERNAL_JARS
MANIFEST_DIR		:= FOLDER_8_MANIFEST

JFlex_GENERATED_FILE	:= ${SRC_DIR}/Lexer.java
SRC_FILES		:= ${SRC_DIR}/*.java
CLASS_FILES		:= ${BIN_DIR}/*.class
EXTERNAL_JAR_FILES	:= ${EXTERNAL_JARS_DIR}/java-cup-11b-runtime.jar
MANIFEST_FILE		:= ${MANIFEST_DIR}/MANIFEST.MF

JFlex_DEST_DIR		:= ${SRC_DIR}
JFlex_FILE		:= ${JFlex_DIR}/LEX_FILE.lex

INPUT			:= ${INPUT_DIR}/Input.txt
OUTPUT			:= ${OUTPUT_DIR}/OutputTokens.txt

TEST_FILE_1		:= ${INPUT_DIR}/TEST_01_Print_Primes.txt
TEST_FILE_1_OUTPUT	:= ${OUTPUT_DIR}/TEST_01_Print_Primes_Output.txt
TEST_FILE_1_EXPECTED	:= ${EXPECTED_OUTPUT_DIR}/TEST_01_Print_Primes_Expected_Output.txt
TEST_FILE_2		:= ${INPUT_DIR}/TEST_02_Bubble_Sort.txt
TEST_FILE_2_OUTPUT	:= ${OUTPUT_DIR}/TEST_02_Bubble_Sort_Output.txt
TEST_FILE_2_EXPECTED	:= ${EXPECTED_OUTPUT_DIR}/TEST_02_Bubble_Sort_Expected_Output.txt
TEST_FILE_3		:= ${INPUT_DIR}/TEST_03_Merge_Lists.txt
TEST_FILE_3_OUTPUT	:= ${OUTPUT_DIR}/TEST_03_Merge_Lists_Output.txt
TEST_FILE_3_EXPECTED	:= ${EXPECTED_OUTPUT_DIR}/TEST_03_Merge_Lists_Expected_Output.txt

.PHONEY: clean test

all: clean
	jflex -q -d ${JFlex_DEST_DIR} ${JFlex_FILE}
	javac -cp ${EXTERNAL_JAR_FILES} -d ${BIN_DIR} ${SRC_FILES}
	jar cfm LEXER ${MANIFEST_FILE} -C ${BIN_DIR} .
#	java -jar LEXER ${INPUT} ${OUTPUT}

clean:
	rm -rf LEXER ${JFlex_GENERATED_FILE} ${CLASS_FILES}

test:
	java -jar LEXER ${TEST_FILE_1} ${TEST_FILE_1_OUTPUT} >/dev/null
	diff ${TEST_FILE_1_OUTPUT} ${TEST_FILE_1_EXPECTED}
	java -jar LEXER ${TEST_FILE_2} ${TEST_FILE_2_OUTPUT} >/dev/null
	diff ${TEST_FILE_2_OUTPUT} ${TEST_FILE_2_EXPECTED}
	java -jar LEXER ${TEST_FILE_3} ${TEST_FILE_3_OUTPUT} >/dev/null
	diff ${TEST_FILE_3_OUTPUT} ${TEST_FILE_3_EXPECTED}
