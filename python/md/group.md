출처  
* [https://stackoverflow.com/questions/2249036/grouping-python-tuple-list](https://stackoverflow.com/questions/2249036/grouping-python-tuple-list)

## Python 튜플 리스트 그룹핑

(라벨, 개수) 튜플의 리스트가 다음처럼 있습니다.

```python
[('grape', 100), ('grape', 3), ('apple', 15), ('apple', 10), ('apple', 4), ('banana', 3)]
```

이로부터 저는 같은 라벨(같은 라벨은 항상 연속됨)로 모든 개수값들을 합계를 내고 같은 라벨 순서로 리스트를 리턴하고 싶습니다.

```python
[('grape', 103), ('apple', 29), ('banana', 3)]
```

저는 다음처럼 뭔가 해결은 할 수 있었습니다

```python
def group(l):
    result = []
    if l:
        this_label = l[0][0]
        this_count = 0
        for label, count in l:
            if label != this_label:
                result.append((this_label, this_count))
                this_label = label
                this_count = 0
            this_count += count
        result.append((this_label, this_count))
    return result
```

하지만, 이것보다 더 Pythonic 하고 / 우아하고 / 효율적인 방법이 있을까요?

---

### 7개의 답변 중 2개의 답변

[`itertools.groupby`](https://docs.python.org/3.7/library/itertools.html#itertools.groupby) 는 당신이 원하는 것을 할 수 있습니다.

```python
import itertools
import operator

L = [('grape', 100), ('grape', 3), ('apple', 15), ('apple', 10),
     ('apple', 4), ('banana', 3)]

def accumulate(l):
    it = itertools.groupby(l, operator.itemgetter(0))
    for key, subiter in it:
       yield key, sum(item[1] for item in subiter) 

print(list(accumulate(L)))
# [('grape', 103), ('apple', 29), ('banana', 3)]
```

#### 댓글

- `lambda` 대신에 `operator.itemgetter`를 사용하는 것이 좋습니다. – [jathanism](https://stackoverflow.com/users/194311/jathanism) [Feb 12 '10 at 1:48](https://stackoverflow.com/questions/2249036/grouping-python-tuple-list#comment2207439_2249060)
    
- 이 방법은 첫번째 키로 정렬된 리스트가 필요합니다. 이미 정렬이 되지 않았다면, ghostdog74 님의 접근이 훨씬 좋은 해결책입니다. – [Martijn Pieters♦](https://stackoverflow.com/users/100297/martijn-pieters) [Oct 10 '16 at 21:05](https://stackoverflow.com/questions/2249036/grouping-python-tuple-list#comment67213367_2249060)
    

---

### ghostdog74 님의 답변

```python
import collections
d=collections.defaultdict(int)
a=[]
alist=[('grape', 100), ('banana', 3), ('apple', 10), ('apple', 4), ('grape', 3), ('apple', 15)]
for fruit,number in alist:
    if not fruit in a: a.append(fruit)
    d[fruit]+=number
for f in a:
    print (f,d[f])
```

결과

```shell
$ ./python.py
('grape', 103)
('banana', 3)
('apple', 29)
```
