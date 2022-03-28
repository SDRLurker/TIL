출처 : [https://stackoverflow.com/questions/843277/how-do-i-check-if-a-variable-exists](https://stackoverflow.com/questions/843277/how-do-i-check-if-a-variable-exists)

# 어떻게 변수가 존재하는 지 체크하나요?

저는 변수가 존재하는 지 체크하기를 원합니다. 이제 저는 다음처럼 뭔가 하였습니다.

```python
try:
   myVar
except NameError:
   # 뭔가 하기
```

예외처리 없이 다른 방법이 있을까요?

---

## 14개의 답변 중 1개

지역 변수의 존재를 체크하려면

```python
if 'myVar' in locals():
  # myVar 있음
```

전역 변수의 존재를 체크하려면

```python
if 'myVar' in globals():
  # myVar 있음
```

객체에 속성이 있는지 체크하려면

```python
if hasattr(obj, 'attr_name'):
  # 객체.속성이름(obj.attr_name) 있음
```
