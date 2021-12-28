**출처**

[https://stackoverflow.com/questions/24718697/pyspark-drop-rows](https://stackoverflow.com/questions/24718697/pyspark-drop-rows)

# PySpark에서 행 버리기

PySpark에서 RDD로부터 행을 어떻게 버릴 수 있을까요? 특별히 첫 번째 행에 제 데이터 셋에 컬럼명이 포함되어 있기 때문입니다. API를 자세히 살펴보면 이 작업을 쉽게 하는 방법을 찾을 수 없는 거 같습니다. 당연히 저는 Bash / HDFS를 통해 이를 할 수 있지만 PySpark로만 이를 할 수 있는 방법을 알고 싶습니다.

---

## 6개 답변 중 1개

제가 아는 한 이를 하는 '쉬운' 방법은 없습니다.

그래서 트릭을 수행해야 합니다.

```scala
val header = data.first
val rows = data.filter(line => line != header)
```
