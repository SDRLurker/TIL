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
    - 셔플 연산
      - 배경
      - 성능 영향
  + RDD 지속성
    - 어떤 스토리지 레벨을 선택해야합니까?
    - 데이터 제거
* 공유 변수
  + 브로드 캐스트 변수
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
