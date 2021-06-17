출처 : [http://stackoverflow.com/questions/7515266/rounding-problem-with-python](http://stackoverflow.com/questions/7515266/rounding-problem-with-python)

# 파이썬에서 반올림 문제

파이썬에서 반올림 문제가 있습니다. 제가 다음을 계산하면

32.50 * 0.19 = 6.1749999999999998

가 됩니다. 하지만 이는 6.175가 되어야 합니다. 만약 둘째 자리로 6.1749999999999998을 반올림하면 정확히 6.18을 보여줍니다. 여기까지는 괜찮습니다.

하지만 이를 계산하면

32.50 * 0.19 * 3 = 18.524999999999999

가 됩니다. 이는 18.525가 되어야 합니다. 만약 둘째 자리로 18.524999999999999을 반올림하면 18.52를 보여주어야 합니다.

이 값은 저에게 18.53을 보여주어야 합니다. 제가 무엇을 잘못했는지와 어떻게 이 현상을 고칠 수 있을까요?

----

## 7 개의 답변 중 2 개의 답변만 추려냄.

당신은 잘못한 게 없고, [파이썬의 잘못도 아닙니다.](https://docs.python.org/3/tutorial/floatingpoint.html) 몇개의 십진 숫자들은 이진 부동형으로 정확하게 표현을 못할 뿐입니다.

1/3을 십진수로 (0.3333....) 정확히 쓸 수 없는 거처럼, 0.1을 이진수로는(0.000110011...)을 정확히 쓸 수 없습니다.

### 해결책 A:

`print 32.5 * 0.19`를 사용합니다. 이는 결과를 자동으로 반올림 합니다.

### 해결책 B:

예를 들어 통화 값을 계산할 때처럼 정확한 값이 필요하면 [Decimal](https://docs.python.org/3/library/decimal.html)모듈을 사용합니다. 

```python
import decimal
D=decimal.Decimal

x=D('32.50')*D('0.19')
print(x)
# 6.1750
print(x.quantize(D('0.01'),rounding=decimal.ROUND_HALF_UP))
# 6.18

y=D('32.50')*D('0.19')*D('3')
print(y)
# 18.5250
print(y.quantize(D('0.01'),rounding=decimal.ROUND_HALF_UP))
# 18.53
```

### 해결책 C:

interactive session에서 결과를 자동으로 반올림하는 Python 3.2나 Python 2.7을 사용합니다.

```python
Python 2.7.2 (default, Jun 12 2011, 14:24:46) [MSC v.1500 64 bit (AMD64)] on win32
Type "help", "copyright", "credits" or "license" for more information.
>>> 32.50 * 0.19
6.175
```

### 추가 내용

[모든 컴퓨터 과학자들이 알아야 할 부동 소수점 계산에 관한 것.](https://docs.oracle.com/cd/E19957-01/806-3568/ncg_goldberg.html)

Python의 rounding mode(추후 조사 필요함.)
