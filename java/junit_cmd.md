참고주소 : [https://stackoverflow.com/questions/2235276/how-to-run-junit-test-cases-from-the-command-line](https://stackoverflow.com/questions/2235276/how-to-run-junit-test-cases-from-the-command-line)

# 명령어 라인에서  JUnit test case를 실행하는 방법

저는 명령어 라인에서 JUnit test case를 실행하고 싶습니다. 어떻게 이를 할 수 있을까요?

---

## 10개 답변 중 1개만 추려냄

**JUnit 5.X**에서는

```shell
java -jar junit-platform-console-standalone-<version>.jar <Options>
```

[https://stackoverflow.com/a/52373592/1431016](https://stackoverflow.com/a/52373592/1431016) 에서 간단한 요약을 찾을 수 있고 [https://junit.org/junit5/docs/current/user-guide/#running-tests-console-launcher](https://junit.org/junit5/docs/current/user-guide/#running-tests-console-launcher) 에서 전체 세부사항을 볼 수 있습니다.

**JUnit 4.X**에서는

```shell
java -cp .:/usr/share/java/junit.jar org.junit.runner.JUnitCore [test class name]
```

**JUnit 3.X**를 사용하신다면 클래스 이름이 다릅니다.

```shell
java -cp .:/usr/share/java/junit.jar junit.textui.TestRunner [test class name]
```

윈도우즈에서는 세미콜론으로 유닉스/리눅스에서는 콜론으로 classpath로 클래스 파일을 디렉터리나 JAR를 더 추가하고 싶을 수 있습니다. 환경에 따라 다릅니다.

편집: 저는 예제로 현재 디렉터리를 추가하였습니다. 환경과 당신이 응용프로그램을 어떻게 빌드하느냐(bin/ 또는 build/ 또는 my_application.jar 등)에 의존적입니다. Java 6+는 classpath에 glob을 지원합니다. 다음처럼 할 수 있습니다.

```shell
java -cp lib/*.jar:/usr/share/java/junit.jar ...
```

도음이 되길 바랍니다. *테스트를 작성하세요!* :-)
