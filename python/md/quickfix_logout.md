출처 : [https://stackoverflow.com/questions/24157767/how-to-logout-fix-session](https://stackoverflow.com/questions/24157767/how-to-logout-fix-session)

# fix 세션을 로그아웃하는 방법?

저는 quickfixj를 사용하고 있습니다. 저는 "EndTrade" 같은 사용자 정의 메세지가 있고 그 메세지를 받을 때 fix session을 종료하고 싶습니다? 어떻게 할 수 있을까요? 저는 그것을 할 수 있는 방법을 찾을 수 없었습니다. `new Session().logout()`을 사용하는 것은 안 됩니다.

---

## 1개의 답변

제 질문의 답변을 얻었습니다. 당신은 다음 방법으로 fix session을 로그아웃 할 수 있습니다.

```java
Session.lookupSession(sessionID).logout();
```
