출처 : [https://stackabuse.com/unpacking-in-python-beyond-parallel-assignment/](https://stackabuse.com/unpacking-in-python-beyond-parallel-assignment/)

# 파이썬으로 unpacking : 병렬 대입을 넘어서

## 소개

파이썬에서 unpacking은 하나의 대입 구문에서 변수들의 [튜플(tuple)](https://stackabuse.com/lists-vs-tuples-in-python/) (또는 `리스트(list)`)로 iterable한 할당을 포함하는 연산입니다. 반대로 packing 이라는 용어는 iterable unpacking 연산자 `*` 를 사용하여 단일 변수에서 여러 값을 수집할 때 사용할 수 있습니다.

역사적으로, 파이썬 개발자는 *튜플(tuple) unpacking* 같은 연산 종류를 일반적으로 참조해 왔습니다. 하지만, 파이썬 기능은 매우 유용하고 인기있는 것으로 밝여졌기 때문에 모든 종류의 iterable 들로 일반화되었습니다. 요즘에는 더 현대적이고 정확한 용어는 *iterable unpacking* 입니다.

이 튜토리얼에서 우리는 iterable unpacking이 무엇이고 우리의 코드를 가독성 있고 유지보수에 좋고 pythonic 하도록 하는 파이썬 특징에 어떻게 이득을 주는지 학습 하겠습니다.

추가적으로, 우리는 대입 연산, `for` 루프, 함수 정의와 함수 호출에서 iterable unpacking 특징을 사용하는 방법에 대한 몇 가지 실용적인 예시를 다룰 것입니다.
