출처 : [https://stackoverflow.com/questions/34629313/how-to-measure-the-execution-time-of-a-query-on-spark](https://stackoverflow.com/questions/34629313/how-to-measure-the-execution-time-of-a-query-on-spark)

# Spark에서 쿼리의 실행 시간을 측정하는 방법

저는 Apache spark (Bluemix)에서 쿼리의 실행 시간을 측정할 필요가 있습니다. 제가 시도한 내용입니다.

```scala
import time

startTimeQuery = time.clock()
df = sqlContext.sql(query)
df.show()
endTimeQuery = time.clock()
runTimeQuery = endTimeQuery - startTimeQuery
```

좋은 방법입니까? 제가 얻은 시간은 테이블에서 보았을 때 상대적으로 너무 작은 거 같습니다.

---

## 9 개의 답변 중 1 개의 답변

spark-shell(Scala) 에서 이를 하기 위해서, `spark.time()`를 사용할 수 있습니다..

저의 다른 답변도 보실 수 있습니다 : [https://stackoverflow.com/questions/36389019/spark-query-execution-time/50289329#50289329](https://stackoverflow.com/questions/36389019/spark-query-execution-time/50289329#50289329)

```scala
df = sqlContext.sql(query)
spark.time(df.show())
```

출력은 다음처럼 나올 것입니다.

```scala
+----+----+
|col1|col2|
+----+----+
|val1|val2|
+----+----+
Time taken: xxx ms
```

관련 글 : [성능 문제 해결을 위한 Apache Spark 워크로드 메트릭 측정.](https://db-blog.web.cern.ch/blog/luca-canali/2017-03-measuring-apache-spark-workload-metrics-performance-troubleshooting)
