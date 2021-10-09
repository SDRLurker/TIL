출처 : [https://stackoverflow.com/questions/26835477/pickle-load-variable-if-exists-or-create-and-save-it](https://stackoverflow.com/questions/26835477/pickle-load-variable-if-exists-or-create-and-save-it)

# 피클(Pickle) - 만약 존재하면 불러오고 그렇지 않으면 생성하여 저장하기

이미 존재하면 불러오고 그렇지 않으면 생성하여 덤프하여 `pickle`로 변수를 불어오는 더 좋은 방법이 있을까요?

```python
if os.path.isfile("var.pickle"):
    foo = pickle.load( open( "var.pickle", "rb" ) )
else:
    foo = 3
    pickle.dump( foo, open( "var.pickle", "wb" ) )
```

---

## 2개 중 1개의 답변

당신은 [용서를 구하면서](https://stackoverflow.com/questions/12265451/ask-forgiveness-not-permission-explain) [`EAFP` 원리](https://docs.python.org/ko/2/glossary.html#term-eafp)를 따를 수 있습니다.

```python
import pickle

try:
    foo = pickle.load(open("var.pickle", "rb"))
except (OSError, IOError) as e:
    foo = 3
    pickle.dump(foo, open("var.pickle", "wb"))
```
