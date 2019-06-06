
### 텀블링 윈도우
* 텀블링 윈도우 입력 데이터와 키 기준으로 단순한 합계를 구하는 과정 예시
![image](https://www.oreilly.com/library/view/spark-the-definitive/9781491912201/assets/spdg_2202.png)

* 이벤트 데이터를 shell에서 다음처럼 생성합니다.

```shell
window $ mkdir test
window $ cd test
window/test $ vi test.json
window/test $ cat test.json
{"EventTime":5,"v":1}
{"EventTime":15,"v":2}
{"EventTime":15,"v":5}
{"EventTime":25,"v":200}
```

* 다음처럼 spark-shell을 실행합니다.

```shell
window/test $ cd ..
window $ spark-shell
```

* spark에서 텀블링 윈도우 스트리밍을 만듭니다.

```scala
val static = spark.read.json("test/test.json")
val streaming = spark.readStream.schema(static.schema).json("test")
val withEventTime = streaming.selectExpr("*", "cast(EventTime as timestamp) as event_time")
```

* 다음은 스트리밍 실행 결과입니다. 위의 그림처럼 결과가 나오는 것을 확인할 수 있습니다.
  - 2:10~2:20 : 1970-01-01 00:00:05 Event: (k,1)
  - 2:20~2:30 : 1970-01-01 00:00:15 Event: (k,2), (k,5)
  - 2:30~2:40 : 1970-01-01 00:00:25 Event: (k,200)

```scala
val result = withEventTime.groupBy(window(col("event_time"), "10 seconds")).sum().writeStream.format("memory").queryName("t").outputMode("complete").start()
spark.sql("SELECT window.*,* FROM t ORDER BY window").show()
+-------------------+-------------------+--------------------+--------------+------+
|              start|                end|              window|sum(EventTime)|sum(v)|
+-------------------+-------------------+--------------------+--------------+------+
|1970-01-01 00:00:00|1970-01-01 00:00:10|[1970-01-01 00:00...|             5|     1|
|1970-01-01 00:00:10|1970-01-01 00:00:20|[1970-01-01 00:00...|            30|     7|
|1970-01-01 00:00:20|1970-01-01 00:00:30|[1970-01-01 00:00...|            25|   200|
+-------------------+-------------------+--------------------+--------------+------+
```

#### 슬라이딩 윈도우
* 윈도우를 시작 시각에서 분리하는 방법을 사용.
![image](https://www.oreilly.com/library/view/spark-the-definitive/9781491912201/assets/spdg_2203.png)

* 다음은 스트리밍 실행 결과입니다. 위의 그림처럼 결과가 나오는 것을 확인할 수 있습니다.
  - 2:10~2:20 : 1970-01-01 00:00:05 Event: (k,1)
  - 2:20~2:30 : 1970-01-01 00:00:15 Event: (k,2), (k,5)
  - 2:30~2:40 : 1970-01-01 00:00:25 Event: (k,200)

```scala
val result = withEventTime.groupBy(window(col("event_time"), "30 seconds", "10 seconds")).sum().writeStream.format("memory").queryName("t").outputMode("complete").start()
spark.sql("SELECT window.*,* FROM t ORDER BY window").show()
+-------------------+-------------------+--------------------+--------------+------+
|              start|                end|              window|sum(EventTime)|sum(v)|
+-------------------+-------------------+--------------------+--------------+------+
|1969-12-31 23:59:40|1970-01-01 00:00:10|[1969-12-31 23:59...|             5|     1|
|1969-12-31 23:59:50|1970-01-01 00:00:20|[1969-12-31 23:59...|            35|     8|
|1970-01-01 00:00:00|1970-01-01 00:00:30|[1970-01-01 00:00...|            60|   208|
|1970-01-01 00:00:10|1970-01-01 00:00:40|[1970-01-01 00:00...|            55|   207|
|1970-01-01 00:00:20|1970-01-01 00:00:50|[1970-01-01 00:00...|            25|   200|
+-------------------+-------------------+--------------------+--------------+------+
```
