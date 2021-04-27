**출처**

[https://stackoverflow.com/questions/18431857/compile-source-file-to-a-different-directory](https://stackoverflow.com/questions/18431857/compile-source-file-to-a-different-directory)

# 소스 파일을 다른 디렉터리로 컴파일?

다른 디렉터리로 자바 소스 파일 (`*.java`)를 컴파일 하는 방법이 있을까요?

만약 제 패키지 파일 구조가 다음과 같습니다.

```
Mathematics ->
  Formulas ->
    src ->
      // 수학 공식을 포함하는 소스 파일...
    bin ->
      // 수학 공식을 포함하는 class 파일...
  Problems ->
    src ->
      // 수학 문제를 포함하는 소스 파일...
    bin ->
      // 수학 문제를 포함하는 class 파일...
```

저는 소스와 class 파일을 폴더 구조를 유지한 채로 분리하고 싶고, 컴파일 할 때마다 `src` 폴더를 `bin` 폴더로 모든 class 파일을 복사해야 합니다.

`javac` 명령어로 다른 폴더로 클래스 파일을 컴파일함으로써 이 과정을 간단하게 하는 방법이 있을까요?

---

## 1개의 답변

옙, 절대적으로 출력 디렉터리를 지정하기 위해 `-d` 옵션을 사용합니다.

```shell
javac -d bin src/foo/bar/*.java
```

당신이 지정한 디렉터리는 출력 구조의 *루트* 입니다. 관련 하위 디렉토리는 코드의 패키지 구조에 따라 자동으로 생성됩니다.

자세한 내용은 [javac 문서](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html)를 참조하십시오.

이 경우 하나의 `javac` 명령을 실행하여 공식 디렉터리를 컴파일하고 다른 하나는 문제 디렉터리를 컴파일 해야 합니다. 잠재적으로 문제 디렉터리를 컴파일 할 때 클래스 경로의 일부로 공식 `bin` 디렉토리를 사용합니다.

(단일 소스 구조를 사용하지만 다른 패키지를 사용하는 것을 고려할 수도 있습니다. IDE를 사용하여 이러한 복잡성 중 일부를 숨기는 것도 고려해야 합니다. 실제로는 *어렵지* 않더라도 이 모든 작업을 수작업으로 수행하는 데 지치게 됩니다.)
