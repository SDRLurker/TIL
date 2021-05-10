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
