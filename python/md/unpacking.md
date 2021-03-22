출처 : [https://stackabuse.com/unpacking-in-python-beyond-parallel-assignment/](https://stackabuse.com/unpacking-in-python-beyond-parallel-assignment/)

# 파이썬으로 unpacking : 병렬 대입을 넘어서

## 소개

파이썬에서 unpacking은 하나의 대입 구문에서 변수들의 [튜플(tuple)](https://stackabuse.com/lists-vs-tuples-in-python/) (또는 `리스트(list)`)로 iterable한 할당을 포함하는 연산입니다. 반대로 packing 이라는 용어는 iterable unpacking 연산자 `*` 를 사용하여 단일 변수에서 여러 값을 수집할 때 사용할 수 있습니다.

역사적으로, 파이썬 개발자는 *튜플(tuple) unpacking* 같은 연산 종류를 일반적으로 참조해 왔습니다. 하지만, 파이썬 기능은 매우 유용하고 인기있는 것으로 밝여졌기 때문에 모든 종류의 iterable 들로 일반화되었습니다. 요즘에는 더 현대적이고 정확한 용어는 *iterable unpacking* 입니다.

이 튜토리얼에서 우리는 iterable unpacking이 무엇이고 우리의 코드를 가독성 있고 유지보수에 좋고 pythonic 하도록 하는 파이썬 특징에 어떻게 이득을 주는지 학습 하겠습니다.

추가적으로, 우리는 대입 연산, `for` 루프, 함수 정의와 함수 호출에서 iterable unpacking 특징을 사용하는 방법에 대한 몇 가지 실용적인 예시를 다룰 것입니다.

## 파이썬에서 packing과 unpacking

파이썬은 변수들의 `튜플(tuple)` ( 또는 `리스트(list)` ) 대입 연산 왼쪽에 나오는 것을 허용합니다. `튜플(tuple)`의 각 변수는 오른쪽 부분에 iterable로부터 하나의 값 ( 또는 만약 `*` 연산자를 사용할 경우 그 이상)을 받을 수 있습니다.

역사적인 이유로 파이썬 개발자는 이를 _tuple unpacking_ 이라 부르고는 했습니다. 하지만 이 특징은 모든 종류의 iterable로 일반화되고 부터 더 정확한 용어는 _iterable unpacking_이 되었고 이 튜토리얼에서 그렇게 부르는 것입니다.

Unpacking 연산은 우리의 코드를 더 가독성있고 우아하게 해주기 때문에 파이썬 개발자 사이에서 아주 유명해졌습니다. 파이썬에서 unpacking에 대해 더 살펴보고 이 특징이 우리의 코드를 어떻게 증진시키는지 살펴봅시다.
