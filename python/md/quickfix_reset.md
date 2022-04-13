출처 : [https://stackoverflow.com/questions/33114855/reset-sequence-number-in-quickfix](https://stackoverflow.com/questions/33114855/reset-sequence-number-in-quickfix)

# quickfix에서 시퀀스 번호 리셋

나는 매주의 시작을 제외하고 로그온 시 시퀀스 번호를 리셋하지 않도록 브로커로 작업하고 있습니다. 그러나 시퀀스 번호가 이상하면 로그온 메시지의 태그 141을 사용하여 시퀀스 번호 리셋을 요청해야 합니다. 분명히 시퀀스 번호가 너무 낮아 거부된 로그온이 거부되었는지 확인하고 onlogon에 태그를 설정할 수 있습니다. 그러나 quickfix에 시퀀스 번호를 리셋해야 한다고 어떻게 알릴 수 있습니까? 이것은 시퀀스 번호에 영향을 미치는 연결 문제가 자주 발생하지 않기 때문에 테스트하기 어려운 기능입니다.

저는 quickfix의 C++ 버전을 사용합니다

------

## 1개의 답변

당신은 `LOGOUT` 메세지에서 적절한 메세지를 모니터해야 합니다. "msgseqnum이 너무 작다" 같은 뭔가가 발생하면, 다음 `LOGON` 메세지에서 리셋하도록 플래그를 설정해야 합니다. 이는 `FIX::Application::fromAdmin` 구현에서 합니다.

그리고 나서 당신의 `FIX::Application::toAdmin` 구현에서 메세지에 `LOGON`이 있는지 확인하고 리셋 플래그를 설정합니다. 그렇다면 당신의 세션(`FIX::Session::lookupSession`)을 찾아 `setNextSenderMsgSeqNum(1)`와 `setNextTargetMsgSeqNum(1)`를 호출합니다. 또한 당신의 플래그를 리셋합니다 :)
