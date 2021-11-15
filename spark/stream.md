출처 : [https://stackoverflow.com/questions/37791685/what-is-the-use-case-for-start-awaittermination-and-stop-with-regard-to-sp](https://stackoverflow.com/questions/37791685/what-is-the-use-case-for-start-awaittermination-and-stop-with-regard-to-sp)

# 스파크 스트리밍에 관해 start(), awaitTermination(), stop()의 사용 사례는 무엇입니까?

저는 스파크 스트리밍 초보입니다. 저는 터미널에서 데이터를 추출하여 HDFS로 불러오는 하나의 응용프로그램을 개발하고 있습니다. 인터넷에서 찾아 보았지만 스트리밍 응용프로그램을 멈추는 방법을 이해할 수 없었습니다.

또한 `sc.awaittermination()`과 `sc.stop()`의 사용 사례를 저에게 설명해 주실 수 있으신가요?

감사합니다.

## 2개의 답변 중 1개의 답변

`streamingContext.awaitTermination()` --> *사용자로부터 종료 신호를 기다립니다.* 사용자로부터 신호를 받을 때(예시 CTRL+c 또는 SIGTERM) 스트리밍 context는 멈출 것입니다. 이는 java의 shutdownhook 종류입니다.

`streamingContext.stop()` 은 스트리밍 context를 바로 멈춥니다. 스파크 context에 관해 스트리밍 context에 말할 수 있습니다. 만약 스파크 context가 아니고 스트리밍 context만 멈추기를 원한다면 `streamingContext.stop(false)`를 호출할 수 있습니다.
