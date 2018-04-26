<b>출처</b>

https://stackoverflow.com/questions/13297207/is-it-valid-to-compare-a-double-with-an-int-in-java

### Java에서 double과 int를 비교하는 것이 유효합니까?

```
Utilities.getDistance(uni, enemyuni) <= uni.getAttackRange()
```

Utilities.getDistance는 double을 리턴하고 getAttackRange는 int를 리턴합니다. 위의 코드는 if 구문의 한 부분이고 이는 true가 되어야 합니다. 이 비교가 유효합니까?

감사합니다.

--

### 5개 답변중 1개의 답변만 추려냄.

예 유효합니다. 비교하기 전에 int를 double로 변환(promote)합니다.

[JLS 섹션 5.6.2 (이진 숫자 변환)]() 의 링크에 있는 [JLS 섹션 15.20.1 (숫자 비교 연산)](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.20.1) 를 확인해 보세요.

뒷부분을 발췌하면
```
primitive의 넓은 타입의 변환은 다음 규칙에 의해 정의된 대로 두 피연산자에 변환이 적용됩니다.
* 만약 한 피연산자 type이 double이면 다른 하나는 double로 변환됩니다.
* ...
```
