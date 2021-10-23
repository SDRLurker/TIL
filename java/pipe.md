출처 : [https://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character](https://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character)

# 파이프 글자로 문자열 분리하기("|")

저는 이 문자열을 값으로 분리할 수 없습니다.

`"Food 1 | Service 3 | Atmosphere 3 | Value for money 1 "`

저의 현재 코드입니다.

```java
String rat_values = "Food 1 | Service 3 | Atmosphere 3 | Value for money 1 ";
String[] value_split = rat_values.split("|");
```

### 출력

> \[, F, o, o, d, , 1, , |, , S, e, r, v, i, c, e, , 3, , |, , A, t, m, o, s, p, h, e, r, e, , 3, , |, , V, a, l, u, e, , f, o, r, , m, o, n, e, y, , 1, \]

### 기대하는 출력

> Food 1  
> Service 3  
> Atmosphere 3  
> Value for money 1

## 5개의 답변 중 1개의 답변

`|`는 정규식에서 메타글자입니다. 당신은 파이프를 escape 할 필요가 있습니다.

```java
String[] value_split = rat_values.split("\\|");
```
