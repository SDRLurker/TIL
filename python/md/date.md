**출처**

[https://stackoverflow.com/questions/3743222/how-do-i-convert-a-datetime-to-date](https://stackoverflow.com/questions/3743222/how-do-i-convert-a-datetime-to-date)

# datetime을 date로 변환하는 방법?

어떻게 Python에서 `datetime.datetime` 객체(예 `datetime.datetime.now()` 의 리턴 값)를 `datetime.date` 객체로 변환할 수 있을까요?

---

## 9 개의 답변 중 1 개의 답변

[`date()`](https://docs.python.org/ko/3/library/datetime.html#datetime.datetime.date) 메소드를 사용하세요.

```python
datetime.datetime.now().date()
```
