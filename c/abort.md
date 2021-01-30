출처 : [https://stackoverflow.com/questions/3413166/when-does-a-process-get-sigabrt-signal-6](https://stackoverflow.com/questions/3413166/when-does-a-process-get-sigabrt-signal-6)

# 언제 프로세스가 SIGABRT (시그널6)을 얻습니까?

프로세스가 C++에서 SIGABRT를 얻는 시나리오에는 어떤 것이 있습니까? 이 시그널은 프로세스 내에서만 항상 오는 건지 아니면 이 시그널이 한 프로세스에서 다른 데로 보내질 수 있나요?

어떤 프로세스가 이 시그널을 보내는지 확인하는 방법이 있습니까?

------

## 8개의 답변 중 1 개의 답변만 추려냄.

abort()는 호출한 프로세스에게 SIGABRT 시그널을 보내고 이는 abort()가 기본적으로 작동하는 방법입니다.

보통 abort()는 내부 오류 또는 심각하게 깨진 제약 조건을 감지하는 라이브러리 함수에 의해 호출됩니다. 예를 들어 malloc()은 내부 구조가 힙 오버플로에 의해 손상된 경우 abort()를 호출합니다.
