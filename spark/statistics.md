# 기본 통계 - RDD 기반 API
* 요약 통계
* 상관 관계
* 계층화된 샘플링
* 가설 검증
  * 스트리밍 의미 테스트
* 무작위 데이터 생성
* 커널 밀도 측정

# 요약 통계

우리는 **Statistics**에서 가능한 함수 **colStats**를 통해 열에 대한 통계를 제공합니다.

## Scala

[colStats()](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.mllib.stat.Statistics$) 은 최대값, 최소값, 평균, 표준 편차, 0이 아닌 데이터 개수 뿐만 아니라 전체 개수를 포함하는 [MultivariateStatisticalSummary](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.mllib.stat.MultivariateStatisticalSummary)의 인스턴스를 리턴합니다.
API에서 더 자세한 내용 [MultivariateStatisticalSummary Scala docs](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.mllib.stat.MultivariateStatisticalSummary) 을 참조하세요.

```scala
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}

val observations = sc.parallelize(
  Seq(
    Vectors.dense(1.0, 10.0, 100.0),
    Vectors.dense(2.0, 20.0, 200.0),
    Vectors.dense(3.0, 30.0, 300.0)
  )
)

// 요약 통계에 대해 계산합니다.
val summary: MultivariateStatisticalSummary = Statistics.colStats(observations)
println(summary.mean)  // 각 컬럼의 평균 값을 포함한 벡터
println(summary.variance)  // 컬럼 단위의 분산
println(summary.numNonzeros)  // 각 컬럼의 0이 아닌 데이터의 개수
```

## Java

[colStats()](https://spark.apache.org/docs/latest/api/java/org/apache/spark/mllib/stat/Statistics.html) 은 최대값, 최소값, 평균, 표준 편차, 0이 아닌 데이터 개수 뿐만 아니라 전체 개수를 포함하는 [MultivariateStatisticalSummary](https://spark.apache.org/docs/latest/api/java/org/apache/spark/mllib/stat/MultivariateStatisticalSummary.html)의 인스턴스를 리턴합니다.
API에서 더 자세한 내용 [MultivariateStatisticalSummary Java docs](https://spark.apache.org/docs/latest/api/java/org/apache/spark/mllib/stat/MultivariateStatisticalSummary.html) 을 참조하세요.

```java
import java.util.Arrays;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;

JavaRDD<Vector> mat = jsc.parallelize(
  Arrays.asList(
    Vectors.dense(1.0, 10.0, 100.0),
    Vectors.dense(2.0, 20.0, 200.0),
    Vectors.dense(3.0, 30.0, 300.0)
  )
); // 벡터의 RDD

// 요약 통계에 대해 계산합니다.
MultivariateStatisticalSummary summary = Statistics.colStats(mat.rdd());
System.out.println(summary.mean());  // 각 컬럼의 평균 값을 포함한 벡터
System.out.println(summary.variance());  // 컬럼 단위의 분산
System.out.println(summary.numNonzeros());  // 각 컬럼의 0이 아닌 데이터의 개수
```

## Python

[colStats()](https://spark.apache.org/docs/latest/api/python/pyspark.mllib.html#pyspark.mllib.stat.Statistics.colStats) 은 최대값, 최소값, 평균, 표준 편차, 0이 아닌 데이터 개수 뿐만 아니라 전체 개수를 포함하는 [MultivariateStatisticalSummary](https://spark.apache.org/docs/latest/api/python/pyspark.mllib.html#pyspark.mllib.stat.MultivariateStatisticalSummary)의 인스턴스를 리턴합니다.
API에서 더 자세한 내용 [MultivariateStatisticalSummary Python docs](https://spark.apache.org/docs/latest/api/python/pyspark.mllib.html#pyspark.mllib.stat.MultivariateStatisticalSummary) 을 참조하세요.

```python
import numpy as np

from pyspark.mllib.stat import Statistics

mat = sc.parallelize(
    [np.array([1.0, 10.0, 100.0]), np.array([2.0, 20.0, 200.0]), np.array([3.0, 30.0, 300.0])]
)  # 벡터의 RDD

# 요약 통계에 대해 계산합니다.
summary = Statistics.colStats(mat)
print(summary.mean())  # 각 컬럼의 평균 값을 포함한 벡터
print(summary.variance())  # 컬럼 단위의 분산
print(summary.numNonzeros())  # 각 컬럼의 0이 아닌 데이터의 개수
```

# 상관 관계

# 계층화된 샘플링

spark.mllib에 있는 다른 통계 함수와 달리 계층화된 샘플링 방법인 **sampleByKey** 및 **sampleByKeyExact**는 키 - 값 쌍의 RDD에서 수행할 수 있습니다. 계층화된 샘플링의 경우 키는 레이블로 값을 특정 속성으로 간주 할 수 있습니다. 예를 들어 키는 남성이나 여성이 될 수 있고 값은 각 사람의 나이에 대한 목록이 될 수 있습니다. 또는 키는 문서 ID 값은 단어의 목록이 될 수 있습니다. **sampleByKey** 메서드는 데이터를 한 번씩 지나가면서 해당 값이 샘플링 될 것인지 결정하기 위해 키에 대한 예상 샘플링 비율(Map(키,샘플링비율))을 제공합니다. **sampleByKeyExact**는 **sampleByKey**에 사용 된 계층 별 단순 무작위 샘플링보다 많은 리소스가 필요하지만 99.99 % 신뢰도로 정확한 샘플링 크기를 제공합니다. **sampleByKeyExact**는 현재 python에서 지원되지 않습니다.

## Scala

[sampleByKeyExact()](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.PairRDDFunctions) 는 사용자가 정확하게 ⌈fk⋅nk⌉∀k∈K 개의 항목을 샘플링 할 수있게합니다. 여기서 fk는 키 k의 원하는 비율(분수), nk는 키 k의 키 - 값 쌍의 개수, K는 키의 집합입니다. 비복원추출을 하면 RDD를 원하는 샘플 개수를 보장하기 위해 한 번 더 통과해야 하며, 복원 추출을 하면 2번 더 통과해야 합니다.

```scala
// 키 - 값 쌍의 RDD[(K, V)]
val data = sc.parallelize(
  Seq((1, 'a'), (1, 'b'), (2, 'c'), (2, 'd'), (2, 'e'), (3, 'f')))

// 각 키로부터 원하는 비율(분수)을 지정함
val fractions = Map(1 -> 0.1, 2 -> 0.6, 3 -> 0.3)

// 각 값으로부터 대략적인 샘플을 추출하여 얻음
val approxSample = data.sampleByKey(withReplacement = false, fractions = fractions)
// 각 값으로부터 정확한 샘플을 추출하여 얻음
val exactSample = data.sampleByKeyExact(withReplacement = false, fractions = fractions)
```

## Java

[sampleByKeyExact()](https://spark.apache.org/docs/latest/api/java/org/apache/spark/api/java/JavaPairRDD.html) 는 사용자가 정확하게 ⌈fk⋅nk⌉∀k∈K 개의 항목을 샘플링 할 수있게합니다. 여기서 fk는 키 k의 원하는 비율(분수), nk는 키 k의 키 - 값 쌍의 개수, K는 키의 집합입니다. 비복원추출을 하면 RDD를 원하는 샘플 개수를 보장하기 위해 한 번 더 통과해야 하며, 복원 추출을 하면 2번 더 통과해야 합니다.

```java
import java.util.*;

import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;

List<Tuple2<Integer, Character>> list = Arrays.asList(
    new Tuple2<>(1, 'a'),
    new Tuple2<>(1, 'b'),
    new Tuple2<>(2, 'c'),
    new Tuple2<>(2, 'd'),
    new Tuple2<>(2, 'e'),
    new Tuple2<>(3, 'f')
);

JavaPairRDD<Integer, Character> data = jsc.parallelizePairs(list);

// 각 키 Map<K, Double>로부터 원하는 비율(분수) 지정함 
ImmutableMap<Integer, Double> fractions = ImmutableMap.of(1, 0.1, 2, 0.6, 3, 0.3);

// 각 값으로부터 대략적인 샘플을 추출하여 얻음
JavaPairRDD<Integer, Character> approxSample = data.sampleByKey(false, fractions);
// 각 값으로부터 정확한 샘플을 추출하여 얻음
JavaPairRDD<Integer, Character> exactSample = data.sampleByKeyExact(false, fractions);
```

## Python

[sampleByKey()](https://spark.apache.org/docs/latest/api/python/pyspark.html#pyspark.RDD.sampleByKey) 는 사용자가 대략적으로 ⌈fk⋅nk⌉∀k∈K 개의 항목을 샘플링 할 수있게합니다. 여기서 fk는 키 k의 원하는 비율(분수), nk는 키 k의 키 - 값 쌍의 개수, K는 키의 집합입니다.

참고: sampleByKeyExact()는 Python에서 현재 지원하지 않습니다.

```python
# 키 - 값 쌍의 RDD
data = sc.parallelize([(1, 'a'), (1, 'b'), (2, 'c'), (2, 'd'), (2, 'e'), (3, 'f')])

# dictionary로 각 키에 원하는 비율(분수)을 지정함
fractions = {1: 0.1, 2: 0.6, 3: 0.3}

approxSample = data.sampleByKey(False, fractions)
```
