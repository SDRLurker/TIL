참고주소 : [https://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work](https://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work)

# 어떻게 자바의 'for each' 루프가 작동하나요?

다음을 고려할 때

```java
List<String> someList = new ArrayList<String>();
// add "monkey", "donkey", "skeleton key" to someList
```

```java
for (String item : someList) {
    System.out.println(item);
}
```

*for each* 문법을 사용하지 않고 `for` 루프처럼 똑같이 하려면 어떻게 해야할까요?

---

## 28개 답변 중 1개만 추려냄

```java
for (Iterator<String> i = someIterable.iterator(); i.hasNext();) {
    String item = i.next();
    System.out.println(item);
}
```

루프에서 `i.remove();`를 사용할 필요가 있거나, 다른 방법으로 실제 iterator를 접근(수정)한다면 구문을 실제 iterator만 추론(읽기) 때문에 사용할 수 없습니다.

Denis Bueno가 언급했듯이 이 코드는 [`Iterable` 인터페이스](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html)를 구현하는 모든 개체에서 작동합니다.

또한 `for (:)` 관용구의 오른쪽이 `Iterable` 객체가 아닌 배열인 경우 내부 코드는 int 인덱스 카운터를 사용하고 대신 `array.length`를 확인합니다. [Java 언어 스펙](https://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work)을 참조하십시오.

