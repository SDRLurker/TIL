출처 : [https://stackoverflow.com/questions/49774027/adding-password-to-logon-message-with-quickfix](https://stackoverflow.com/questions/49774027/adding-password-to-logon-message-with-quickfix)

# QuickFix에서 로그온 메세지에 비밀번호 추가하기

안녕하세요. 저는 Quick Fix python에서 문제가 있습니다. 저는 거래소가 요구하는 Tag 554를 거래소로 보낼 로그온 메세지에 추가할 필요가 있지만, 어떻게 해야 할 지 잘 모르겠습니다. 내가 찾은 모든 온라인 예제는 C++ 코드이며 이를 Python으로 번역하려는 시도는 성공하지 못했습니다.

누군가가 비밀번호 태그로 로그온 메세지를 보내는 방법을 조언해 주신다면 감사하겠습니다.

```python
 def toAdmin(self, sessionID, message):
        message.getHeader().setField(554, "password")
```

태그 : [`python`](https://stackoverflow.com/questions/tagged/python) [`quickfix`](https://stackoverflow.com/questions/tagged/quickfix) [`fix-protocol`](https://stackoverflow.com/questions/tagged/fix-protocol)

---

### 1개의 답변

당신의 코드는 거의 정확합니다. 당신은 실제로 당신이 그것을 실행하는 것에 대해 말하지 않았으므로 나는 당신이 그것에 대해 잘못되었다고 생각하는 것을 100% 확신할 수 없습니다.

그러나 한 가지 개선해야 할 사항이 있습니다. 로그온 메시지에만 암호를 설정하려는 것입니다.

```python
def toAdmin(self, sessionID, message):
        if message.getHeader().getField(35) == "A":
                message.getHeader().setField(554, "password")
```

(Python 구문 오류를 용서하십시오. 제가 잘 아는 언어가 아닙니다.)

이것은 다른 QF 포트에서 수행하는 작업과 매우 유사합니다. 예를 들어, C# 방식에 대한 [QuickFIX/n 사용자 FAQ](https://github.com/connamara/quickfixn/wiki/User-FAQ)를 참조하십시오.
