출처 : [http://chrisalbon.com/python/pandas_create_column_with_loop.html](http://chrisalbon.com/python/pandas_create_column_with_loop.html)

# For 루프로 Pandas 열 만들기

## 사전준비

```python
import pandas as pd
import numpy as np
```

## 데이터프레임 예시 만들기

```python
raw_data = {'student_name': ['Miller', 'Jacobson', 'Ali', 'Milner', 'Cooze', 'Jacon', 'Ryaner', 'Sone', 'Sloan', 'Piger', 'Riani', 'Ali'], 
        'test_score': [76, 88, 84, 67, 53, 96, 64, 91, 77, 73, 52, np.NaN]}
df = pd.DataFrame(raw_data, columns = ['student_name', 'test_score'])
```

## 학점을 할당하기 위한 함수 만들기

```python
# 데이터를 저장할 list(리스트)를 만듭니다.
grades = []

# 열에 추가할 각 행을 For로 순회합니다,
for row in df['test_score']:
    # 이 값보다 크면,
    if row > 95:
        # 'A' 학점으로 list에 추가합니다.
        grades.append('A')
    # 아니고, 이 값보다 크면,
    elif row > 90:
        # 'A-' 학점으로 list에 추가합니다.
        grades.append('A-')
    # 아니고, 이 값보다 크면,
    elif row > 85:
        # 'B' 학점으로 list에 추가합니다.
        grades.append('B')
    # 아니고, 이 값보다 크면,
    elif row > 80:
        # 'B-' 학점으로 list에 추가합니다.
        grades.append('B-')
    # 아니고, 이 값보다 크면,
    elif row > 75:
        # 'C' 학점으로 list에 추가합니다.
        grades.append('C')
    # 아니고, 이 값보다 크면,
    elif row > 70:
        # 'C-' 학점으로 list에 추가합니다.
        grades.append('C-')
    # 아니고, 이 값보다 크면,
    elif row > 65:
        # 'D' 학점으로 list에 추가합니다.
        grades.append('D')
    # 아니고, 이 값보다 크면,
    elif row > 60:
        # 'D-' 학점으로 list에 추가합니다.
        grades.append('D-')
    # 아니면,
    else:
        # 'F' 학점을 추가합니다.
        grades.append('Failed')

# list(리스트)로부터 'grades'열을 추가합니다.
df['grades'] = grades
```

```python
# 새로운 dataframe(데이터프레임)을 봅니다.
df
```

| |student_name|test_score|grades|
|-|-|-|-|
|0|Miller|76|C|
|1|Jacobson|88|B|
|2|Ali|84|B-|
|3|Milner|67|D|
|4|Cooze|53|Failed|
|5|Jacon|96|A|
|6|Ryaner|64|D-|
|7|Sone|91|A-|
|8|Sloan|77|C|
|9|Piger|73|C-|
|10|Riani|52|Failed|
|11|Ali|NaN|Failed|


