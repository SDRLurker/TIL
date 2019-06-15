출처 - https://stackoverflow.com/questions/27387786/reload-a-module-in-python-3-4

# Python 3.4에서 모듈 다시 불러오기

## 질문

## 답변

imp 모듈은 importlib 모듈을 위해 Python 3.4에서 더 이상 사용되지 않습니다. imp 모듈에 대한 문서에서 :

버전 3.4부터는 더 이상 사용되지 않습니다 : imp 패키지는 importlib에 찬성하여 지원 중단 될 예정입니다.

그래서 거기에서 reload 함수를 사용해야합니다.

```python
>>> import importlib
>>> importlib.reload
<function reload at 0x01BA4030>
>>> importlib.reload (the_module)
```
