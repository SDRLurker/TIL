# Spark SQL, DataFrames 및 Datasets 가이드
* 개요
  + SQL
  + DataSets 및 DataFrames
* 시작하기
  + 시작점 : SparkSession
  + DataFrame 만들기
  + 타입이 지정되지 않은 DataSet 연산 (DataFrame 연산)
  + 프로그래밍 방식으로 SQL 쿼리 실행
  + 전역 임시(Temporary) View
  + DataSet 만들기
  + RDD와 상호 연산
    - 리플렉션을 사용하여 스키마 추론하기
    - 프로그래밍 방식으로 스키마 지정
  + Aggregations
    - 타입이 지정되지 않은 사용자 정의 Aggregate 함수
    - 타입에 안전한 사용자 정의 Aggregate 함수
* 데이터 소스
  + 일반적인 로드 / 저장 함수
    - 수동으로 옵션 지정
    - 파일에 직접 SQL 실행
    - 모드 저장
    - 영구 테이블에 저장
    - Bucketing, 분류 및 파티셔닝
  + Parquet 파일
    - 프로그래밍 방식으로 데이터 로드
    - 파티션 검색
    - 스키마 병합
    - Hive metastore Parquet 테이블 변환
      - Hive / Parquet 스키마 조정
      - 메타 데이터 새로 고침
    - 구성
  + ORC 파일
  + JSON 데이터 세트
  + Hive 테이블
    - Hive 테이블의 저장소 형식 지정
    - 다양한 버전의 Hive Metastore와 상호 작용
  + 다른 데이터베이스에 JDBC
  + 문제 해결
* 성능 튜닝
  + 메모리에 데이터 캐싱
  + 기타 구성 옵션
  + 브로드 캐스트 힌트 SQL 쿼리
* 분산 SQL 엔진
  + Thrift JDBC / ODBC 서버 실행
  + Spark SQL CLI 실행
* Apache Arrow와 Pandas를 위한 PySpark 사용 가이드
  + Spark의 Apache Arrow
    - PyArrow 설치 여부 확인
  + Pandas로 또는 Pandas로부터 변환 활성화
  + Pandas UDF (a.k.a. 벡터화 된 UDF)
    - Scalar
    - 그룹화된 Map
  + 사용 메모
    - 지원되는 SQL 유형
    - Arrow Batch 크기 설정
    - Time Zone Semantics와 Timestamp
* 마이그레이션 가이드
  + Spark SQL 2.3.0에서 2.3.1 이상으로 업그레이드
  + Spark SQL 2.2에서 2.3으로 업그레이드
  + Spark SQL 2.1에서 2.2로 업그레이드
  + Spark SQL 2.0에서 2.1로 업그레이드
  + Spark SQL 1.6에서 2.0으로 업그레이드
  + Spark SQL 1.5에서 1.6으로 업그레이드
  + Spark SQL 1.4에서 1.5로 업그레이드
  + Spark SQL 1.3에서 1.4로 업그레이드
    - DataFrame 데이터 reader / writer 인터페이스
    - DataFrame.groupBy는 그룹화 열을 유지.
    - DataFrame.withColumn의 동작 변경
  + Spark SQL 1.0-1.2에서 1.3으로 업그레이드
    - SchemaRDD의 이름을 DataFrame으로 변경
    - Java 및 Scala API의 통합
    - 암시적 변환 격리 및 dsl 패키지 제거 (스칼라 전용)
    - DataType을위한 org.apache.spark.sql의 타입 별칭 제거 (스칼라 전용)
    - sqlContext.udf로 UDF 등록(Java 및 Scala)
    - 더 이상 싱글톤이 아닌 파이썬 DataTypes
  + Apache Hive와의 호환성
    - 기존 Hive Warehouses에 배포
    - 지원되는 Hive 기능
    - 지원되지 않는 Hive 기능
    - 호환되지 않는 Hive UDF
* 참고
  + 데이터 유형
  + NaN 의미

# 개요
Spark SQL은 구조화된 데이터 처리를 위한 Spark 모듈입니다. 기본 Spark RDD API와는 달리, Spark SQL에서 제공하는 인터페이스는 수행되는 데이터와 계산의 구조에 대해 더 많은 정보를 Spark에 제공합니다. 내부적으로 Spark SQL은 이 추가(extra) 정보를 사용하여 추가 최적화를 수행합니다. SQL과 Dataset API를 포함하여 Spark SQL과 상호 작용하는 방법은 여러 가지가 있습니다. 결과를 계산할 때 계산을 표현하는 데 사용하는 API / 언어와 관계없이 동일한 실행 엔진이 사용됩니다. 이 동일함(unification)은 개발자가 주어진 변환을 표현하는 가장 자연스러운 방법을 제공하는 데 있어 다양한 API간에 쉽게 전환 할 수 있음을 의미합니다.

이 페이지의 모든 예제는 Spark 배포판에 포함된 샘플 데이터를 사용하며 spark-shell, pyspark shell 또는 sparkR shell에서 실행할 수 있습니다.

# SQL
Spark SQL의 한 가지 용도는 SQL 쿼리를 실행하는 것입니다. Spark SQL은 기존 Hive 설치에서 데이터를 읽는 데에도 사용할 수 있습니다. 이 기능을 구성하는 방법에 대한 자세한 내용은 [Hive 테이블](https://spark.apache.org/docs/latest/sql-programming-guide.html#hive-tables) 섹션을 참조하십시오. 다른 프로그래밍 언어에서 SQL을 실행하면 결과가 [DataSet / DataFrame](https://spark.apache.org/docs/latest/sql-programming-guide.html#datasets-and-dataframes)으로 반환됩니다. [명령행](https://spark.apache.org/docs/latest/sql-programming-guide.html#running-the-spark-sql-cli) 또는 [JDBC / ODBC](https://spark.apache.org/docs/latest/sql-programming-guide.html#running-the-thrift-jdbcodbc-server)를 사용하여 SQL 인터페이스와 상호 작용할 수도 있습니다.

# DataSets 및 DataFrames
DataSet은 분산된 데이터 집합입니다. DataSet은 Spark SQL의 최적화된 실행 엔진과 함께 RDD (강력한 타입(typing), 강력한 람다 기능 사용)의 이점을 제공하는 Spark 1.6에서 추가된 새로운 실행 인터페이스입니다. DataSet은 JVM 객체로 [생성될](https://spark.apache.org/docs/latest/sql-programming-guide.html#creating-datasets) 수 있고 transformation(변환) 함수(map, flatMap, filter 등)를 사용하여 조작할 수 있습니다. Dataset API는 [Scala](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.Dataset)와 [Java](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/sql/Dataset.html)에서 사용할 수 있습니다. Python에는 Dataset API에 대한 지원이 없습니다. 그러나 Python의 동적 특성으로 인해 Dataset API의 많은 이점을 이미 사용할 수 있습니다. (예시. 행의 필드에 이름을 자연스럽게 row.columnName으로 액세스 할 수 있습니다). R의 경우도 비슷합니다.

DataFrame은 이름을 가진 열로 구성된 데이터 집합입니다. 관계형 데이터베이스의 테이블이나 R / Python의 데이터 프레임과 개념적으로는 동일하지만 더 자세한 최적화가 필요합니다. DataFrame은 구조화된 데이터 파일, Hive의 테이블, 외부 데이터베이스 또는 기존 RDD와 같은 다양한 [소스](https://spark.apache.org/docs/latest/sql-programming-guide.html#data-sources)로 구성 할 수 있습니다. DataFrame API는 Scala, Java, [Python](https://spark.apache.org/docs/latest/api/python/pyspark.sql.html#pyspark.sql.DataFrame) 및 [R](https://spark.apache.org/docs/latest/api/R/index.html)에서 사용할 수 있습니다. Scala 및 Java에서 DataFrame은 행 데이터 집합으로 표시됩니다. [Scala API](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.Dataset)에서 DataFrame은 단순히 Dataset[Row]의 타입의 별칭(alias)입니다. [Java API](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/sql/Dataset.html)에서 사용자는 DataFrame을 나타내기 위해 Dataset<Row\>을 사용해야 합니다.

이 문서에서 Scala / Java DataSet 의 Row를 DataFrame라고 부르기도 합니다.

# 시작하기

# 시작점 : SparkSession

## Scala

Spark에서 모든 기능에 대한 진입점은 [SparkSession](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.SparkSession) 클래스입니다. 기본적인 SparkSession을 만들려면 SparkSession.builder()를 사용하면 됩니다.

```Scala
import org.apache.spark.sql.SparkSession

val spark = SparkSession
  .builder()
  .appName("Spark SQL basic example")
  .config("spark.some.config.option", "some-value")
  .getOrCreate()

// RDD에서 DataFrame으로 암시적인 변환을 위해
import spark.implicits._
```

Spark repo의 "examples/src/main/scala/org/apache/spark/examples/sql/SparkSQLExample.scala"에서 전체 예제 코드를 찾아 보십시오.

Spark 2.0의 SparkSession은 HiveQL을 사용하여 쿼리를 작성하는 기능, Hive UDF에 액세스하는 기능 및 Hive 테이블에서 데이터를 읽는 기능을 포함하여 Hive 기능을 기본적으로 지원합니다. 이러한 기능을 사용하려면 기존 Hive 설정이 필요하지 않습니다.

## Java

Spark에서 모든 기능에 대한 진입점은 [SparkSession](https://spark.apache.org/docs/latest/api/java/index.html#org.apache.spark.sql.SparkSession) 클래스입니다. 기본적인 SparkSession을 만들려면 SparkSession.builder()를 사용하면 됩니다.

```Java
import org.apache.spark.sql.SparkSession;

SparkSession spark = SparkSession
  .builder()
  .appName("Java Spark SQL basic example")
  .config("spark.some.config.option", "some-value")
  .getOrCreate();
```

Spark repo의 "examples/src/main/java/org/apache/spark/examples/sql/JavaSparkSQLExample.java"에서 전체 예제 코드를 찾아 보십시오.

Spark 2.0의 SparkSession은 HiveQL을 사용하여 쿼리를 작성하는 기능, Hive UDF에 액세스하는 기능 및 Hive 테이블에서 데이터를 읽는 기능을 포함하여 Hive 기능을 기본적으로 지원합니다. 이러한 기능을 사용하려면 기존 Hive 설정이 필요하지 않습니다.

## Python

Spark에서 모든 기능에 대한 진입점은 [SparkSession](https://spark.apache.org/docs/latest/api/python/pyspark.sql.html#pyspark.sql.SparkSession) 클래스입니다. 기본적인 SparkSession을 만들려면 SparkSession.builder를 사용하면 됩니다.

```Python
from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .appName("Python Spark SQL basic example") \
    .config("spark.some.config.option", "some-value") \
    .getOrCreate()
```

Spark repo의 "examples/src/main/python/sql/basic.py"에서 전체 예제 코드를 찾아 보십시오.

Spark 2.0의 SparkSession은 HiveQL을 사용하여 쿼리를 작성하는 기능, Hive UDF에 액세스하는 기능 및 Hive 테이블에서 데이터를 읽는 기능을 포함하여 Hive 기능을 기본적으로 지원합니다. 이러한 기능을 사용하려면 기존 Hive 설정이 필요하지 않습니다.

## R

Spark에서 모든 기능에 대한 진입점은 [SparkSession](https://spark.apache.org/docs/latest/api/R/sparkR.session.html) 클래스입니다. 기본적인 SparkSession을 만들려면 sparkR.session()를 사용하면 됩니다.

```R
sparkR.session(appName = "R Spark SQL basic example", sparkConfig = list(spark.some.config.option = "some-value"))
```

Spark repo의 "examples/src/main/r/RSparkSQLExample.R"에서 전체 예제 코드를 찾아 보십시오.


처음으로 호출할 때 sparkR.session()은 전역 SparkSession 싱글톤 인스턴스를 초기화하고 연속 호출에 대해 이 인스턴스에 대한 참조를 항상 반환합니다. 이 방법으로 사용자는 SparkSession을 한 번만 초기화하면 read.df와 같은 SparkR 함수가 이 전역 인스턴스에 암시적으로 액세스 할 수 있으며 사용자는 SparkSession 인스턴스를 전달할 필요가 없습니다.

Spark 2.0의 SparkSession은 HiveQL을 사용하여 쿼리를 작성하는 기능, Hive UDF에 액세스하는 기능 및 Hive 테이블에서 데이터를 읽는 기능을 포함하여 Hive 기능을 기본적으로 지원합니다. 이러한 기능을 사용하려면 기존 Hive 설정이 필요하지 않습니다.

# DataFrame 만들기

## Scala

SparkSession을 사용하면 응용 프로그램은 [기존 RDD](https://spark.apache.org/docs/latest/sql-programming-guide.html#interoperating-with-rdds), Hive 테이블 또는 [Spark 데이터 소스](https://spark.apache.org/docs/latest/sql-programming-guide.html#data-sources)에서 DataFrames를 만들 수 있습니다.

예를 들어, 다음은 JSON 파일의 내용을 기반으로 DataFrame을 만듭니다.

```Scala
val df = spark.read.json("examples/src/main/resources/people.json")

// DataFrame의 내용을 표준출력으로 보여준다.
df.show()
// +----+-------+
// | age|   name|
// +----+-------+
// |null|Michael|
// |  30|   Andy|
// |  19| Justin|
// +----+-------+
```

Spark repo의 "examples/src/main/scala/org/apache/spark/examples/sql/SparkSQLExample.scala"에서 전체 예제 코드를 찾아 보십시오.

## Java

SparkSession을 사용하면 응용 프로그램은 [기존 RDD](https://spark.apache.org/docs/latest/sql-programming-guide.html#interoperating-with-rdds), Hive 테이블 또는 [Spark 데이터 소스](https://spark.apache.org/docs/latest/sql-programming-guide.html#data-sources)에서 DataFrames를 만들 수 있습니다.

예를 들어, 다음은 JSON 파일의 내용을 기반으로 DataFrame을 만듭니다.

```Java
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

Dataset<Row> df = spark.read().json("examples/src/main/resources/people.json");

// DataFrame의 내용을 표준출력으로 보여준다.
df.show();
// +----+-------+
// | age|   name|
// +----+-------+
// |null|Michael|
// |  30|   Andy|
// |  19| Justin|
// +----+-------+
```

Spark repo의 "examples/src/main/java/org/apache/spark/examples/sql/JavaSparkSQLExample.java"에서 전체 예제 코드를 찾아 보십시오.

## Python

SparkSession을 사용하면 응용 프로그램은 [기존 RDD](https://spark.apache.org/docs/latest/sql-programming-guide.html#interoperating-with-rdds), Hive 테이블 또는 [Spark 데이터 소스](https://spark.apache.org/docs/latest/sql-programming-guide.html#data-sources)에서 DataFrames를 만들 수 있습니다.

예를 들어, 다음은 JSON 파일의 내용을 기반으로 DataFrame을 만듭니다.

```Python
# spark는 기존 SparkSession이다.
df = spark.read.json("examples/src/main/resources/people.json")
# DataFrame의 내용을 표준출력으로 보여준다.
df.show()
# +----+-------+
# | age|   name|
# +----+-------+
# |null|Michael|
# |  30|   Andy|
# |  19| Justin|
# +----+-------+
```

Spark repo의 "examples/src/main/python/sql/basic.py"에서 전체 예제 코드를 찾아 보십시오.

## R

SparkSession을 사용하면 응용 프로그램은 local R data.frame, Hive 테이블 또는 [Spark 데이터 소스](https://spark.apache.org/docs/latest/sql-programming-guide.html#data-sources)에서 DataFrames를 만들 수 있습니다.

예를 들어, 다음은 JSON 파일의 내용을 기반으로 DataFrame을 만듭니다.

```R
df <- read.json("examples/src/main/resources/people.json")

# DataFrame의 내용을 보여준다.
head(df)
##   age    name
## 1  NA Michael
## 2  30    Andy
## 3  19  Justin

# 처음 몇 줄만 출력하고 길이가 긴 값의 출력을 생략하는 다른 메소드
showDF(df)
## +----+-------+
## | age|   name|
## +----+-------+
## |null|Michael|
## |  30|   Andy|
## |  19| Justin|
## +----+-------+
```

Spark repo의 "examples/src/main/r/RSparkSQLExample.R"에서 전체 예제 코드를 찾아 보십시오.

# 타입이 지정되지 않은 DataSet 연산 (DataFrame 연산)

DataFrame은 [Scala](http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.Dataset), [Java](http://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/sql/Dataset.html), [Python](http://spark.apache.org/docs/latest/api/python/pyspark.sql.html#pyspark.sql.DataFrame) 및 [R](http://spark.apache.org/docs/latest/api/R/SparkDataFrame.html)에서 구조화 된 데이터 조작을 위한 도메인 특정 언어를 제공합니다.

위에서 언급했듯이 Spark 2.0에서 DataFrames는 Scala 및 Java API의 Row의 DataSet입니다. 이러한 연산은 강력한 타입의 Scala / Java DataSet과 함께 제공되는 "타입이 지정된 변환(transformation)"과 달리 "타입이 지정되지 않은 변환"이라고도 합니다.

여기에는 DataSet을 사용하여 구조화된 데이터 처리의 몇 가지 기본 예제가 포함됩니다.

## Scala

```Scala
// 이 import는 $-표기법을 사용하기 위해 필요합니다.
import spark.implicits._
// Tree 형식으로 스키마를 출력합니다.
df.printSchema()
// root
// |-- age: long (nullable = true)
// |-- name: string (nullable = true)

// "name" 열만 선택(select)합니다.
df.select("name").show()
// +-------+
// |   name|
// +-------+
// |Michael|
// |   Andy|
// | Justin|
// +-------+

// 모든 내용 age에 1을 더한 값을 선택(select)합니다.
df.select($"name", $"age" + 1).show()
// +-------+---------+
// |   name|(age + 1)|
// +-------+---------+
// |Michael|     null|
// |   Andy|       31|
// | Justin|       20|
// +-------+---------+

// 21살보다 나이(age)든 사람을 선택(select)합니다.
df.filter($"age" > 21).show()
// +---+----+
// |age|name|
// +---+----+
// | 30|Andy|
// +---+----+

// 나이(age) 별로 수를 샙니다.
df.groupBy("age").count().show()
// +----+-----+
// | age|count|
// +----+-----+
// |  19|    1|
// |null|    1|
// |  30|    1|
// +----+-----+
```

Spark repo의 "examples/src/main/scala/org/apache/spark/examples/sql/ SparkSQLExample.scala"에서 전체 예제 코드를 찾아보십시오.
DataSet에서 수행 할 수 있는 연산 type의 전체 목록은 [API 문서](http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.Dataset)를 참조하십시오.

간단한 열 참조 및 표현 이외에도 DataSet에는 문자열 조작, 날짜 계산, 일반적인 수학 연산 등의 풍부한 함수 라이브러리가 있습니다. 전체 목록은 [DataFrame 함수 참조](http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.functions$)에서 사용할 수 있습니다.

## Java

```Java
// col("...")이 df.col("...")보다 좋습니다.
import static org.apache.spark.sql.functions.col;

// Tree 형식으로 스키마를 출력합니다.
df.printSchema();
// root
// |-- age: long (nullable = true)
// |-- name: string (nullable = true)

// "name" 열만 선택(select)합니다.
df.select("name").show();
// +-------+
// |   name|
// +-------+
// |Michael|
// |   Andy|
// | Justin|
// +-------+

// 모든 내용 age에 1을 더한 값을 선택(select)합니다.
df.select(col("name"), col("age").plus(1)).show();
// +-------+---------+
// |   name|(age + 1)|
// +-------+---------+
// |Michael|     null|
// |   Andy|       31|
// | Justin|       20|
// +-------+---------+

// 21살보다 나이(age)든 사람을 선택(select)합니다.
df.filter(col("age").gt(21)).show();
// +---+----+
// |age|name|
// +---+----+
// | 30|Andy|
// +---+----+

// 나이(age) 별로 수를 샙니다.
df.groupBy("age").count().show();
// +----+-----+
// | age|count|
// +----+-----+
// |  19|    1|
// |null|    1|
// |  30|    1|
// +----+-----+
```

Spark repo의 "examples/src/main/java/org/apache/spark/examples/sql/JavaSparkSQLExample.java"에서 전체 예제 코드를 찾아보십시오.
DataSet에서 수행 할 수 있는 연산 type의 전체 목록은 [API 문서](http://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html)를 참조하십시오.

간단한 열 참조 및 표현 이외에도 DataSet에는 문자열 조작, 날짜 계산, 일반적인 수학 연산 등의 풍부한 함수 라이브러리가 있습니다. 전체 목록은 [DataFrame 함수 참조](http://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html)에서 사용할 수 있습니다.

## Python

파이썬에서는 속성(attribute) (df.age) 또는 인덱싱(indexing) (df['age'])을 사용하여 DataFrame의 열에 액세스 할 수 있습니다. 전자는 대화형 데이터 탐색에 편리하지만 사용자는 후자의 형식을 사용하는 것이 좋습니다. 이는 DataFrame 클래스의 속성으로 열 이름이 손상되지 않도록 할 것입니다.

```Python
# spark의 df는 이전 예시에서 왔습니다.
# Tree 형식으로 스키마를 출력합니다.
df.printSchema()
# root
# |-- age: long (nullable = true)
# |-- name: string (nullable = true)

# "name" 열만 선택(select)합니다.
df.select("name").show()
# +-------+
# |   name|
# +-------+
# |Michael|
# |   Andy|
# | Justin|
# +-------+

# 모든 내용 age에 1을 더한 값을 선택(select)합니다.
df.select(df['name'], df['age'] + 1).show()
# +-------+---------+
# |   name|(age + 1)|
# +-------+---------+
# |Michael|     null|
# |   Andy|       31|
# | Justin|       20|
# +-------+---------+

# 21살보다 나이(age)든 사람을 선택(select)합니다.
df.filter(df['age'] > 21).show()
# +---+----+
# |age|name|
# +---+----+
# | 30|Andy|
# +---+----+

# 나이(age) 별로 수를 샙니다.
df.groupBy("age").count().show()
# +----+-----+
# | age|count|
# +----+-----+
# |  19|    1|
# |null|    1|
# |  30|    1|
# +----+-----+
```

Spark repo의 "examples/src/main/python/sql/basic.py"에서 전체 예제 코드를 찾아보십시오.
DataSet에서 수행 할 수 있는 연산 type의 전체 목록은 [API 문서](http://spark.apache.org/docs/latest/api/python/pyspark.sql.html#pyspark.sql.DataFrame)를 참조하십시오.

간단한 열 참조 및 표현 이외에도 DataSet에는 문자열 조작, 날짜 계산, 일반적인 수학 연산 등의 풍부한 함수 라이브러리가 있습니다. 전체 목록은 [DataFrame 함수 참조](http://spark.apache.org/docs/latest/api/python/pyspark.sql.html#module-pyspark.sql.functions)에서 사용할 수 있습니다.

## R

```R
# DataFrame을 생성합니다.
df <- read.json("examples/src/main/resources/people.json")

# DataFrame의 내용을 보여줍니다.
head(df)
##   age    name
## 1  NA Michael
## 2  30    Andy
## 3  19  Justin


# Tree 형식으로 스키마를 출력합니다.
printSchema(df)
## root
## |-- age: long (nullable = true)
## |-- name: string (nullable = true)

# "name" 열만 선택(select)합니다.
head(select(df, "name"))
##      name
## 1 Michael
## 2    Andy
## 3  Justin

# 모든 내용 age에 1을 더한 값을 선택(select)합니다.
head(select(df, df$name, df$age + 1))
##      name (age + 1.0)
## 1 Michael          NA
## 2    Andy          31
## 3  Justin          20

# 21살보다 나이(age)든 사람을 선택(select)합니다.
head(where(df, df$age > 21))
##   age name
## 1  30 Andy

# 나이(age) 별로 수를 샙니다.
head(count(groupBy(df, "age")))
##   age count
## 1  19     1
## 2  NA     1
## 3  30     1
```
Spark repo의 "examples/src/main/r/RSparkSQLExample.R"에서 전체 예제 코드를 찾아보십시오.
DataSet에서 수행 할 수 있는 연산 type의 전체 목록은 [API 문서](http://spark.apache.org/docs/latest/api/R/index.html)를 참조하십시오.

간단한 열 참조 및 표현 이외에도 DataSet에는 문자열 조작, 날짜 계산, 일반적인 수학 연산 등의 풍부한 함수 라이브러리가 있습니다. 전체 목록은 [DataFrame 함수 참조](http://spark.apache.org/docs/latest/api/R/SparkDataFrame.html)에서 사용할 수 있습니다.
