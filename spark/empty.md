**출처**

[https://sparkbyexamples.com/spark/spark-how-to-create-an-empty-dataset/](https://sparkbyexamples.com/spark/spark-how-to-create-an-empty-dataset/)

# Spark - 비어있는 Dataset을 만드는 방법

이 글에서, 저는 몇가지 Scala 예시를 사용하여 비어있는 Spark Dataset(emptyDataset())을 스키마가 있고 없고에 따라 만드는 방법을 설명하겠습니다. 우리는 시작하기 전에, 비어있는 Dataset을 만들 필요가 있는 많은 시나리오 중에 하나를 설명하겠습니다.

Spark에서 파일로 작업하는 동안 처리할 파일을 받지 못하는 경우도 있지만, 파일을 받을 때 생성한 데이터 세트와 유사한 (동일한 [스키마](https://sparkbyexamples.com/spark/spark-schema-explained-with-examples/)) 빈 데이터 세트를 생성해야 합니다. 동일한 스키마로 생성하지 않으면 표시되지 않을 수 있는 열을 참조하므로 데이터 세트에 대한 작업 / 변환이 실패합니다.

**관련글:** [Spark 비어있는 DataFrame 생성하기](https://sparkbyexamples.com/spark/spark-how-to-create-an-empty-dataframe/)

이와 유사한 상황을 처리하려면 항상 동일한 스키마로 Dataset을 생성해야 합니다. 즉, 파일이 존재하거나 빈 파일 처리에 관계없이 동일한 열 이름과 데이터 유형을 의미합니다.

먼저 예제 전체에서 사용할 [SparkSession](https://sparkbyexamples.com/spark/sparksession-explained-with-examples/) 및 [Spark StructType](https://sparkbyexamples.com/spark/spark-sql-structtype-on-dataframe/) 스키마와 case class를 생성해 보겠습니다.

```scala
val spark:SparkSession = SparkSession.builder()
   .master("local[1]")
   .appName("SparkByExamples.com")
   .getOrCreate()

import spark.implicits._

val schema = StructType(
    StructField("firstName", StringType, true) ::
      StructField("lastName", IntegerType, false) ::
      StructField("middleName", IntegerType, false) :: Nil)

val colSeq = Seq("firstName","lastName","middleName")
case class Name(firstName: String, lastName: String, middleName:String)
```

## emptyDataset() – 컬럼이 없는 비어있는 Dataset 생성

SparkSession은 스키마 없이 빈 Dataset을 반환하는 emptyDataset() 메서드를 제공하지만 이것은 우리가 원하는 것이 아닙니다. 다음 예제는 스키마로 생성하는 방법을 보여줍니다.

```scala
case class Empty()
val ds0 = spark.emptyDataset[Empty]
ds0.printSchema()
// Outputs following
root
```

## emptyDataset() – 스키마로 비어있는 Dataset 생성

아래 예에서는 스키마 (열 이름 및 데이터 type)가 있는 Spark 빈 데이터 세트를 만듭니다.

```scala
val ds1=spark.emptyDataset[Name]
ds1.printSchema()
// Outputs following
root
 |-- firstName: string (nullable = true)
 |-- lastName: string (nullable = true)
 |-- middleName: string (nullable = true)
```

## createDataset() – 스키마로 비어있는 Dataset 생성


SparkSession의 `createDataset()` 메서드를 사용하여 스키마가 있는 빈 Spark Dataset을 만들 수 있습니다. 아래의 두 번째 예는 먼저 [빈 RDD를 생성](https://sparkbyexamples.com/apache-spark-rdd/spark-how-to-create-an-empty-rdd/)하고 RDD를 데이터 셋으로 변환하는 방법을 설명합니다.

```scala
val ds2=spark.createDataset(Seq.empty[Name])
ds2.printSchema()
val ds3=spark.createDataset(spark.sparkContext.emptyRDD[Name])
ds3.printSchema()
//These both Outputs following
root
 |-- firstName: string (nullable = true)
 |-- lastName: string (nullable = true)
 |-- middleName: string (nullable = true)
```

## createDataset () – 기본 열 이름으로 빈 Dataset 만들기

```scala
val ds4=spark.createDataset(Seq.empty[(String,String,String)])
ds4.printSchema()
// Outputs following
root
 |-- _1: string (nullable = true)
 |-- _2: string (nullable = true)
 |-- _3: string (nullable = true)
 ```

## 암시적인 인코더 사용

암시적인 인코더를 사용하는 다른 방법을 살펴 보겠습니다.

```scala
val ds5 = Seq.empty[(String,String,String)].toDS()
ds5.printSchema()
// Outputs following
root
 |-- _1: string (nullable = true)
 |-- _2: string (nullable = true)
 |-- _3: string (nullable = true)
```

## case class 사용

Scala case class에서 원하는 스키마로 빈 데이터 세트를 만들 수도 있습니다.

```scala
val ds6 = Seq.empty[Name].toDS()
ds6.printSchema()
// Outputs following
root
 |-- firstName: string (nullable = true)
 |-- lastName: string (nullable = true)
 |-- middleName: string (nullable = true)
```
