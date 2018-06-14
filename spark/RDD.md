# RDD 프로그래밍 가이드
* 개요
* Spark와 연결(Linking)
* Spark 초기화
  + 셸 사용
* 탄력적인 분산 데이터 세트 (RDDs)
  + Parallelize된 컬렉션
  + 외부 데이터 세트
  + RDD 운영
    - 기초
    - Spark에 함수 전달하기
    - 클로저 이해하기
      - 예시
      - 로컬 모드와 클러스터 모드 비교
      - RDD의 요소 출력하기
    - 키 - 값 쌍을 사용한 작업
    - 변환(Transformation)
    - 행위(Action)
    - 셔플(shuffle) 연산
      - 배경
      - 성능 영향
  + RDD 영속성(persistence)
    - 어떤 스토리지 레벨을 선택해야합니까?
    - 데이터 제거
* 공유 변수
  + 브로드캐스트(Broadcast) 변수
  + Accumulators
* 클러스터에 배포
* 자바 / 스칼라에서 Spark 작업 시작하기
* 단위 테스트
* 여기에서 가야할 방향

# 개요
높은 추상화 레벨에서 모든 Spark 애플리케이션은 사용자의 main 함수를 실행하고 클러스터에서 다양한 병렬 작업을 실행 하는 드라이버 프로그램으로 구성됩니다. Spark에서 제공하는 주요 추상화는 탄력적인 분산 데이터 세트 (RDD)입니다.이 분산 데이터 세트는 클러스터 노드에서 분할 된(partitioned) 요소 모음으로 병렬로 작동 될 수 있습니다. RDD는 Hadoop 파일 시스템 (또는 Hadoop을 지원하는 다른 파일 시스템)이나 드라이버 프로그램의 기존 Scala 컬렉션에서 시작하여 RDD를 변환함으로써 생성됩니다. 사용자는 Spark에 메모리에 RDD를 유지 하도록 요청하여 병렬 작업에서 효율적으로 재사용 할 수 있습니다. 마지막으로, RDD는 노드 장애로부터 자동으로 복구됩니다.

Spark의 두 번째 추상화는 병렬 작업에서 사용할 수 있는 공유 변수 입니다. 기본적으로 Spark은 다른 노드에서 작업 세트로 병렬로 함수를 실행할 때 함수에 사용 된 각 변수의 복사본을 각 작업에 제공합니다. 때로는 변수가 작업간에 또는 작업과 드라이버 프로그램간에 공유되어야합니다. Spark은 공유 변수의 두 가지 유형 , 즉 모든 노드의 메모리에 값을 캐시하는 데 사용할 수 있는 브로드 캐스트 변수 와 카운터 및 합계와 같이 "추가된" 변수인 accumulator를 지원합니다.

이 가이드는 Spark에서 지원하는 각 언어의 각 기능을 보여줍니다. bin/spark-shell로 스칼라 셸이나 bin/pyspark로 파이썬 쉘에서 Spark의 대화형 셸을 실행하면 가장 쉽게 따라 할 수 있습니다.

# Spark와 연결(Linking)

## Scala

Spark 2.3.0은 기본으로 Scala로 작업하여 빌드되고 배포됩니다. (Spark는 Scala의 다른 버전에서도 작업사여 빌드될 수 있습니다.) Scala로 응용프로그램을 작성하기 위해, 당신은 호환되는 Scala 버전 (예 2.11.X)을 사용해야 할 것입니다.

Spark 응용프로그램을 작성하기 위해 Spark에 Maven 의존성을 추가해야 합니다. Spark는 Maven Central을 통해 사용할 수 있습니다.

```
groupId = org.apache.spark
artifactId = spark-core_2.11
version = 2.3.0
```

또한 HDFS 클러스터에 액세스하려면 사용중인 HDFS 버전에 대해 hadoop-client에 의존성을 추가해야합니다.

```
groupId = org.apache.hadoop
artifactId = hadoop-client
version = <your-hdfs-version>
```

마지막으로 Spark 클래스를 프로그램에 가져와야합니다. 다음 행을 추가하십시오.

```spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
```

(Spark 1.3.0 이전이면 당신은 본질적이고 암시적인 변환을 가능하도록 명시적으로 org.apache.spark.SparkContext._ 를 가져와야 합니다.)

## Java

Spark 2.3.0은 정확한 함수 작성을 위해 [람다 표현식](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)을 지원합니다. 그렇지 않으면 [org.apache.spark.api.java.function](http://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/api/java/function/package-summary.html) 패키지를 사용할 수 있습니다.

Note that support for Java 7 was removed in Spark 2.2.0.
Java 7에 대한 지원은 Spark 2.2.0에서 제거되었습니다.

Java에서 Spark 응용프로그램을 작성하려면 당신은 Spark의 의존성을 추가해야 합니다. Spark는 Maven Central을 통해 사용할 수 있습니다.

```
groupId = org.apache.spark
artifactId = spark-core_2.11
version = 2.3.0
```

또한 HDFS 클러스터에 액세스하려면 사용중인 HDFS 버전에 대해 hadoop-client에 의존성을 추가해야합니다.

```
groupId = org.apache.hadoop
artifactId = hadoop-client
version = <your-hdfs-version>
```

마지막으로 Spark 클래스를 프로그램에 가져와야합니다. 다음 행을 추가하십시오.

```java
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
```

## Python

Spark 2.3.0은 Python 2.7 이상 또는 Python 3.4 이상에서 작동합니다. 표준 CPython 인터프리터를 사용할 수 있으므로 NumPy와 같은 C 라이브러리를 사용할 수 있습니다. PyPy 2.3 이상에서도 작동합니다.

Python 2.6 지원은 Spark 2.2.0에서 제거되었습니다.

Python의 Spark 애플리케이션은 런타임에 Spark가 포함 된 bin / spark-submit 스크립트로 실행하거나 setup.py에 다음을 포함시킬 수 있습니다.

```python
    install_requires=[
        'pyspark=={site.SPARK_VERSION}'
    ]
```

PySpark를 pip 설치하지 않고 Python으로 Spark 응용 프로그램을 실행하려면 Spark 디렉토리에 있는 bin / spark-submit 스크립트를 사용하십시오. 이 스크립트는 Spark의 Java / Scala 라이브러리를 로드하고 응용 프로그램을 클러스터에 제출할 수있게합니다. bin / pyspark를 사용하여 대화식 Python 쉘을 시작할 수도 있습니다.

HDFS 데이터에 액세스하려면 사용중인 HDFS 버전에 연결되는 PySpark 빌드를 사용해야합니다. [사전 빌드된(pre-built) 패키지](http://spark.apache.org/downloads.html)는 일반 HDFS 버전의 경우 Spark 홈페이지에서도 구할 수 있습니다.

마지막으로 Spark 클래스를 프로그램에 가져와야 합니다. 다음 행을 추가하십시오.

```python
from pyspark import SparkContext, SparkConf
```

PySpark는 드라이버와 작업자 모두에서 동일한 부 버전의 Python을 필요로합니다. PATH에서 기본 파이썬 버전을 사용하므로 PYSPARK_PYTHON에서 사용할 Python 버전을 지정할 수 있습니다. 예를 들면 다음과 같습니다.

```shell
$ PYSPARK_PYTHON=python3.4 bin/pyspark
$ PYSPARK_PYTHON=/opt/pypy-2.5/bin/pypy bin/spark-submit examples/src/main/python/pi.py
```

# Spark 초기화

## Scala

Spark 프로그램에서 반드시 해야할 첫번째는 클러스터에게 접근하는 방법을 Spark에게 말하는 [SparkContext](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkContext) 객체를 생성하는 것입니다. [SparkContext](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkConf)를 생성하기 위해 당신은 당신의 응용프로그램에 관한 정보를 포함하는 SpackConf 객체를 만들어야 합니다.

Only one SparkContext may be active per JVM. You must stop() the active SparkContext before creating a new one.
JVM 당 하나의 SparkContext만 활성화될 수 있습니다. 당신은 새로 만들기 전에 활성화된 SparkContext를 stop() 해야 합니다.


```spark
val conf = new SparkConf().setAppName(appName).setMaster(master)
new SparkContext(conf)
```

appName 파라미터는 클러스터 UI에 표시할 당신의 응용프로그램 이름입니다. master 파라미터는 [Spark, Mesos, 또는 YARN 클러스터의 URL](https://spark.apache.org/docs/latest/submitting-applications.html#master-urls) 이나 local 모드에서 실행하기 위한 특별한 "local"이 올 수 있습니다. 실제 클러스터에서 실행할 때, 당신은 프로그램의 master 코드를 하드코딩하고 싶지 않다면, [spark-submit으로 응용 프로그램을 시작](https://spark.apache.org/docs/latest/submitting-applications.html)한 다음 수신하십시오. 그러나 로컬 테스트 및 단위 테스트의 경우 "local"을 전달하여 Spark를 프로세스 내에서 실행할 수 있습니다.

## Java

Java 프로그램에서 반드시 해야할 첫번째는 클러스터에게 접근하는 방법을 Spark에게 말하는 [JavaSparkContext](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/api/java/JavaSparkContext.html) 객체를 생성하는 것입니다. SparkContext를 생성하기 위해 당신은 당신의 응용프로그램에 관한 정보를 포함하는 [SpackConf](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/SparkConf.html) 객체를 만들어야 합니다.

```Java
SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
JavaSparkContext sc = new JavaSparkContext(conf);
```

appName 파라미터는 클러스터 UI에 표시할 당신의 응용프로그램 이름입니다. master 파라미터는 [Spark, Mesos, 또는 YARN 클러스터의 URL](https://spark.apache.org/docs/latest/submitting-applications.html#master-urls) 이나 local 모드에서 실행하기 위한 특별한 "local"이 올 수 있습니다. 실제 클러스터에서 실행할 때, 당신은 프로그램의 master 코드를 하드코딩하고 싶지 않다면, [spark-submit으로 응용 프로그램을 시작](https://spark.apache.org/docs/latest/submitting-applications.html)한 다음 수신하십시오. 그러나 로컬 테스트 및 단위 테스트의 경우 "local"을 전달하여 Spark를 프로세스 내에서 실행할 수 있습니다.

## Python

Spark 프로그램에서 반드시 해야할 첫번째는 클러스터에게 접근하는 방법을 Spark에게 말하는 [SparkContext](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkContext) 객체를 생성하는 것입니다. [SparkContext](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkConf)를 생성하기 위해 당신은 당신의 응용프로그램에 관한 정보를 포함하는 SpackConf 객체를 만들어야 합니다.

```Python
conf = SparkConf().setAppName(appName).setMaster(master)
sc = SparkContext(conf=conf)
```

appName 파라미터는 클러스터 UI에 표시할 당신의 응용프로그램 이름입니다. master 파라미터는 [Spark, Mesos, 또는 YARN 클러스터의 URL](https://spark.apache.org/docs/latest/submitting-applications.html#master-urls) 이나 local 모드에서 실행하기 위한 특별한 "local"이 올 수 있습니다. 실제 클러스터에서 실행할 때, 당신은 프로그램의 master 코드를 하드코딩하고 싶지 않다면, [spark-submit으로 응용 프로그램을 시작](https://spark.apache.org/docs/latest/submitting-applications.html)한 다음 수신하십시오. 그러나 로컬 테스트 및 단위 테스트의 경우 "local"을 전달하여 Spark를 프로세스 내에서 실행할 수 있습니다.

# 셸 사용

## Scala

Spark 셸에서 특별히 인터프리터가 알고있는 SparkContext는 이미 sc라 불리는 변수로 당신을 위해 이미 생성되어 있습니다. 당신이 소유한 SparkContext를 만드는 것은 작동하지 않을 것입니다. 당신은 --master 인자를 사용하여 context가 어떤 마스터에 접속할 지 설정할 수 있고 --jars 인자로 쉼표로 구분된 리스트를 넘김으로서 classpath에 JAR를 추가할 수 있습니다. --packages 인자에 대응하는 쉼표로 구분된 Maven 목록을 제공함으로서 셸 세션에 종속성(예 : Spark 패키지)을 추가 할 수도 있습니다. 의존성이 존재할 수 있는 추가 저장소 (예 : Sonatype)는 --repositories 인수로 전달 될 수 있습니다. 예를 들어 정확히 4 개의 코어에서 bin / spark-shell을 실행하려면 다음을 사용하십시오.

```
$ ./bin/spark-shell --master local[4]
```

또는 classpath에 code.jar을 추가하려면 다음을 사용하십시오.

```
$ ./bin/spark-shell --master local[4] --jars code.jar
```

packages 인자에 대응하는 Maven을 사용하여 의존성을 포함하려면 다음을 수행하십시오.

```
$ ./bin/spark-shell --master local[4] --packages "org.example:example:0.1"
```

전체 option 목록을 보려면 spark-shell --help를 실행하세요. 더 자세하게 말하자면 spark-shell은 더 일반적인 [spark-submit 스크립트](https://spark.apache.org/docs/latest/submitting-applications.html)를 호출합니다.

## Python

PySpark 셸에서 특별히 인터프리터가 알고있는 SparkContext는 이미 sc라 불리는 변수로 당신을 위해 이미 생성되어 있습니다. 당신이 소유한 SparkContext를 만드는 것은 작동하지 않을 것입니다. 당신은 --master 인자를 사용하여 context가 어떤 마스터에 접속할 지 설정할 수 있고 --py-files 인자로 쉼표로 구분된 리스트를 넘김으로서 런타임 경로(runtime path)에 Python .zip, .egg 또는 .py 파일을 추가할 수 있습니다. --packages 인자에 대응하는 쉼표로 구분된 Maven 목록을 제공함으로서 셸 세션에 종속성(예 : Spark 패키지)을 추가 할 수도 있습니다. 의존성이 존재할 수 있는 추가 저장소 (예 : Sonatype)는 --repositories 인수로 전달 될 수 있습니다. Spark Package에 대한 Python 의존성은 (그 패키지의 requirements.txt 목록에 있습니다.) 필요하다면 pip를 사용하여 설치해야 합니다. Any Python dependencies a Spark package has (listed in the requirements.txt of that package) must be manually installed using pip when necessary. 예를 들어 정확히 4 개의 코어에서 bin / pyspark를 실행하려면 다음을 사용하십시오.

```
$ ./bin/pyspark --master local[4]
```

또는 검색 경로(search path)에 (code를 import하여 나중에 사용하기 위해) code.py을 추가하려면 다음을 사용하십시오.

```
$ ./bin/pyspark --master local[4] --py-files code.py
```

전체 옵션 목록을 보려면 pyspark --help를 실행하십시오. 더 자세하게 말하자면 pyspark는보다 일반적인 [spark-submit 스크립트](https://spark.apache.org/docs/latest/submitting-applications.html)를 호출합니다.

파이썬 인터프리터 인 [IPython](http://ipython.org/)에서 PySpark 셸을 시작할 수도 있습니다. PySpark는 IPython 1.0.0 이상에서 작동합니다. IPython을 사용하려면 bin / pyspark를 실행할 때 PYSPARK_DRIVER_PYTHON 변수를 ipython으로 설정하십시오.

```
$ PYSPARK_DRIVER_PYTHON=ipython ./bin/pyspark
```

Jupyter 노트북 (이전에 IPython 노트북이라고 함)을 사용하려면,

```
$ PYSPARK_DRIVER_PYTHON=jupyter PYSPARK_DRIVER_PYTHON_OPTS=notebook ./bin/pyspark
```

PYSPARK_DRIVER_PYTHON_OPTS를 설정하여 ipython 또는 jupyter 명령을 사용자 정의 할 수 있습니다.

Jupyter Notebook 서버가 시작되면 "파일"탭에서 새로운 "Python 2"노트북을 만들 수 있습니다. 노트북 안에는 Jupyter 노트북에서 Spark를 시도하기 전에 노트북의 일부로 %pylab 인라인 명령을 입력 할 수 있습니다.

# 탄력적인 분산 데이터 세트 (RDDs)

Spark은 탄력적인 분산 데이터 세트(RDD) 개념을 중심으로 병렬로 작동할 수 있는 내결함성 컬렉션입니다. RDD를 생성하는 방법은 2가지가 있습니다. 드라이버 프로그램에서 기존 collection을 paralleize로 만드는 방법과 공유 파일 시스템, HDFS, HBase 또는 데이터 소스로 하둡 InputFormat을 제공할 수 있는 데이터 소스와 같은 외부 스토리지 시스템에서 데이터 집합을 참조하는 방법이 있습니다.

# Parallelize된 컬렉션

## Scala

Parallelize된 컬렉션은 당신의 드라이버 프로그램 (Scala Seq)의 기존 컬렉션에서 SparkContext의 parallelize 메소드를 호출하여 생성됩니다. 컬렉션의 요소는 병렬로 연산될 수 있는 분산된 데이터 집합의 형태로 복사됩니다. 예를 들어 1에서 5 까지의 숫자가 있는 Parallelize된 컬렉션을 작성하는 방법은 다음과 같습니다.

```scala
val data = Array(1, 2, 3, 4, 5)
val distData = sc.parallelize(data)
```

일단 생성되면 분산 데이터 세트 (distData)를 병렬로 조작 할 수 있습니다. 예를 들어, distData.reduce ((a, b) => a + b)를 호출하여 배열의 요소에 덧셈을 할 수 있습니다. 나중에 분산 데이터 세트에 대한 연산을 설명합니다.

Parallelize된 컬렉션을 위한 중요한 매개변수 중 하나는 데이터 집합을 잘라내는 파티션의 개수입니다. Spark는 클러스터의 각 파티션에 대해 하나의 작업을 실행합니다. 일반적으로 클러스터의 각 CPU에 대해 2-4 개의 파티션이 필요합니다. 보통 Spark는 클러스터를 기반으로 자동으로 파티션 수를 설정하려고 시도합니다. 그러나 parallelize 할 두 번째 매개 변수 (예 : sc.parallelize (data, 10))로 전달하여 수동으로 설정할 수도 있습니다. 참고 : 코드의 일부 위치에서는 이전 버전과의 호환성을 유지하기 위해 슬라이스라는 용어 (파티션과 동의어)를 사용합니다.

## Java

Parallelize된 컬렉션은 당신의 드라이버 프로그램의 기존 컬렉션에서  JavaSparkContext의 parallelize 메소드를 호출하여 생성됩니다. 컬렉션의 요소는 병렬로 연산될 수 있는 분산된 데이터 집합의 형태로 복사됩니다. 예를 들어 1에서 5 까지의 숫자가 있는 Parallelize된 컬렉션을 작성하는 방법은 다음과 같습니다.

```Java
List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
JavaRDD<Integer> distData = sc.parallelize(data);
```

일단 생성되면 분산 데이터 세트 (distData)를 병렬로 조작 할 수 있습니다. 예를 들어, distData.reduce ((a, b) -> a + b)를 호출하여 리스트의 요소에 덧셈을 할 수 있습니다. 나중에 분산 데이터 세트에 대한 연산을 설명합니다.

Parallelize된 컬렉션을 위한 중요한 매개변수 중 하나는 데이터 집합을 잘라내는 파티션의 개수입니다. Spark는 클러스터의 각 파티션에 대해 하나의 작업을 실행합니다. 일반적으로 클러스터의 각 CPU에 대해 2-4 개의 파티션이 필요합니다. 보통 Spark는 클러스터를 기반으로 자동으로 파티션 수를 설정하려고 시도합니다. 그러나 parallelize 할 두 번째 매개 변수 (예 : sc.parallelize (data, 10))로 전달하여 수동으로 설정할 수도 있습니다. 참고 : 코드의 일부 위치에서는 이전 버전과의 호환성을 유지하기 위해 슬라이스라는 용어 (파티션과 동의어)를 사용합니다.

## Python

Parallelize된 컬렉션은 당신의 드라이버 프로그램의 기존 컬렉션에서 SparkContext의 parallelize 메소드를 호출하여 생성됩니다. 컬렉션의 요소는 병렬로 연산될 수 있는 분산된 데이터 집합의 형태로 복사됩니다. 예를 들어 1에서 5 까지의 숫자가 있는 Parallelize된 컬렉션을 작성하는 방법은 다음과 같습니다.

```Python
data = [1, 2, 3, 4, 5]
distData = sc.parallelize(data)
```

일단 생성되면 분산 데이터 세트 (distData)를 병렬로 조작 할 수 있습니다. 예를 들어, distData.reduce (lambda a, b: a + b)를 호출하여 리스트의 요소에 덧셈을 할 수 있습니다. 나중에 분산 데이터 세트에 대한 연산을 설명합니다.

Parallelize된 컬렉션을 위한 중요한 매개변수 중 하나는 데이터 집합을 잘라내는 파티션의 개수입니다. Spark는 클러스터의 각 파티션에 대해 하나의 작업을 실행합니다. 일반적으로 클러스터의 각 CPU에 대해 2-4 개의 파티션이 필요합니다. 보통 Spark는 클러스터를 기반으로 자동으로 파티션 수를 설정하려고 시도합니다. 그러나 parallelize 할 두 번째 매개 변수 (예 : sc.parallelize (data, 10))로 전달하여 수동으로 설정할 수도 있습니다. 참고 : 코드의 일부 위치에서는 이전 버전과의 호환성을 유지하기 위해 슬라이스라는 용어 (파티션과 동의어)를 사용합니다.

# 외부 데이터 세트

## Scala

Spark는 local 파일 시스템, HDFS, Cassandra, HBase, [Amazon S3](http://wiki.apache.org/hadoop/AmazonS3) 등 Hadoop이 지원하는 모든 저장 소스에서 분산 데이터 세트를 만들 수 있습니다. Spark는 텍스트 파일, [SequenceFiles](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/mapred/SequenceFileInputFormat.html) 및 기타 Hadoop [InputFormat](http://hadoop.apache.org/docs/stable/api/org/apache/hadoop/mapred/InputFormat.html)을 지원합니다.

텍스트 파일 RDD는 SparkContext의 textFile 메소드를 사용하여 만들 수 있습니다. 이 메소드는 파일의 URI (시스템의 local 경로 또는 hdfs://, s3a://, 기타 URI)를 가져 와서 행 모음으로 읽습니다. 예제 호출은 다음과 같습니다.

```
scala> val distFile = sc.textFile("data.txt")
distFile: org.apache.spark.rdd.RDD[String] = data.txt MapPartitionsRDD[10] at textFile at <console>:26
```

일단 생성되면, distFile은 데이터 집합 연산에 의해 작동될 수 있습니다. 예를 들어, distFile.map (s => s.length) .reduce ((a, b) => a + b)와 같이  map과 reduce를 사용하여 모든 라인의 크기를 합산할 수 있습니다.

Spark로 파일 읽기에 대한 몇 가지 사항입니다.

* local 파일 시스템의 경로를 사용하는 경우 작업 노드와 동일한 경로에서 파일에 액세스 할 수 있어야합니다. 파일을 모든 작업자에게 복사하거나 네트워크 마운트된 공유 파일 시스템을 사용하십시오.

* textFile을 포함한 Spark의 모든 파일 기반 입력 방법은 디렉토리, 압축 파일 및 와일드 카드도 실행될 수 있습니다. 예를 들어 textFile ( "/ my / directory"), textFile ( "/ my / directory / \*. txt") 및 textFile ( "/ my / directory / \*. gz")을 사용할 수 있습니다.

* textFile 메소드는 또한 파일의 파티션 수를 제어하기 위해 선택적인 두 번째 인수를 취합니다. 기본적으로 스파크는 파일의 각 블록에 대해 하나의 파티션을 만듭니다 (HDFS에서는 블록이 기본적으로 128MB 임). 그러나 더 큰 값을 전달하여 더 많은 수의 파티션을 요청할 수도 있습니다. 블록보다 파티션 수는 적을 수 없습니다.

텍스트 파일 외에도 Spark의 Scala API는 여러 가지 다른 데이터 형식을 지원합니다.

* SparkContext.wholeTextFiles를 사용하면 여러 개의 작은 텍스트 파일이 들어있는 디렉토리를 읽고 각각을 (파일 이름, 내용) 쌍으로 반환합니다. 이것은 각 파일에서 한 줄에 하나의 레코드를 반환하는 textFile과는 대조적입니다. Partitioning은 데이터 지역성에 의해 결정되며, 경우에 따라 파티션이 너무 적을 수 있습니다. 이러한 경우 wholeTextFiles는 최소한의 파티션 수를 제어하기 위한 선택적인 두 번째 인수를 제공합니다.

* [SequenceFile](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/mapred/SequenceFileInputFormat.html)의 경우, SparkContext의 sequenceFile [K, V] 메소드를 사용합니다. 여기서 K와 V는 파일의 키와 값의 type입니다. 이들은 [IntWritable](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/io/IntWritable.html) 및 [Text](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/io/Text.html)와 같은 Hadoop의 [Writable](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/io/Writable.html) 인터페이스의 하위 클래스 여야합니다. 또한 Spark를 사용하면 몇 가지 일반적인 Writable에 대해 기본 유형을 지정할 수 있습니다. 예를 들어, sequenceFile [Int, String]은 자동으로 IntWritables 및 Text를 읽습니다.

* 다른 Hadoop InputFormats의 경우, 임의의 JobConf 및 입력 형식 클래스, 키 클래스 및 값 클래스를 취하는 SparkContext.hadoopRDD 메서드를 사용할 수 있습니다. 입력 소스를 사용하는 Hadoop 작업과 동일한 방식으로 설정하십시오. "새로운" MapReduce API (org.apache.hadoop.mapreduce)에 기반한 InputFormats에 대해 SparkContext.newAPIHadoopRDD를 사용할 수도 있습니다.

* RDD.saveAsObjectFile 및 SparkContext.objectFile은 직렬화 된 Java 객체로 구성된 간단한 형식으로 RDD를 저장하도록 지원합니다. Avro와 같은 특수 형식만큼 효율적이지는 않지만 RDD를 쉽게 저장할 수 있습니다.

## Java

Spark는 local 파일 시스템, HDFS, Cassandra, HBase, [Amazon S3](http://wiki.apache.org/hadoop/AmazonS3) 등 Hadoop이 지원하는 모든 저장 소스에서 분산 데이터 세트를 만들 수 있습니다. Spark는 텍스트 파일, [SequenceFiles](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/mapred/SequenceFileInputFormat.html) 및 기타 Hadoop [InputFormat](http://hadoop.apache.org/docs/stable/api/org/apache/hadoop/mapred/InputFormat.html)을 지원합니다.

텍스트 파일 RDD는 SparkContext의 textFile 메소드를 사용하여 만들 수 있습니다. 이 메소드는 파일의 URI (시스템의 local 경로 또는 hdfs://, s3a://, 기타 URI)를 가져 와서 행 모음으로 읽습니다. 예제 호출은 다음과 같습니다.

```Java
JavaRDD<String> distFile = sc.textFile("data.txt");
```

일단 생성되면, distFile은 데이터 집합 연산에 의해 작동될 수 있습니다. 예를 들어, distFile.map(s -> s.length()).reduce((a, b) -> a + b)와 같이  map과 reduce를 사용하여 모든 라인의 크기를 합산할 수 있습니다.

Spark로 파일 읽기에 대한 몇 가지 사항입니다.

* local 파일 시스템의 경로를 사용하는 경우 작업 노드와 동일한 경로에서 파일에 액세스 할 수 있어야합니다. 파일을 모든 작업자에게 복사하거나 네트워크 마운트된 공유 파일 시스템을 사용하십시오.

* textFile을 포함한 Spark의 모든 파일 기반 입력 방법은 디렉토리, 압축 파일 및 와일드 카드도 실행될 수 있습니다. 예를 들어 textFile ( "/ my / directory"), textFile ( "/ my / directory / \*. txt") 및 textFile ( "/ my / directory / \*. gz")을 사용할 수 있습니다.

* textFile 메소드는 또한 파일의 파티션 수를 제어하기 위해 선택적인 두 번째 인수를 취합니다. 기본적으로 스파크는 파일의 각 블록에 대해 하나의 파티션을 만듭니다 (HDFS에서는 블록이 기본적으로 128MB 임). 그러나 더 큰 값을 전달하여 더 많은 수의 파티션을 요청할 수도 있습니다. 블록보다 파티션 수는 적을 수 없습니다.

텍스트 파일 외에도 Spark의 Scala API는 여러 가지 다른 데이터 형식을 지원합니다.

* JavaSparkContext.wholeTextFiles를 사용하면 여러 개의 작은 텍스트 파일이 들어있는 디렉토리를 읽고 각각을 (파일 이름, 내용) 쌍으로 반환합니다. 이것은 각 파일에서 한 줄에 하나의 레코드를 반환하는 textFile과는 대조적입니다.

* [SequenceFile](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/mapred/SequenceFileInputFormat.html)의 경우, SparkContext의 sequenceFile [K, V] 메소드를 사용합니다. 여기서 K와 V는 파일의 키와 값의 type입니다. 이들은 [IntWritable](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/io/IntWritable.html) 및 [Text](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/io/Text.html)와 같은 Hadoop의 [Writable](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/io/Writable.html) 인터페이스의 하위 클래스 여야합니다.

* 다른 Hadoop InputFormats의 경우, 임의의 JobConf 및 입력 형식 클래스, 키 클래스 및 값 클래스를 취하는 JavaSparkContext.hadoopRDD 메서드를 사용할 수 있습니다. 입력 소스를 사용하는 Hadoop 작업과 동일한 방식으로 설정하십시오. "새로운" MapReduce API (org.apache.hadoop.mapreduce)에 기반한 InputFormats에 대해 SparkContext.newAPIHadoopRDD를 사용할 수도 있습니다.

* JavaRDD.saveAsObjectFile 및 JavaSparkContext.objectFile은 직렬화 된 Java 객체로 구성된 간단한 형식으로 RDD를 저장하도록 지원합니다. Avro와 같은 특수 형식만큼 효율적이지는 않지만 RDD를 쉽게 저장할 수 있습니다.

## Python

PySpark는 local 파일 시스템, HDFS, Cassandra, HBase, [Amazon S3](http://wiki.apache.org/hadoop/AmazonS3) 등 Hadoop이 지원하는 모든 저장 소스에서 분산 데이터 세트를 만들 수 있습니다. Spark는 텍스트 파일, [SequenceFiles](http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/mapred/SequenceFileInputFormat.html) 및 기타 Hadoop [InputFormat](http://hadoop.apache.org/docs/stable/api/org/apache/hadoop/mapred/InputFormat.html)을 지원합니다.

텍스트 파일 RDD는 SparkContext의 textFile 메소드를 사용하여 만들 수 있습니다. 이 메소드는 파일의 URI (시스템의 local 경로 또는 hdfs://, s3a://, 기타 URI)를 가져 와서 행 모음으로 읽습니다. 예제 호출은 다음과 같습니다.

```
>>> distFile = sc.textFile("data.txt")
```

일단 생성되면, distFile은 데이터 집합 연산에 의해 작동될 수 있습니다. 예를 들어, distFile.map(lambda s: len(s)).reduce(lambda a, b: a + b)와 같이  map과 reduce를 사용하여 모든 라인의 크기를 합산할 수 있습니다.

Spark로 파일 읽기에 대한 몇 가지 사항입니다.

* local 파일 시스템의 경로를 사용하는 경우 작업 노드와 동일한 경로에서 파일에 액세스 할 수 있어야합니다. 파일을 모든 작업자에게 복사하거나 네트워크 마운트된 공유 파일 시스템을 사용하십시오.

* textFile을 포함한 Spark의 모든 파일 기반 입력 방법은 디렉토리, 압축 파일 및 와일드 카드도 실행될 수 있습니다. 예를 들어 textFile ( "/ my / directory"), textFile ( "/ my / directory / \*. txt") 및 textFile ( "/ my / directory / \*. gz")을 사용할 수 있습니다.

* textFile 메소드는 또한 파일의 파티션 수를 제어하기 위해 선택적인 두 번째 인수를 취합니다. 기본적으로 스파크는 파일의 각 블록에 대해 하나의 파티션을 만듭니다 (HDFS에서는 블록이 기본적으로 128MB 임). 그러나 더 큰 값을 전달하여 더 많은 수의 파티션을 요청할 수도 있습니다. 블록보다 파티션 수는 적을 수 없습니다.

텍스트 파일 외에도 Spark의 Scala API는 여러 가지 다른 데이터 형식을 지원합니다.

* SparkContext.wholeTextFiles를 사용하면 여러 개의 작은 텍스트 파일이 들어있는 디렉토리를 읽고 각각을 (파일 이름, 내용) 쌍으로 반환합니다. 이것은 각 파일에서 한 줄에 하나의 레코드를 반환하는 textFile과는 대조적입니다.

* RDD.saveAsPickleFile과 SparkContext.pickleFile은 pickle 형태의 Python 객체로 구성된 간단한 형식으로 RDD를 저장하도록 지원합니다. 일괄 처리는 pickle 직렬화에 사용되며 기본 일괄 처리 크기는 10입니다.

* SequenceFile과 Hadoop 입출력 포맷

이 기능은 현재 실험적으로 표시되어 있으며 고급 사용자를 대상으로합니다. Spark SQL 기반의 읽기 / 쓰기 지원으로 대체 될 수 있습니다.이 경우 Spark SQL이 선호됩니다.

### Writable 객체

PySpark SequenceFile 지원은 Java 내에서 키 - 값 쌍의 RDD를 로드하고 Writable을 기본 Java 유형으로 변환하며 [Pyrolite](https://github.com/irmen/Pyrolite/)를 사용하여 결과 Java 객체를 pickle합니다. 키 - 값 쌍의 RDD를 SequenceFile에 저장할 때, PySpark는 그 반대입니다. Python 객체를 Java 객체로 unpickle하고 Writable로 변환합니다. 다음 Writable은 자동으로 변환됩니다.

|Writable Type  |Python Type|
|---------------|-----------|
|Text           |unicode str|
|IntWritable	  |int        |
|FloatWritable  |float      |
|DoubleWritable |float      |
|BooleanWritable|bool       |
|BytesWritable  |bytearray  |
|NullWritable   |None       |
|MapWritable    |dict       |

배열은 즉시 사용할 수 없습니다. 사용자는 읽기 또는 쓰기 작업시 사용자 정의 ArrayWritable 부속 유형을 지정해야합니다. 작성시 사용자는 배열을 사용자 정의 ArrayWritable 하위 유형으로 변환하는 사용자 정의 변환기를 지정해야 합니다. 읽을 때 기본 변환기는 사용자 정의 ArrayWritable 부속 유형을 Java Object []로 변환 한 다음 Python 튜플에 저장합니다. 원시 타입의 배열을위한 파이썬 array.array를 얻으려면, 사용자는 커스텀 변환기를 지정할 필요가 있습니다.

### SequenceFile 저장 및로드

텍스트 파일과 마찬가지로 SequenceFiles는 경로를 지정하여 저장하고 로드할 수 있습니다. 키 및 값 클래스를 지정할 수는 있지만 표준 Writable의 경우 필수는 아닙니다.

```
>>> rdd = sc.parallelize(range(1, 4)).map(lambda x: (x, "a" * x))
>>> rdd.saveAsSequenceFile("path/to/file")
>>> sorted(sc.sequenceFile("path/to/file").collect())
[(1, u'a'), (2, u'aa'), (3, u'aaa')]
```

### 다른 Hadoop 입력 / 출력 형식 저장 및로드

PySpark는 '새로운'및 '이전'Hadoop MapReduce API에 대해 Hadoop InputFormat을 읽거나 Hadoop OutputFormat을 작성할 수도 있습니다. 필요한 경우 Hadoop 구성을 Python dict로 전달할 수 있습니다. 다음은 Elasticsearch ESInputFormat을 사용하는 예제입니다.

```
$ ./bin/pyspark --jars /path/to/elasticsearch-hadoop.jar
>>> conf = {"es.resource" : "index/type"}  # Elasticsearch가 localhost에 defaults로 실행중이라 가정합니다.
>>> rdd = sc.newAPIHadoopRDD("org.elasticsearch.hadoop.mr.EsInputFormat",
                             "org.apache.hadoop.io.NullWritable",
                             "org.elasticsearch.hadoop.mr.LinkedMapWritable",
                             conf=conf)
>>> rdd.first()  # 결과는 Python dict로 MapWritable로 부터 변환되었습니다.
(u'Elasticsearch ID',
 {u'field1': True,
  u'field2': u'Some Text',
  u'field3': 12345})
```

InputFormat이 단순히 Hadoop 설정 및  입력 경로에 의존하고 위의 표에 따라 키 및 값 클래스를 쉽게 변환 할 수 있다면 이 방법이 이러한 경우에 적합해야합니다.

(Cassandra / HBase에서 데이터를 로드하는 것과 같이) 사용자가 정의한 직렬화된 이진 데이터가 있는 경우 먼저 Scala / Java 측의 해당 데이터를 Pyrolite의 pickler가 처리 할 수 있는 데이터로 변환해야 합니다. 이것을 위해 Converter trait이 제공됩니다. 이 trait을 확장하고 convert 메소드에서 변환 코드를 구현(implement)하기만 하면 됩니다. InputFormat에 접근하는 데 필요한 의존성과 함께 이 클래스가 Spark 작업 항아리에 패키징되고 PySpark 클래스 패스에 포함되는지 확인하십시오.

Cassandra / HBase InputFormat과 OutputFormat을 커스텀 컨버터와 함께 사용하는 예제는 [Python 예제](https://github.com/apache/spark/tree/master/examples/src/main/python)와 [Converter 예제](https://github.com/apache/spark/tree/master/examples/src/main/scala/org/apache/spark/examples/pythonconverters)를 참조하십시오.

# RDD 연산

RDD는 두 가지 유형의 연산, 즉 기존 데이터 집합에서 새 데이터 집합을 만드는 transformation(변환)과 데이터 집합에서 계산을 실행 한 후 값을 드라이버 프로그램에 반환하는 action을 지원합니다. 예를 들어, map은 함수를 통해 각 데이터 집합 요소를 전달하고 결과를 나타내는 새로운 RDD를 반환하는 transformation(변환) 유형입니다. 반면에 reduce는 일부 함수를 사용하여 RDD의 모든 요소를 ​​집계하고 최종 결과를 드라이버 프로그램에 반환하는 action 유형입니다. (분산된 데이터 집합을 반환하는 병렬 reduceByKey도 있습니다.)

Spark의 모든 변환(transformation)은 lazy합니다. 그래서 변환은 바로 결과를 계산하지 않습니다. 대신에, 변환은 몇 가지 기본 데이터셋에 적용합니다. (예: 파일) 그 변환은 action이 드라이버 프로그램에 리턴될 때만 계산됩니다. 이 설계는 Spark를 더 효율적으로 실행하게 합니다. 예를 들어, 맵을 통해 생성 된 데이터 세트는 reduce에서 사용되며 맵핑된 더 큰 데이터 세트가 아니라 드라이버의 reduce 결과 만을 리턴함으로서 효율적임을 알 수 있습니다.

기본적으로 각 변환된 RDD는 action을 실행할 때마다 재계산 됩니다. 그러나 persist (혹은 cache) 메소드를 사용하여 RDD를 보관할 수 있고 이 경우 Spark는 다음에 이 결과를 질의했을 때 더 빠르게 접근할 수 있도록 클러스터에서 RDD의 값들을 보관할 것입니다. 또한 디스크에 RDD를 지속적으로 저장하게 지원하거나 여러 노드에 걸쳐 복제 할 수 있습니다.

# 기초

## Scala

RDD 기초에서 설명했듯이 아래 간단한 프로그램을 생각해 봅시다.

```Scala
val lines = sc.textFile("data.txt")
val lineLengths = lines.map(s => s.length)
val totalLength = lineLengths.reduce((a, b) => a + b)
```

첫 번째 줄은 외부 파일에서부터 기본 RDD를 정의합니다. 이 데이터셋은 메모리에 로드되지 않고 행위도 없습니다. lines는 단지 파일의 포인터입니다. 두 번째 줄은 map 변환(transformation) 의 결과로 lineLengths를 정의합니다. 다시 말하지만 lineLengths는 lazy 때문에 바로 계산되지 않습니다. 마지막으로 우리가 action인 reduce를 실행합니다. 이 지점에서 Spark는 분리된 머신에서 실행하기 위해 작업들을 계산하고 각 머신은 map과 local reduce의 일부분을 실행합니다. 드라이버 프로그램에게는 그 답변만 리턴합니다.

If we also wanted to use lineLengths again later, we could add:
만약 우리가 lineLengths를 나중에 다시 사용하고 싶다면 다음을 추가할 수 있습니다.

```scala
lineLengths.persist()
```

reduce 전에 lineLength가 처음 계산 된 후 메모리에 저장됩니다.

## Java

RDD 기초에서 설명했듯이 아래 간단한 프로그램을 생각해 봅시다.

```Java
JavaRDD<String> lines = sc.textFile("data.txt");
JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
int totalLength = lineLengths.reduce((a, b) -> a + b);
```

첫 번째 줄은 외부 파일에서부터 기본 RDD를 정의합니다. 이 데이터셋은 메모리에 로드되지 않고 행위도 없습니다. lines는 단지 파일의 포인터입니다. 두 번째 줄은 map 변환(transformation) 의 결과로 lineLengths를 정의합니다. 다시 말하지만 lineLengths는 lazy 때문에 바로 계산되지 않습니다. 마지막으로 우리가 action인 reduce를 실행합니다. 이 지점에서 Spark는 분리된 머신에서 실행하기 위해 작업들을 계산하고 각 머신은 map과 local reduce의 일부분을 실행합니다. 드라이버 프로그램에게는 그 답변만 리턴합니다.

If we also wanted to use lineLengths again later, we could add:
만약 우리가 lineLengths를 나중에 다시 사용하고 싶다면 다음을 추가할 수 있습니다.

```Java
lineLengths.persist(StorageLevel.MEMORY_ONLY());
```

reduce 전에 lineLength가 처음 계산 된 후 메모리에 저장됩니다.

## Python

RDD 기초에서 설명했듯이 아래 간단한 프로그램을 생각해 봅시다.

```Python
lines = sc.textFile("data.txt")
lineLengths = lines.map(lambda s: len(s))
totalLength = lineLengths.reduce(lambda a, b: a + b)
```

첫 번째 줄은 외부 파일에서부터 기본 RDD를 정의합니다. 이 데이터셋은 메모리에 로드되지 않고 행위도 없습니다. lines는 단지 파일의 포인터입니다. 두 번째 줄은 map 변환(transformation) 의 결과로 lineLengths를 정의합니다. 다시 말하지만 lineLengths는 lazy 때문에 바로 계산되지 않습니다. 마지막으로 우리가 action인 reduce를 실행합니다. 이 지점에서 Spark는 분리된 머신에서 실행하기 위해 작업들을 계산하고 각 머신은 map과 local reduce의 일부분을 실행합니다. 드라이버 프로그램에게는 그 답변만 리턴합니다.

If we also wanted to use lineLengths again later, we could add:
만약 우리가 lineLengths를 나중에 다시 사용하고 싶다면 다음을 추가할 수 있습니다.

```Python
lineLengths.persist()
```

reduce 전에 lineLength가 처음 계산 된 후 메모리에 저장됩니다.

# Spark에 함수 전달하기

## Scala

Spark의 API는 드라이버 프로그램에서 함수를 전달하여 클러스터에서 실행하는 것에 크게 의존합니다. 이 작업을 수행하는 두 가지 권장 방법이 있습니다.

* [익명 함수 구문](http://docs.scala-lang.org/tour/basics.html#functions) : 짧은 코드 조각에 사용할 수 있습니다.
* 전역 싱글톤 객체(object)의 정적 메소드. 예를 들어 다음처럼 MyFunctions object를 정의하고 MyFunctions를 전달할 수 있습니다.

```Scala
object MyFunctions {
  def func1(s: String): String = { ... }
}

myRdd.map(MyFunctions.func1)
```

클래스 인스턴스 (싱글톤 객체가 아닌)에서 메소드에 대한 참조를 전달하는 것도 가능하지만,이 경우 메소드와 함께 해당 클래스가 포함된 객체를 보내야합니다. 예를 들어 다음을 고려하십시오.

```Scala
class MyClass {
  def func1(s: String): String = { ... }
  def doStuff(rdd: RDD[String]): RDD[String] = { rdd.map(func1) }
}
```

여기서 새로운 MyClass 인스턴스를 만들고 그것에 doStuff를 호출하면 그 안에있는 맵이 해당 MyClass 인스턴스의 func1 메소드를 참조하므로 전체 객체를 클러스터로 보내야합니다. rdd.map (x => this.func1 (x))을 작성하는 것과 유사합니다.

비슷한 방법으로 외부 객체의 필드에 액세스하면 전체 객체를 참조하게됩니다.

```Scala
class MyClass {
  val field = "Hello"
  def doStuff(rdd: RDD[String]): RDD[String] = { rdd.map(x => field + x) }
}
```

이는 rdd.map(x => this.field + x)를 작성하는 것과 같으며 this 전체를 참조하게 됩니다. 이를 피하기 위해 간단한 방법은 메소드 외부의 값을 접근하는 것이 아니고 local 변수로 외부 field를 복사하는 것입니다.

```Scala
def doStuff(rdd: RDD[String]): RDD[String] = {
  val field_ = this.field
  rdd.map(x => field_ + x)
}
```

## Java

Spark의 API는 드라이버 프로그램에서 함수를 전달하여 클러스터에서 실행하는 것에 크게 의존합니다. Java에서 함수는 [org.apache.spark.api.java.function](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/api/java/function/package-summary.html) 패키지의 인터페이스를 구현한 클래스에 의해 표현됩니다. 이 함수를 생성하는 두 가지 방법이 있습니다.

* 자신의 클래스에 익명의 내부 클래스 또는 명명된(named) 클래스로 Function 인터페이스를 구현하고 그 인스턴스를 Spark에 전달합니다.
* lambda 표현식을 사용하여 구현을 간결하게 정의하십시오.

이 가이드의 대부분은 간결성을 위해 람다 구문을 사용하지만 긴 형식의 모든 동일한 API를 사용하는 것은 쉽습니다. 예를 들어 위 코드를 다음과 같이 작성할 수 있습니다.

```Java
JavaRDD<String> lines = sc.textFile("data.txt");
JavaRDD<Integer> lineLengths = lines.map(new Function<String, Integer>() {
  public Integer call(String s) { return s.length(); }
});
int totalLength = lineLengths.reduce(new Function2<Integer, Integer, Integer>() {
  public Integer call(Integer a, Integer b) { return a + b; }
});
```

또는, 함수를 인라인으로 작성하는 것이 어렵다면

```Java
class GetLength implements Function<String, Integer> {
  public Integer call(String s) { return s.length(); }
}
class Sum implements Function2<Integer, Integer, Integer> {
  public Integer call(Integer a, Integer b) { return a + b; }
}

JavaRDD<String> lines = sc.textFile("data.txt");
JavaRDD<Integer> lineLengths = lines.map(new GetLength());
int totalLength = lineLengths.reduce(new Sum());
```

Java의 익명 내부 클래스는 final로 표시된 경우 enclosing(중괄호로 묶인) 범위의 변수에도 액세스 할 수 있습니다. Spark은 다른 언어와 마찬가지로 이러한 변수의 복사본을 각 작업자 노드에 제공합니다.

## Python

Spark의 API는 드라이버 프로그램에서 함수를 전달하여 클러스터에서 실행하는 것에 크게 의존합니다. 이 작업을 수행하는 세 가지 권장 방법이 있습니다.

* [lambda 표현식](https://docs.python.org/2/tutorial/controlflow.html#lambda-expressions)을 사용하여 구현을 간결하게 정의하십시오. (lambda는 여러 문장의 함수나 값을 리턴하지 않는 문장은 지원하지 않습니다.)
* 더 긴 코드를 위해 Spark로 호출할 함수 안에 local def(함수)를 정의합니다.
* 모듈에서 최상위 함수를 정의합니다.

예를 들어 람다를 사용하여 지원할 수있는 것보다 긴 함수를 전달하려면 아래 코드를 고려하십시오.

```Python
"""MyScript.py"""
if __name__ == "__main__":
    def myFunc(s):
        words = s.split(" ")
        return len(words)

    sc = SparkContext(...)
    sc.textFile("file.txt").map(myFunc)
```

클래스 인스턴스 (싱글톤 객체가 아닌)에서 메소드에 대한 참조를 전달하는 것도 가능하지만,이 경우 메소드와 함께 해당 클래스가 포함된 객체를 보내야합니다. 예를 들어 다음을 고려하십시오.

```Python
class MyClass(object):
    def func(self, s):
        return s
    def doStuff(self, rdd):
        return rdd.map(self.func)
```

여기서 새로운 MyClass 인스턴스를 만들고 그것에 doStuff를 호출하면 그 안에있는 맵이 해당 MyClass 인스턴스의 func1 메소드를 참조하므로 전체 객체를 클러스터로 보내야합니다.

비슷한 방법으로 외부 객체의 필드에 액세스하면 전체 객체를 참조하게됩니다.

```Python
class MyClass(object):
    def __init__(self):
        self.field = "Hello"
    def doStuff(self, rdd):
        return rdd.map(lambda s: self.field + s)
```

이를 피하기 위해 간단한 방법은 메소드 외부의 값을 접근하는 것이 아니고 local 변수로 외부 field를 복사하는 것입니다.

```Python
def doStuff(self, rdd):
    field = self.field
    return rdd.map(lambda s: field + s)
```

# 클로저 이해하기

Spark에 관한 더 어려운 것 중 하나는 클러스터 간에 코드를 실행할 때 변수와 메소드의 범위와 수명주기를 이해하는 것입니다. 범위 밖의 변수를 수정하는 RDD 연산은 자주 혼란을 일으킬 수 있습니다. 아래 예제에서는 foreach ()를 사용하여 카운터를 증가시키는 코드를 살펴 보지만 다른 작업에서도 비슷한 문제가 발생할 수 있습니다.

# 예시

아래의 단순한 RDD 요소 합계를 고려해보십시오. 실행은 동일한 JVM 내에서 발생하는지 여부에 따라 다르게 동작 할 수 있습니다. 이에 대한 일반적인 예는 지역 모드 (--master = local [n])에서 Spark 애플리케이션을 클러스터에 배포하는 경우 (예 : YARN에 대한 spark-submit을 통해)입니다.

## Scala

```Scala
var counter = 0
var rdd = sc.parallelize(data)

// 틀렸음: 이렇게 하지 마세요!!
rdd.foreach(x => counter += x)

println("Counter value: " + counter)
```

## Java

```Java
int counter = 0;
JavaRDD<Integer> rdd = sc.parallelize(data);

// 틀렸음: 이렇게 하지 마세요!!
rdd.foreach(x -> counter += x);

println("Counter value: " + counter);
```

## Python

```Python
counter = 0
rdd = sc.parallelize(data)

# 틀렸음: 이렇게 하지 마세요!!
def increment_counter(x):
    global counter
    counter += x
rdd.foreach(increment_counter)

print("Counter value: ", counter)
```

# 로컬 모드와 클러스터 모드 비교

위 코드의 동작은 정의되지 않았으므로 의도한 대로 작동하지 않을 수 있습니다. 작업을 실행하기 위해 Spark는 RDD 작업 처리를 작업으로 분리합니다. 각 작업은 실행 프로그램에 의해 실행됩니다. Spark은 실행 전에 작업의 클로저(closure)를 계산합니다. 클로저는 Executor가 RDD (이 경우 foreach ())에서 계산을 수행 할 수 있도록 표시되어야하는 변수 및 메소드입니다. 이 클로저는 직렬화되어 각 실행자에게 전송됩니다.

각 실행자에게 전송 된 클로저 내의 변수는 이제 복사되므로 foreach 함수 내에서 카운터가 참조되면 더 이상 드라이버 노드의 카운터가 아닙니다. 드라이버 노드의 메모리에는 여전히 카운터가 있지만 실행 프로그램에서는 더 이상 볼 수 없습니다! 집행자는 일련 화 된 클로저의 복사본 만 봅니다. 따라서 카운터의 모든 연산이 직렬화 된 클로저 내의 값을 참조하기 때문에 카운터의 최종 값은 여전히 ​​0이됩니다.

로컬 모드에서는 foreach 함수가 실제로 드라이버와 동일한 JVM 내에서 실행되며 동일한 원래 카운터를 참조하고 실제로 업데이트 할 수 있습니다.

이러한 종류의 시나리오에서 잘 정의 된 동작을 보장하려면 Accumulator를 사용해야합니다. Spark에서 Accumulator는 클러스터의 작업자 노드에서 실행이 분리 될 때 변수를 안전하게 업데이트하기위한 메커니즘을 제공하기 위해 특별히 사용됩니다. 이 가이드의 Accumulators 섹션에서 이에 대해 자세히 설명합니다.

일반적으로 루프와 같은 구조 또는 로컬 정의된 메소드는 일부 전역 상태를 변경하는 데 사용하면 안됩니다. Spark은 클로저 외부에서 참조 된 객체에 대한 돌연변이의 동작을 정의하거나 보장하지 않습니다. 이 작업을 수행하는 일부 코드는 로컬 모드에서 작동하지만 실수로 인해 이러한 코드는 분산 모드에서 예상대로 작동하지 않습니다. 일부 글로벌 집계가 필요한 경우 Accumulator를 사용하십시오.

# RDD의 요소 출력하기

또 다른 공통 관용구는 rdd.foreach (println) 또는 rdd.map (println)을 사용하여 RDD 요소를 출력하려고 합니다. 단일 시스템에서는 예상 출력을 생성하고 모든 RDD의 요소를 출력합니다. 그러나 클러스터 모드에서 executor에 의해 호출되는 stdout에 대한 출력이 이제는 실행 프로그램의 stdout이 아닌 executor의 stdout에 쓰여지므로 드라이버의 stdout은 이를 표시하지 않습니다! 드라이버의 모든 요소를 ​​출력 하려면 collect() 메서드를 사용하여 먼저 RDD를 드라이버 노드에 가져옵니다. rdd.collect().foreach(println). 그러나 collect()는 전체 RDD를 단일 시스템으로 가져 오기 때문에 드라이버에서 메모리가 부족해질 수 있습니다. RDD의 일부 요소만 출력해야 하는 경우 안전한 방법은 다음처럼 take()를 사용하는 것입니다. rdd.take(100).foreach(println)

# 키 - 값 쌍을 사용한 작업

## Scala

대부분 Spark 연산은 객체 타입을 포함하는 RDD로 작동하지만 몇 가지 특별한 연산은 키 - 값 쌍의 RDD에서만 사용할 수 있습니다. 공통점은 키에 의해 요소들을 그룹화하거나 집계하는 것과 같은 분산된 "shuffle" 연산입니다.

Scala에서 이 연산은 [Tuple2](http://www.scala-lang.org/api/2.11.8/index.html#scala.Tuple2) 객체를 포함한 RDD로 자동으로 사용할 수 있습니다(언어의 build-in 튜플로 간단하게 (a, b)로 작성하여 생성됩니다). 키 - 값 쌍 연산은 자동으로 tuple의 RDD로 wrapping한 [PairRDDFunctions](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.PairRDDFunctions) class에서 사용 가능합니다.

예를 들어, 다음 코드는 키 - 값 쌍에 reduceByKey 연산을 사용하여 파일에서 각 텍스트 행이 몇 번 발생하는지 계산합니다.

```Scala
val lines = sc.textFile("data.txt")
val pairs = lines.map(s => (s, 1))
val counts = pairs.reduceByKey((a, b) => a + b)
```

예를 들어, counts.sortByKey ()를 사용하여 쌍을 사전 순으로 정렬하고 마지막으로 counts.collect ()를 사용하여 객체 배열로 드라이버 프로그램에 다시 가져올 수 있습니다.

참고 : 키 - 값 쌍 작업에서 사용자 지정 개체를 키로 사용하는 경우 사용자 지정 equals () 메서드와 일치하는 hashCode () 메서드가 있어야합니다. 자세한 것은, [Object.hashCode () 문서](http://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode())로 설명되고 있는 규약을 참조하십시오.

## Java

대부분 Spark 연산은 객체 타입을 포함하는 RDD로 작동하지만 몇 가지 특별한 연산은 키 - 값 쌍의 RDD에서만 사용할 수 있습니다. 공통점은 키에 의해 요소들을 그룹화하거나 집계하는 것과 같은 분산된 "shuffle" 연산입니다.

Java에서 키 - 값 쌍은 Scala 표준 라이브러리의 [scala.Tuple2](http://www.scala-lang.org/api/2.11.8/index.html#scala.Tuple2) 클래스를 사용하여 표현됩니다. 새 Tuple2 (a, b)를 호출하여 튜플을 만들고 나중에 tuple._ 1 () 및 tuple._ 2 ()를 사용하여 해당 필드에 액세스 할 수 있습니다.

키 - 값 쌍의 RDD는 [JavaPairRDD](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/api/java/JavaPairRDD.html) 클래스로 표시됩니다. mapToPair 및 flatMapToPair와 같은 map 연의 특수 버전을 사용하여 JavaRDD에서 JavaPairRDD를 생성 할 수 있습니다. JavaPairRDD는 표준 RDD 기능과 특수 키 - 값 기능을 모두 갖습니다.

예를 들어, 다음 코드는 키 - 값 쌍에 reduceByKey 연산을 사용하여 파일에서 각 텍스트 행이 몇 번 발생하는지 계산합니다.

```Java
JavaRDD<String> lines = sc.textFile("data.txt");
JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(s, 1));
JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
```

예를 들어, counts.sortByKey ()를 사용하여 쌍을 사전 순으로 정렬하고 마지막으로 counts.collect ()를 사용하여 객체 배열로 드라이버 프로그램에 다시 가져올 수 있습니다.

참고 : 키 - 값 쌍 작업에서 사용자 지정 개체를 키로 사용하는 경우 사용자 지정 equals () 메서드와 일치하는 hashCode () 메서드가 있어야합니다. 자세한 것은, [Object.hashCode () 문서](http://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode())로 설명되고 있는 규약을 참조하십시오.

## Python

대부분 Spark 연산은 객체 타입을 포함하는 RDD로 작동하지만 몇 가지 특별한 연산은 키 - 값 쌍의 RDD에서만 사용할 수 있습니다. 공통점은 키에 의해 요소들을 그룹화하거나 집계하는 것과 같은 분산된 "shuffle" 연산입니다.

Python에서 이러한 연산은 (1,2)와 같은 built-in Python 튜플을 포함하는 RDD에서 작동합니다. 이러한 튜플을 만든 다음 원하는 연산을 호출하면 됩니다.

예를 들어, 다음 코드는 키 - 값 쌍에 reduceByKey 연산을 사용하여 파일에서 각 텍스트 행이 몇 번 발생하는지 계산합니다.

```Python
lines = sc.textFile("data.txt")
pairs = lines.map(lambda s: (s, 1))
counts = pairs.reduceByKey(lambda a, b: a + b)
```

예를 들어, counts.sortByKey ()를 사용하여 쌍을 사전 순으로 정렬하고 마지막으로 counts.collect ()를 사용하여 객체 배열로 드라이버 프로그램에 다시 가져올 수 있습니다.

# 셔플(shuffle) 연산

Spark 내의 특정 작업은 셔플(shuffle)이라는 이벤트를 발생(trigger)합니다. 셔플은 파티션 간에 그룹화되도록 데이터를 다시 배포하는 Spark의 메커니즘입니다. 이것은 일반적으로 executor와 기계간에 데이터를 복사하여 셔플을 복잡하고 값 비싼 작업으로 만듭니다.

# 배경

셔플하는 동안 무슨 일이 일어나는지 이해하려면 reduceByKey 작업의 예를 생각해 볼 수 있습니다. reduceByKey 연산은 하나의 키에 대한 모든 값이 하나의 튜플로 결합된 새로운 RDD를 생성합니다. 이 튜플은 해당 키와 관련된 모든 값에 대해 reduce 함수를 실행한 결과입니다. 도전할 문제는 하나의 키에 대한 모든 값이 동일한 파티션이나 동일한 시스템에 있어야 하는 것은 아니지만 결과를 계산하기 위해 같은 위치에 있어야한다는 것입니다.

Spark에서 데이터는 일반적으로 특정 작업을 수행하는 데 필요한 위치에 있도록 여러 파티션에 분산되어 있지 않습니다. 계산 중에 단일 작업이 단일 파티션에서 작동하므로 하나의 reduceByKey에서 reduce 작업을 실행하기 위한 모든 데이터를 구성하려면 Spark가 all-to-all 작업을 수행해야 합니다. 모든 파티션의 모든 값을 찾아 모든 파티션의 값을 모아 각 키의 최종 결과를 계산해야합니다. 이것을 셔플이라고 합니다.

새로 shuffle된 데이터의 각 파티션에있는 요소 집합이 결정적 일 수는 있지만 파티션 자체의 순서도 동일하지만 이러한 요소의 순서는 다릅니다. shuffle 후에 정렬한 데이터가 필요하다면 다음을 사용할 수 있습니다.

* 예를 들어 .sorted를 사용하여 각 파티션(partition)을 정렬하기 위해 mapPartitions을 사용합니다.
* 동시에 repartition하는 동안 partition을 효율적으로 정렬하기 위해 repartitionAndSortWithinPartitions을 사용합니다.
* 전체가 정렬된 RDD를 만들기 위해 sortBy를 사용합니다.

셔플을 발생하는 연산은 [repartition](https://spark.apache.org/docs/latest/rdd-programming-guide.html#RepartitionLink)과 [coalesce](https://spark.apache.org/docs/latest/rdd-programming-guide.html#CoalesceLink)와 같은 **repartition** 연산, [groupByKey](https://spark.apache.org/docs/latest/rdd-programming-guide.html#GroupByLink)와 [reduceByKey](https://spark.apache.org/docs/latest/rdd-programming-guide.html#ReduceByLink) 같은 **ByKey** 연산(세는 연산 제외) 그리고 [cogroup](https://spark.apache.org/docs/latest/rdd-programming-guide.html#CogroupLink)과 [join](https://spark.apache.org/docs/latest/rdd-programming-guide.html#JoinLink) 같은 join 연산을 포함합니다.

# 성능 영향

**셔플** 은 디스크 입출력, 데이터 직렬화, 네트워크 입출력을 포함하기 때문에 비싼 연산입니다. 셔플에 대한 데이터를 구성하기 위해 Spark는 데이터를 구성하기 위한 map 작업과 이를 집계하는 reduce 작업 세트를 생성합니다. 이 명명법은 MapReduce에서 왔지만 Spark의 map과 reduce 연산과는 직접적인 관련은 없습니다.

내부적으로 각 map 작업의 결과는 적합하지 않을 때까지 메모리에 보관됩니다. 그런 다음 대상 파티션을 기반으로 정렬되고 단일 파일에 기록됩니다. reduce 측면에서 작업은 관련하여 정렬된 블록을 읽습니다.

특정 셔플 작업은 메모리 내 데이터 구조를 사용하여 레코드를 전송하기 전 또는 후에 레코드를 구성하기 때문에 상당한 양의 힙 메모리를 사용할 수 있습니다. 특히, reduceByKey 및 aggregateByKey는 map 측면에서 이러한 구조를 만들고 'ByKey 연산은 reduce 측면에서 이러한 구조를 생성합니다. 데이터가 메모리보다 크다면 Spark는 디스크에 이러한 테이블을 쏟아 부어 디스크 입출력 및 가비지 수집의 추가 오버 헤드를 초래합니다.

Shuffle은 또한 디스크에 많은 수의 중간 파일을 생성합니다. Spark 1.3부터 이 파일들은 해당 RDD가 더 이상 사용되지 않고 가비지 수집 될 때까지 보존됩니다. lineage(계보)를 다시 계산할 때 셔플 파일을 다시 만들 필요가 없도록 이 작업이 수행됩니다. 가비지 수집은 응용 프로그램이 이러한 RDD에 대한 참조를 유지하거나 GC가 자주 시작하지 않는 경우 오랜 시간이 지나야 만 발생할 수 있습니다. 이는 장기적으로 실행하는 Spark 작업이 많은 양의 디스크 공간을 소비 할 수 있음을 의미합니다. 임시 저장소 디렉토리는 Spark 컨텍스트를 구성 할 때 spark.local.dir 구성 매개 변수에 의해 지정됩니다.

셔플 동작은 다양한 구성 매개 변수를 통해 조정할 수 있습니다. [Spark Configuration Guide](https://spark.apache.org/docs/latest/configuration.html)의 'Shuffle Behavior'섹션을 참조하십시오.

# RDD 영속성(persistence)

Spark에서 가장 중요한 기능 중 하나는 작업 전반에 걸쳐 메모리에 데이터 집합을 유지 (또는 캐싱)하는 것입니다. RDD를 유지하면 각 노드는 메모리에 계산 된 모든 파티션을 저장하고 해당 데이터 세트 (또는 그로 부터 파생 된 데이터 세트)의 다른 작업에서 다시 사용합니다. 이렇게 하면 향후 작업을 훨씬 빠르게 (종종 10배 이상) 수행 할 수 있습니다. 캐싱은 반복 알고리즘 및 빠른 대화식 사용을 위한 핵심 도구입니다.

persist() 또는 cache() 메소드를 사용하여 RDD에 유지되도록 표시 할 수 있습니다. 처음 action에서 계산되면 노드의 메모리에 유지됩니다. Spark의 캐시는 내결함성이 있습니다. RDD의 파티션이 손실되면 원래 만든 transformation을 사용하여 자동으로 다시 계산됩니다.

또한 유지된 각 RDD는 다른 저장소 레벨을 사용하여 저장할 수 있으므로 디스크에 데이터 집합을 유지하거나 메모리에 보관할 수 있지만 (공간을 절약하기 위해) 직렬화된 Java 객체로 노드간에 복제 할 수 있습니다. 이 레벨은 StorageLevel 객체 ([Scala](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.storage.StorageLevel), [Java](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/storage/StorageLevel.html), [Python](https://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/storage/StorageLevel.html))를 persist ()로 전달하여 설정합니다. cache () 메서드는 기본 저장소 수준 인 StorageLevel.MEMORY_ONLY (메모리에 deserialize 된 개체 저장)를 사용하는 것을 의미합니다. 전체 저장소 레벨은 다음과 같습니다.

|저장소 레벨|의미|
|---------------|-----------|
|MEMORY_ONLY|RDD는 JVM에 직렬화 되지 않은 Java 객체로 저장합니다. RDD가 메모리에 저장되지 못할 경우 일부 파티션은 캐시되지 않으며 필요할 때마다 즉시 다시 계산됩니다. 이것은 default 입니다.|
|MEMORY_AND_DISK|RDD는 JVM에 직렬화 되지 않은 Java 객체로 저장합니다. RDD가 메모리에 저장되지 못할 경우 디스크에 맞지 않는 파티션을 저장하고 필요할 때 읽을 수 있습니다.|
|MEMORY_ONLY_SER<br>(Java and Scala)|직렬화된 Java 객체(파티션 당 바이트 배열)로 RDD를 저장합니다. 이것은 일반적으로 고속 serializer를 사용하는 경우에는 deserialize 된 객체보다 공간에 효율적입니다. 그러나 읽는데 CPU를 더 많이 사용합니다.|
|MEMORY_AND_DISK_SER<br>(Java and Scala)|MEMORY_ONLY_SER와 비슷하지만 파티션이 필요할 때마다 재계산하는 대신에 디스크에 있고 메모리에 저장되지 못하는 파티션을 넣습니다.|
|DISK_ONLY|RDD를 디스크에만 저장합니다.|
|MEMORY_ONLY_2, MEMORY_AND_DISK_2, 기타등등|위의 레벨과 같지만, 2개의 클러스터 노드로 각 파티션을 복제합니다.|
|OFF_HEAP(실험중)|MEMORY_ONLY_SER와 비슷하지만 데이터를 [off-heap 메모리](https://spark.apache.org/docs/latest/configuration.html#memory-management)에 저장합니다.이는 off-heap 메모리가 사용 가능해야 설정할 수 있습니다.|

참고 : 파이썬에서는 저장된 객체가 항상 Pickle 라이브러리로 직렬화되므로 직렬화된 레벨을 선택했는지 여부는 중요하지 않습니다. 파이썬에서 사용 가능한 저장소 레에는 MEMORY_ONLY, MEMORY_ONLY_2, MEMORY_AND_DISK, MEMORY_AND_DISK_2, DISK_ONLY 및 DISK_ONLY_2가 있습니다.

Spark은 사용자가 persist를 호출하지 않아도 셔플 작업(예 : reduceByKey)에서 중간 데이터를 자동으로 유지합니다. 이는 셔플 중에 노드가 실패 할 경우 전체 입력을 다시 계산하는 것을 피하기 위해 수행됩니다. 재사용을 계획하고있는 사용자는 결과 RDD에서 persist를 호출하는 것이 좋습니다.

# 어떤 스토리지 레벨을 선택해야합니까?

Spark의 스토리지 레벨은 메모리 사용량과 CPU 효율성 간에 서로 다른 절충점을 제공합니다. 다음 과정을 거쳐 하나를 선택하는 것이 좋습니다.

* RDD가 기본 저장소 수준(MEMORY_ONLY)에 잘 맞으면 그대로 두십시오. 이것은 CPU 효율이 가장 높은 옵션으로, RDD 작업을 가능한 한 빨리 실행할 수 있습니다.

* 그렇지 않다면, MEMORY_ONLY_SER를 사용하고 객체를 훨씬 공간 효율적으로 만들 수 있는 [빠른 직렬화 라이브러리를 선택](https://spark.apache.org/docs/latest/tuning.html)하십시오. (자바와 스칼라)

* 데이터 집합을 계산하는 함수의 비용이 비싸지 않거나 대용량의 데이터를 필터링하지 않으면 디스크에 데이터를 유출하지 마십시오. 파티션을 다시 계산하는 것이 디스크에서 읽는 것보다 빠릅니다.

* 신속한 장애 복구 (예 : Spark를 사용하여 웹 응용 프로그램의 요청을 처리하는 경우)를 원할 경우 복제된 저장소 수준을 사용하십시오. 모든 스토리지 레벨은 손실 된 데이터를 다시 계산하여 완벽한 내결함성을 제공하지만 복제된 파티션은 손실된 파티션을 다시 계산하지 않고 RDD에서 작업을 계속 실행할 수 있습니다

# 데이터 제거

Spark은 각 노드의 캐시 사용을 자동으로 모니터링하고 LRU (least-recently-used) 방식으로 이전 데이터 파티션을 제거합니다. RDD를 캐시에서 빠져 나오기를 기다리지 않고 수동으로 제거하려면 RDD.unpersist () 메소드를 사용하십시오.

# 공유 변수

일반적으로 Spark 연산에 전달 된 함수 (예 : map 또는 reduce)가 원격 클러스터 노드에서 실행되면 함수에서 사용되는 모든 변수의 개별적인 복사본이 작동합니다. 이러한 변수는 각 시스템에 복사되며 원격 시스템의 변수에 대한 업데이트는 드라이버 프로그램에 전파되지 않습니다. 태스크간에 일반적인 읽기 - 쓰기 공유 변수를 지원하는 것은 비효율적입니다. 그러나 Spark는 두 가지 공통 사용 패턴, 즉 broadcast 변수 및 accumulator에 대해 두 가지 제한된 유형의 공유 변수를 제공합니다.
