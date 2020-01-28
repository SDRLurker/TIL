출처 : [https://stackoverflow.com/questions/29523954/distinguishing-between-two-quickfix-initiator-sessions](https://stackoverflow.com/questions/29523954/distinguishing-between-two-quickfix-initiator-sessions)

# 2개의 QuickFix initiator 세션을 구분하기



저는 broker로 접속하기 위한 Python binding으로 QuickFix를 사용하고 있고 config 파일에서 2 개의 initiator 세션이 있습니다. 하나는 가격을 위한 것이고 다른 것은 주문 세션을 위한 것입니다.



제 질문은 그들 중 하나만 온라인일 때 뭔가를 하고 싶습니다. 



```python

initiator = fix.SocketInitiator(application, storeFactory, settings, logFactory)

if initiator.isLoggedOn():

    function()

```



가격이나 주문 세션이나 로그인 된 둘 다에 관련하여 `function`이 호출될 것입니다. 저는  _특정_ initiator가 로그인되어 있을 때 알아낼 수 있을까요?



### 1개의 답변 중 1개의 답변만 추려냄 

언급하신대로, 메소드 [bool Initiator::isLoggedOn()](https://github.com/quickfix/quickfix/blob/adb936f95e2cc00af21800c5793880564f2b63e2/src/C%2B%2B/Initiator.cpp#L269)는 어떤 세션이든 현재 로그인 되었는지 당신에게 알려줍니다.



특별한 세션을 확인하기 위해 [bool Initiator::isConnected( const SessionID& sessionID](https://github.com/quickfix/quickfix/blob/adb936f95e2cc00af21800c5793880564f2b63e2/src/C%2B%2B/Initiator.cpp#L178)를 사용하세요.



[SocketInitiator](https://github.com/quickfix/quickfix/blob/adb936f95e2cc00af21800c5793880564f2b63e2/src/C%2B%2B/SocketInitiator.h#L36) `Initiator`로 부터 이들 둘 다를 상속합니다.
