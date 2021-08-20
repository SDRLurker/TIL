출처 : [https://stackoverflow.com/questions/893657/how-do-i-calculate-r-squared-using-python-and-numpy](https://stackoverflow.com/questions/893657/how-do-i-calculate-r-squared-using-python-and-numpy)

# Python과 Numpy를 사용하여 r-제곱을 계산하는 방법?

Python과 Numpy를 사용하여 임의의 차수에 가장 적합한 다항식을 계산합니다. x값, y값 및 내가 맞추려는 다항식의 차수(선형, 2차 등) 목록을 전달합니다. 

이것은 많은 효과가 있지만 r(상관 계수)과 r-제곱(결정 계수)도 계산하고 싶습니다. Excel의 최적 추세선 기능 및 제가 계산하는 r-제곱 값 결과와 비교하고 있습니다. 이것을 사용하여 선형 최적 맞춤(차수는 1과 같음)에 대해 r-제곱을 올바르게 계산하고 있다는 것을 알고 있습니다. 그러나 내 함수는 차수가 1보다 큰 다항식에서는 작동하지 않습니다. 

엑셀로 이를 할 수 있습니다. Numpy를 사용하여 고차 다항식에 대한 r-제곱을 어떻게 계산합니까?

제가 만든 함수는 다음과 같습니다.

```python
import numpy

# 다항 회귀
def polyfit(x, y, degree):
    results = {}

    coeffs = numpy.polyfit(x, y, degree)
     # 다항 상관 계수 
    results['polynomial'] = coeffs.tolist()

    correlation = numpy.corrcoef(x, y)[0,1]

     # r
    results['correlation'] = correlation
     # r-제곱
    results['determination'] = correlation**2

    return results
```

---

## 11개 중 1개의 답변

[numpy.polyfit](https://numpy.org/doc/stable/reference/generated/numpy.polyfit.html) 문서로부터 이는 선형 회귀에 적합(fit) 합니다. 특히, 차수가 'd'인 numpy.polyfit은 평균 함수를 사용하여 선형 회귀에 맞춥니다. 

따라서 해당 회귀에 적합(fit)하도록 R-제곱을 계산하기만 하면 됩니다. [선형 회귀](https://en.wikipedia.org/wiki/Linear_regression)에 대한 wikipedia 페이지는 자세한 내용을 제공합니다. 당신은 몇 가지 방법으로 계산할 수 있는 R^2에 관심이 있습니다. 가장 쉬운 것은 아마도

```
SST = Sum(i=1..n) (y_i - y_bar)^2
SSReg = Sum(i=1..n) (y_ihat - y_bar)^2
Rsquared = SSReg/SST
```

여기서 y의 평균으로 'y_bar'를 사용하고 각 점에 대한 맞춤(fit) 값으로 'y_ihat'을 사용합니다.

저는 numpy에 익숙하지 않습니다(저는 일반적으로 R에서 작업합니다). 따라서 R-제곱을 계산하는 더 깔끔한 방법이 있을 수 있지만 다음 방법은 정확할 것입니다.

```python
import numpy

# 다항 회귀
def polyfit(x, y, degree):
    results = {}

    coeffs = numpy.polyfit(x, y, degree)

     # 다항 상관 계수 
    results['polynomial'] = coeffs.tolist()

    # r-제곱
    p = numpy.poly1d(coeffs)
    # 적합(fit) 값들과 평균 
    yhat = p(x)                         # or [p(z) for z in x]
    ybar = numpy.sum(y)/len(y)          # or sum(y)/len(y)
    ssreg = numpy.sum((yhat-ybar)**2)   # or sum([ (yihat - ybar)**2 for yihat in yhat])
    sstot = numpy.sum((y - ybar)**2)    # or sum([ (yi - ybar)**2 for yi in y])
    results['determination'] = ssreg / sstot

    return results
```
