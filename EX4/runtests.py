from pathlib import Path
from pprint import pprint
import subprocess
import difflib


INPUT_FOLDER = 'FOLDER_4_INPUT'
OUTPUT_FOLDER = 'FOLDER_5_OUTPUT'
EXPECTED_FOLDER = 'FOLDER_6_EXPECTED_OUTPUT'


subprocess.run(['make'])

for path in sorted(Path(INPUT_FOLDER).glob('*.txt')):

    n = path.name

    output_file = Path(OUTPUT_FOLDER) / path.name
    expected_file = Path(EXPECTED_FOLDER) / path.name
    print(path.name)

    result = subprocess.run(['java', '-jar', 'COMPILER', path, output_file], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    result = subprocess.run(['spim', '-f', output_file], stdout=subprocess.PIPE, stderr=subprocess.PIPE)

    output_content = result.stdout.decode().rstrip()
    expected_content = expected_file.open().read().rstrip()

    if output_content != expected_content:
        print(output_content)
        break
