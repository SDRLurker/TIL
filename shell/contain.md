출처 : [https://stackoverflow.com/questions/229551/how-to-check-if-a-string-contains-a-substring-in-bash](https://stackoverflow.com/questions/229551/how-to-check-if-a-string-contains-a-substring-in-bash)

# Bash에서 문자열이 부분 문자열을 포함하는지 확인하는 방법

저는 Bash에서 하나의 문자열을 가지고 있습니다.

```shell
string="My string"
```

저는 이 문자열이 다른 문자열을 포함하는지 어떻게 테스트할 수 있을까요?

```shell
if [ $string ?? 'foo' ]; then
  echo "It's there!"
fi
```

어디에 제가 모르는 연산자 `??`가 있나요? 저는 어떻게 `echo`와 `grep`을 사용할 수 있을까요?

```shell
if echo "$string" | grep 'foo'; then
  echo "It's there!"
fi
```

저는 이 부분에 약간 서투른 것 같습니다.


## 28개의 답변 중 1개의 답변

당신은 만약 이중 괄호를 사용한다면 case 구문 바깥에서 [Marcus의 답변(* 와일드 카드)](https://stackoverflow.com/questions/229551/how-to-check-if-a-string-contains-a-substring-in-bash/229585#229585) 를 사용할 수도 있습니다.

```shell
string='My long string'
if [[ $string == *"My long"* ]]; then
  echo "It's there!"
fi
```


문자열의 공백은 큰 따옴표로 묶어야 하며 `*` 와일드 카드는 큰 따옴표 바깥에 있어야 합니다. 또한 정규식 연산자 `=~`가 아닌 단순 비교 연산자(예: `==`)가 사용됩니다.
