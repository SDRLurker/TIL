출처 : [http://stackoverflow.com/questions/279561/what-is-the-python-equivalent-of-static-variables-inside-a-function](http://stackoverflow.com/questions/279561/what-is-the-python-equivalent-of-static-variables-inside-a-function)

# Python에서 함수 안의 static 변수와 같은 것은 무엇입니까?

이 C/C++ 코드와 Python에서 같은 것은 무엇입니까?

```c
void foo()
{
    static int counter = 0;
    counter++;
    printf("counter is %d\n", counter);
}
```

특별히, 클래스 레벨과는 다르게 함수 레벨에서 어떻게 static 멤버를 구현할 수 있습니까? 함수를 클래스로 넣는 것을 다른 걸로 바꿀 수 있습니까?

---

## 18 개의 답변 중 1 개의 답변만 추려냄.

당신은 함수에 속성(attribute)을 추가할 수 있습니다. 그것을 static 변수로 사용하면 됩니다.

```python
def myfunc():
  myfunc.counter += 1
  print myfunc.counter

# 속성은 반드시 초기화 되어야 합니다.
myfunc.counter = 0
```

다른 대안으로 함수 밖에 변수를 설정하길 원하지 않는다면, 당신은 AttributeError exception을 피하기 위해 hasattr()를 사용할 수 있습니다.

```python
def myfunc():
  if not hasattr(myfunc, "counter"):
     myfunc.counter = 0  # it doesn't exist yet, so initialize it
  myfunc.counter += 1
```

static 변수는 드물게 사용되기 때문에 당신은 이 변수를 사용하기 더 좋은 위치를 찾는게 좋고 대부분은 클래스 안에 멤버변수로 사용합니다.
