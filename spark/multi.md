출처 : [https://web.archive.org/web/20201025213055/https://bitdatatechie.com/2019/09/13/spark-journal-return-multiple-dataframes-from-a-scala-method/](https://web.archive.org/web/20201025213055/https://bitdatatechie.com/2019/09/13/spark-journal-return-multiple-dataframes-from-a-scala-method/)

# Spark Journal: Scala 메소드로부터 여러 개의 dataframe을 리턴

지금까지, 저는 Spark에 한해서 글을 남기는 데 집중하였지만, Spark Framework를 사용할 때 사용되는 주요 언어 중 하나는 당신이 알 듯이 Scala입니다. 흥미로운 사용 사례를 보여주기 위해 Spark API와 Scala 언어 모두 사용할 것입니다.

이번 작업은 Scala 메소드로부터 여러 개의 dataframe을 리턴하는 것입니다. Int, String, Dataframe일 수 있는 리턴 값이 있을 때 메소드의 리턴 부분에 1개의 값만으로 이 작업을 해왔습니다.  
저의 동료와 Architect는 이를 매우 쉽게 할 수 있는 다른 옵션을 저에게 보여주었고 도움이 되었습니다.

더 읽기 전에 StackOverflow의 이 게시물을 살펴 보는 것이 좋습니다. 이 방법은 **Scala에서 List와 Tuple의 개념적 차이**를 분명히 하는 데 도움이 됩니다.

**접근 1**  

_리턴 값으로 List를 사용_

```scala
import org.apache.spark.sql.DataFrame

def returMultipleDf  : List[DataFrame] = {
    val dataList1 = List((1,"abc"),(2,"def"))
    val df1 = dataList1.toDF("id","Name")

    val dataList2 = List((3,"ghi","home"),(4,"jkl","ctrl"))
    val df2 = dataList2.toDF("id","Name","Type")

    List(df1, df2)

}

val dfList = returMultipleDf 
val dataFrame1 = dfList(0)
val dataFrame2 = dfList(1)

dataFrame2.show

+---+----+----+
| id|Name|Type|
+---+----+----+
|  3| ghi|home|
|  4| jkl|ctrl|
+---+----+----+
```

**접근 2**

_리턴 값으로 Tuple을 사용_

```scala
import org.apache.spark.sql.DataFrame

def returMultipleDf : (DataFrame, DataFrame) = {
    val dataList1 = List((1,"abc"),(2,"def"))
    val df1 = dataList1.toDF("id","Name")

    val dataList2 = List((3,"ghi","home"),(4,"jkl","ctrl"))
    val df2 = dataList2.toDF("id","Name","Type")

    (df1, df2)

}

val (df1, df2) = returMultipleDf


df2.show

+---+----+----+
| id|Name|Type|
+---+----+----+
|  3| ghi|home|
|  4| jkl|ctrl|
+---+----+----+
```

개인적으로 접근 2를 선호합니다. Tuple을 사용하는 자체 장점이 있고 List와 비교할 때 더 유연하기 때문입니다.
