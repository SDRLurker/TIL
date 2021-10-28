출처 

* [https://stackoverflow.com/questions/34529953/why-is-the-fold-action-necessary-in-spark](https://stackoverflow.com/questions/34529953/why-is-the-fold-action-necessary-in-spark)

# 스파크에서 fold action이 왜 필요합니까?

저는 PySpark에서 `fold`와 reduce에 관한 질문이 있습니다. 이 2개의 메소드의 차이점은 알고 있습니다. 하지만, 둘 다 적용된 함수끼리 교환하여 사용 가능하고 저는 `fold가 reduce로 대체될 수 없다`는 예시를 알 수 없습니다.

게다가, `fold` 구현에서 `acc = op(obj, acc)`가 사용됩니다. 왜 `acc = op(acc, obj)` 대신에 앞의 연산의 순서가 사용됩니까? (이 두번째 순서는 저에겐 `leftFold`에 가깝다고 이해됩니다)

토마스가

------

## 1개의 답변

### 빈 RDD

`RDD`가 비었을 때 그것은 대체될 수 없습니다.

```scala
val rdd = sc.emptyRDD[Int]
rdd.reduce(_ + _)
// java.lang.UnsupportedOperationException: empty collection at   
// org.apache.spark.rdd.RDD$$anonfun$reduce$1$$anonfun$apply$ ...

rdd.fold(0)(_ + _)
// Int = 0
```

당신은 당연히 `isEmpty`조건과 함께 `reduce`를 결합하여 사용할 수 있지만 코드는 더 추해집니다.

### 변경가능한(Mutable) 버퍼

다른 사용 방법은 변경가능(mutable)한 버퍼에 누적하는 것입니다. 다음 RDD가 있다고 생각합시다.

```scala
import breeze.linalg.DenseVector

val rdd = sc.parallelize(Array.fill(100)(DenseVector(1)), 8)
```

모든 요소의 합계를 원한다고 합시다. 소박한 해결책은 `+`와 함께 하는 겁니다.

```scala
rdd.reduce(_ + _)
```

불행히도 이는 각 요소에 대한 새로운 벡터를 생성합니다. 객체 생성과 계속되는 garbage collection 때문에 비용이 많이 들며 변경가능한(Mutable) 객체를 사용하는 것이 더 좋습니다. 이는 `reduce`로는 불가능하지만 (모든 요소의 변경불가능성을 내포하지는 않습니다.) 다음처럼 `fold`로는 이룰 수 있습니다.

```scala
rdd.fold(DenseVector(0))((acc, x) => acc += x)
```

Zero 요소는 실제 데이터를 변경하지 않고 하나의 파티션 당 버퍼를 초기화 함으로서 여기서 사용될 수 있습니다.

> 이것이 acc = op(acc, obj) 대신에 acc = op(obj, acc) 연산 순서를 사용하는 이유입니다. 

[SPARK-6416](https://issues.apache.org/jira/browse/SPARK-6416) 와 [SPARK-7683](https://issues.apache.org/jira/browse/SPARK-7683) 내용도 확인해주세요.

