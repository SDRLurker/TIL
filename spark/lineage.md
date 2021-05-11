**출처**

[https://data-flair.training/blogs/rdd-lineage/](https://data-flair.training/blogs/rdd-lineage/)

# Spark에서 RDD 계보 정보(리니지): ToDebugString 메소드

## 1\. 목적

기본적으로 [Spark](https://data-flair.training/blogs/what-is-spark/)에서는 실제 데이터에도 RDD 간의 모든 종속성이 그래프에 기록됩니다. 이것이 우리가 Spark에서 계보 그래프라고 부르는 것입니다. 이 문서는 Spark 논리적 실행 계획의 RDD 계보 개념을 담고 있습니다. 또한 toDebugString 메서드로 RDD 계보 정보 그래프를 얻는 방법을 자세히 알게 될 것입니다. 먼저 Spark RDD에 대해서도 알아 보겠습니다.

## 2\. Spark RDD 소개

Spark RDD는 "Resilient Distributed Dataset"의 약어 입니다. RDD를 Apache Spark의 기본 데이터 구조로 간주할 수 있습니다. 구체적으로 말하면 RDD는 Apache Spark의 변경 불가능한 개체 모음입니다. 이는 클러스터의 다른 노드에서 계산하는 데 도움이 됩니다.  
Spark RDD의 이름을 분해 할 때 :

-   **탄력성**

이것은 내결함성을 의미합니다. RDD 계보 정보 그래프(DAG)를 사용하여 노드 장애로 인해 누락되거나 손상된 파티션을 다시 계산할 수 있습니다.

-   **분산**

데이터가 여러 노드에 있음을 의미합니다.

-   **데이터 세트**

작업하는 데이터의 기록일 뿐 입니다. 또한 사용자는 데이터 세트를 외부에서 불러올 수 있습니다. 예를 들어, 특정 데이터 구조가 없는 JDBC를 통한 JSON 파일, CSV 파일, 텍스트 파일 또는 데이터베이스일 수 있습니다.

[당신은 Spark dataSet 튜토리얼을 읽어야 합니다.](https://data-flair.training/blogs/apache-spark-dataset-tutorial/)

## 3\. RDD 계보정보(리니지) 소개

기본적으로, RDD의 평가는 자연적으로 게으릅니다. 이는 변환의 시리즈가 RDD에서 수행되지만, 바로 평가되지는 않습니다.
Spark RDD로부터 [새로운 RDD를 만드는](https://data-flair.training/blogs/create-rdds-in-apache-spark/) 동안, 새로운 RDD는 Spark에서 부모 RDD의 포인터를 가져옵니다. 이는 실제 데이터가 아니라 그래프에 기록된 RDD 간의 모든 종속성과 동일합니다. 우리가 계보 그래프라고 부르는 것입니다. RDD 계보는 RDD의 모든 부모 RDD의 그래프일 뿐 입니다. RDD 연산자 그래프 또는 RDD 종속성 그래프라고도 합니다. 구체적으로 말하자면 스파크에 Transformation을 적용한 결과입니다. 그런 다음 논리적 실행 계획을 생성합니다.
또한 실제 실행 계획 또는 실행 DAG를 단계의 [DAG](https://data-flair.training/blogs/dag-in-apache-spark/)라고 합니다.
잘 이해하기 위해 Cartesian 또는 zip을 사용하여 Spark RDD 계보의 한 예부터 시작하겠습니다. 그러나 다른 연산자를 사용하여 Spark에서 RDD 그래프를 작성할 수도 있습니다.

**예시**

![](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/01/rdd-lineage.jpg)

위 그림은 다음과 같은 일련의 Transformation의 결과인 RDD 그래프를 보여줍니다.

[Spark의 게으른 평가](https://data-flair.training/blogs/apache-spark-lazy-evaluation/)

```scala
val r00 = sc.parallelize(0 to 9)
val r01 = sc.parallelize(0 to 90 by 10)
val r10 = r00 cartesian df01
val r11 = r00.map(n => (n, n))
val r12 = r00 zip df01
val r13 = r01.keyBy(_ / 20)
val r20 = Seq(r11, r12, r13).foldLeft(r10)(_ union _)
```

**다른 예시**

다음과 같은 RDD `val b=a.map()`이 있다고 합시다.

RDD b는 부모 RDD a에 대한 참조를 유지해야 합니다. 이것이 RDD 계보 정보(리니지)의 종류입니다.
