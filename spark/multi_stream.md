출처 : [https://stackoverflow.com/questions/50791975/spark-structured-streaming-multiple-writestreams-to-same-sink](https://stackoverflow.com/questions/50791975/spark-structured-streaming-multiple-writestreams-to-same-sink)

# 같은 Sink로 여러개의 Spark Structured Streaming WriteStreams하기

Spark Structured Streaming 2.2.1에서 순서대로 같은 데이터베이스 sink로 두 개의 `Writestream` 하는 것이 안 됩니다. 이 2개의 Writestream이 순서대로 실행하는 방법을 제안해주세요.

```scala
val deleteSink = ds1.writestream
  .outputMode("update")
  .foreach(mydbsink)
  .start()

val UpsertSink = ds2.writestream
  .outputMode("update")
  .foreach(mydbsink)
  .start()

deleteSink.awaitTermination()
UpsertSink.awaitTermination()
```

위의 코드에서 사용한대로 `deleteSink`는 `UpsertSink` 뒤에 실행됩니다.

---

## 1개의 답변

만약 당신이 병렬로 두개의 stream을 실행하고 싶으시면 다음을 사용해야 합니다.

```scala
sparkSession.streams.awaitAnyTermination()
```

다음 것 대신에 말이지요.

```scala
deleteSink.awaitTermination()
UpsertSink.awaitTermination()
```

당신 코드의 UpsertSink의 경우 deleteSink가 멈추거나 exception이 발생하지 않으면 시작하지 않을 것입니다. scaladoc에 이런 내용이 나와 있습니다.

> exception 발생 또는 `query.stop()` 또는 `this` 쿼리의 종료를 기다립니다. exception과 함께 쿼리가 종료되면 exception이 발생될 것입니다. 만약 쿼리가 정상 종료되면,  이 메소드와 모든 후속 호출은 바로 리턴될 것입니다. (쿼리가 `stop()`에 의해 종료되면) exception이 바로 발생할 것입니다. (쿼리가 exception이 발생하여 종료했다면) 
