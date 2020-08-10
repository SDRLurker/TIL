출처 : [https://stackoverflow.com/questions/43995886/gradle-could-not-target-platform-java-se-8-using-tool-chain-jdk-7-1-7/44000209](https://stackoverflow.com/questions/43995886/gradle-could-not-target-platform-java-se-8-using-tool-chain-jdk-7-1-7/44000209)

# Gradle - Could not target platform: 'Java SE 8' using tool chain: 'JDK 7 (1.7)'

저는 Intellij Idea에서 Gragle 프로젝트를 불러오려고(import) 시도하였고 다음과 같은 메세지가 나왔습니다. `Gradle - Could not target platform: 'Java SE 8' using tool chain: 'JDK 7 (1.7)'` 누구든지 이렇게 나온 이유가 무엇인지 설명해 주시겠습니까?

---

## 7 개의 답변 중 1 개의 답변만 추려냄.

저는 이렇게 해결 하였습니다. (Intellij Idea 2018.1.2)

1) 파일-> 설정-> 빌드, 실행, 배포-> 빌드 도구-> Gradle로 이동합니다.
File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle

2) Gradle JVM : 버전 1.8로 변경

3) Gradle 작업 다시 실행
