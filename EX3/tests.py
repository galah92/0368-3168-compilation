from pathlib import Path
import subprocess


subprocess.run(['make'])
i = 0
for path in sorted(Path('tests/input').glob('*.txt')):
    output_file = Path('tests/output') / path.name
    expected_file = Path('tests/expected_output') / path.name
    result = subprocess.run(['java', '-jar', 'COMPILER', path, output_file], stderr=subprocess.PIPE)
    
    output_content = output_file.open().readline().rstrip()
    expected_content = expected_file.open().readline().rstrip()
    print(f"{i}: Expected: {expected_content}\tGot: {output_content}, {path.name}")
    if output_content != expected_content:
        print(result.stderr.decode())
        break
    i += 1    

