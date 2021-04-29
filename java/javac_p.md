**출처**

[https://stackoverflow.com/questions/19382593/how-to-compile-java-package-structures-using-javac](https://stackoverflow.com/questions/19382593/how-to-compile-java-package-structures-using-javac)

# javac를 사용하여 자바 패키지 구조를 컴파일 하는 방법

다른 패키지를 가져 오는 Java 패키지를 (명령 줄에서) 컴파일 하려고 합니다. [튜토리얼 온라인](https://www.roseindia.net/java/master-java/createsubpackage.shtml)을 따르고 있었지만 최종 자바 파일 (CallPackage.java)을 컴파일 하려고 하면 오류가 발생하는 것 같습니다.

다음은 파일 구조입니다.

```
+ test_directory (contains CallPackage.java)
   -> importpackage
       -> subpackage (contains HelloWorld.java)
```

다음은 CallPackage.java 입니다.

```
/// CallPackage.java
import importpackage.subpackage.*;
class CallPackage{
  public static void main(String[] args){
  HelloWorld h2=new HelloWorld();
  h2.show();
  }
}
```

그리도 다음은 HelloWorld.java 입니다.

```
///HelloWorld.java

package importpackage.subpackage;

public class HelloWorld {
  public void show(){
  System.out.println("This is the function of the class HelloWorld!!");
  }
}
```

## 시도했던 단계

1.  subpackage로 가서 HelloWorld.java를 `$javac HelloWorld.java`로 컴파일 하였습니다.
2.  test\_directory로 가서 CallPackage.java를 `$javac CallPackage.java`로 컴파일 하였습니다.

이는 마지막 명령에서 다음과 같은 오류를 저에게 제공하였습니다.

```
CallPackage.java:1: package importpackage.subpackage does not exist
import importpackage.subpackage.*;
^
CallPackage.java:4: cannot find symbol
symbol  : class HelloWorld
location: class CallPackage
  HelloWorld h2=new HelloWorld();
  ^
CallPackage.java:4: cannot find symbol
symbol  : class HelloWorld
location: class CallPackage
  HelloWorld h2=new HelloWorld();
                    ^
3 errors
```

두 패키지 모두 컴파일을 어떻게 할 수 있을까요? 어떠한 도움이든 감사드립니다!

---

## 6개 답변 중 1개만 추림

문제는 각 명령 (javac 및 java)에 대해 클래스 경로를 설정해야 한다는 것입니다.

시도한 단계

1.  하위 패키지로 이동하는 대신 최상위 수준에서 HelloWorld.java를 컴파일합니다.

```
$ javac -cp . importpackage/subpackage/HelloWorld.java
```

2.  같은 방법으로 CallPackage.java를 컴파일합니다.

```
$ javac -cp . CallPackage.java
```

3.  클래스 경로를 사용하여 파일을 실행하십시오.

```
$ java -cp . CallPackage
```

참고 : "$ java CallPackage"를 실행하면 "오류 : 주 클래스 CallPackage를 찾거나 로드 할 수 없습니다" 오류가 발생합니다.

요약하면 각 단계에서 클래스 경로를 지정해야 합니다. 그렇게 실행한 후에 작동했습니다.
