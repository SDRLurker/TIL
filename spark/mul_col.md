출처 : [https://stackoverflow.com/questions/37795488/partitioning-by-multiple-columns-in-spark-sql](https://stackoverflow.com/questions/37795488/partitioning-by-multiple-columns-in-spark-sql)

# Spark SQL에서 여러 개의 열로 파티셔닝(Partitioning)

Spark SQL의 윈도우 함수로 저는 데이타 쿼리를 실행하기 위해 여러 개의 열로 파티션을 해야 합니다. 다음처럼 해 보았습니다.

`val w = Window.partitionBy($"a").partitionBy($"b").rangeBetween(-100, 0)`

현재 테스트 환경(설정 작업 중)이 없지만 간단한 질문으로 현재 이것이 Spark SQL의 윈도우 기능의 일부로 지원됩니까, 아니면 작동하지 않습니까?

## 2 개의 답변 중 1 개의 답변

이것은 작동하지 않을 것입니다. 두 번째 `partitionBy`는 첫 번째 것을 덮어쓰기 할 것입니다. 두 개의 파티션 열은 같은 호출에서 지정되어야 합니다.

```scala
val w = Window.partitionBy($"a", $"b").rangeBetween(-100, 0)
```
