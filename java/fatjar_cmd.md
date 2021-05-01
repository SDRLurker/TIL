**출처**

[https://dzone.com/articles/java-8-how-to-create-executable-fatjar-without-ide](https://dzone.com/articles/java-8-how-to-create-executable-fatjar-without-ide)

# 명령줄로 실행 가능한 Fat JAR 생성하기

명령줄 만으로 실행 가능한 fat JAR 파일을 만들고 실행하고 싶으십니까? 필요한 기초 작업과 수행 방법을 확인하십시오.

이 글은 추가 플러그인, IDE 또는 다른 도구를 사용하지 않고 순수한 명령 줄과 Java에서 Fat JAR (Java 아카이브 파일)을 만들 수 있는 가능성을 검토하는 내 블로그 게시물을 통합한 내용입니다.

빌드 도구 ([Ant](http://ant.apache.org/), [Maven](https://maven.apache.org/) 또는 [Gradle](https://gradle.org/))의 세계에서는 명령줄에 대해 생각하는 것이 유용하지 않을 수도 있습니다. 가장 유명한 IDE (IntelliJ, Eclipse 또는 NetBeans)는 빌드 도구와 구현을 즉시 제공합니다. 그러나 명령줄 만 있고 인터넷에 액세스 할 수 없다고 가정해 보겠습니다.

그러면 어떻게 하시겠습니까?

## 파트 1: ExecutableOne.jar 컴파일 ([GitHub](https://github.com/mirage22/executable-one))

이 첫 번째 부분의 목표는 실행 가능한 JAR 파일을 만드는 것입니다. ExecutableOne.jar 이라고 부르겠습니다. 명령줄을 열어서 간단한 프로젝트 폴더를 생성해 보겠습니다. 예제 프로젝트 구조는 [Maven 표준 디렉토리 레이아웃](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) 구조를 따릅니다.

```
./libs
./out
./README.md
./src
./src/main
./src/main/java
./src/main/java/com
./src/main/java/com/exec
./src/main/java/com/exec/one
./src/main/resources
```

우리의 의도는 실행 가능한 JAR 파일을 만드는 것이므로 기본 클래스를 만들어야 합니다. com.exec.one 패키지에서 해봅시다. 패키지는 샘플 프로젝트 구조의 _SRC/MAIN/JAVA_ 폴더에서 찾을 수 있습니다.

```java
package com.exec.one;

public class Main {
    public static void main(String[] args){                                                                                 
        System.out.println("Main Class Start");                     
    }                                                      
}
```

_SRC/MAIN/RESOURCES_ 폴더 안에 META-INF 폴더를 만든 다음 그 안에 MANIFEST.FM 파일을 배치합니다. 새로 생성된 MANIFEST.FM 파일을 열고 기본 설명을 입력해 보겠습니다.

```
Manifest-Version: 1.0   
Class-Path: .                                                                                                                                                                          
Main-Class: com.exec.one.Main
```

**참고** : JAR 파일 당 하나의 MANIFEST.FM 파일만 있습니다.

MANIFEST.FM 파일에는 JAR 파일이 사용되는 방법에 대한 세부 사항이 포함되어 있습니다. 자세한 내용은 다루지 않습니다. 정의한 옵션에 집중하겠습니다.

1.  Manifest-Version : manifest 파일 버전입니다.
    
2.  클래스 경로 : 애플리케이션 또는 확장 클래스 로더는 이 속성 값을 사용하여 내부 검색 경로를 구성합니다. 원래 클래스 로더는 검색 경로에서 각 요소를 다운로드하고 엽니다. 이러한 목적을 위해 간단한 선형 검색 알고리즘이 사용되었습니다.
    
3.  Main-Class : 시작시에 런처가 로드할 클래스의 이름이 있습니다.
    

이제 \*.jar 라이브러리없이 JAR 파일을 생성합니다. 프로젝트 구조의 _LIBS_ 폴더는 여전히 비어 있습니다. 이렇게 하려면 먼저 javac를 사용하여 프로젝트를 컴파일해야합니다. 한편 출력은 _OUT_ 폴더에 저장합니다. 명령 줄로 돌아가서 프로젝트 루트 안에 다음을 입력해 보겠습니다.

```shell
$javac -cp ./src/main/java ./src/main/java/com/exec/one/*.java -d ./out/
```

프로젝트가 _OUT_ 디렉터리로 컴파일되었습니다. ls 명령을 사용하여 확인할 수 있습니다.

두 번째 단계는 _OUT_ 디렉터리에 있는 리소스에서 실행 가능한 JAR 파일을 만드는 것입니다. 명령줄로 돌아가서 다음 명령을 실행합니다.

```shell
$jar cvfm ExecutableOne.jar ./src/main/resources/META-INF/MANIFEST.MF -C ./out/ .
```

우리가 사용한 JAR 도구 옵션을 간단히 검토 및 설명해 보겠습니다.

-   c : 새 JAR 파일을 만들려고 함을 나타냅니다.
-   v : 표준 출력에 대한 자세한 출력을 생성합니다.
-   f : 생성 할 jar 파일을 지정합니다.
-   m : 우리가 사용하는 매니페스트 파일을 나타냅니다. 매니페스트 파일에는 이름-값 쌍이 포함됩니다.
-   \-C : 디렉토리에 대한 임시 변경을 나타냅니다. 이 디렉토리에서 JAR 파일로 클래스가 추가됩니다. 점은 모든 클래스 (파일)를 나타냅니다.

최종 출력을 위해 명령줄을 열고 다음을 입력합니다.

```shell
$java -jar ./ExecutableOne.jar

standard output: 
Main Class Start
```

잘 했습니다! 파트 2로 이동하겠습니다.
