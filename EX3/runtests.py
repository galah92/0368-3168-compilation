from pathlib import Path
import subprocess


subprocess.run(['make'])

for path in sorted(Path('FOLDER_4_INPUT').glob('TEST*.txt')):
    
    n = int(path.name[5:7])
    # if n < 15:
    #     continue

    output_file = Path('FOLDER_5_OUTPUT') / path.name
    expected_file = list(Path('FOLDER_6_EXPECTED_OUTPUT').glob(path.name[:7] + '*.txt'))[0]

    result = subprocess.run(['java', '-jar', 'COMPILER', path, output_file], stderr=subprocess.PIPE)
    
    output_content = output_file.open().readline().rstrip()
    expected_content = expected_file.open().readline().rstrip()
    print(f'{n}: Expected: {expected_content}\tGot: {output_content}')

    if output_content != expected_content:
        print(result.stderr.decode())
        break

