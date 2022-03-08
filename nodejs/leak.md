출처 : [https://stackoverflow.com/questions/9768444/possible-eventemitter-memory-leak-detected](https://stackoverflow.com/questions/9768444/possible-eventemitter-memory-leak-detected)

# 가능한 EventEmitter 메모리 누수 감지됨

다음 warning이 나왔습니다.

```shell
(node) warning: possible EventEmitter memory leak detected. 11 listeners added. Use emitter.setMaxListeners() to increase limit.
Trace: 
    at EventEmitter.<anonymous> (events.js:139:15)
    at EventEmitter.<anonymous> (node.js:385:29)
    at Server.<anonymous> (server.js:20:17)
    at Server.emit (events.js:70:17)
    at HTTPParser.onIncoming (http.js:1514:12)
    at HTTPParser.onHeadersComplete (http.js:102:31)
    at Socket.ondata (http.js:1410:22)
    at TCP.onread (net.js:354:27)
```

저는 server.js에 다음처럼 코드를 작성하였습니다.

```javascript
http.createServer(
    function (req, res) { ... }).listen(3013);
```

이를 어떻게 고칠 수 있을까요?

---

## 21개의 답변 중 3개의 답변


이것은 [노드 eventEmitter 문서](https://nodejs.org/docs/latest/api/events.html#events_emitter_setmaxlisteners_n)에 설명되어 있습니다.

노드의 버전은 무엇입니까? 어떤 다른 코드가 있습니까? 정상적인 행동이 아닙니다.

간단히 말해서: `process.setMaxListeners(0);`

또한 다음도 참조하세요: [node.js - 요청 - "emitter.setMaxListeners()" 방법](https://stackoverflow.com/questions/8313628/node-js-request-how-to-emitter-setmaxlisteners)?

---

경고가 있는 데는 이유가 있으며 올바른 수정이 제한을 늘리지 않고 동일한 이벤트에 그렇게 많은 리스너를 추가하는 이유를 알아내는 좋은 기회가 있음을 여기서 지적하고 싶습니다. 왜 그렇게 많은 리스너가 추가되고 있는지 알고 있고 그것이 정말로 원하는 것이라고 확신하는 경우에만 제한을 늘리십시오.

이 경고를 받았기 때문에 이 페이지를 찾았고 제 경우에는 전역 개체를 EventEmitter로 바꾸는 버그가 사용 중이었기 때문에 이 페이지를 찾았습니다! 이러한 것들이 눈에 띄지 않게 하고 싶지 않기 때문에 전역으로 한도를 늘리지 않는 것이 좋습니다.

---

기본으로 최대 10개의 listener가 하나의 이벤트마다 등록될 수 있습니다. 

다음이 당신의 코드라면 maxListeners를 다음을 통해 지정할 수 있습니다.

```javascript
const emitter = new EventEmitter()
emitter.setMaxListeners(100)
// 0은 한계를 제한하지 않습니다.
emitter.setMaxListeners(0)
```

만약 다음이 당신의 코드가 아니라면 당신은 전역으로 그 수를 늘릴 수 있습니다.

```javascript
require('events').EventEmitter.prototype._maxListeners = 100;
```

당연히 당신은 한계를 없앨 수 있지만 주의해야 합니다.

```javascript
// 기본으로 한계를 없앤다. (주의하세요.)
require('events').EventEmitter.prototype._maxListeners = 0;
```

추신. 코드는 당신의 응용프로그램의 아주 시작에 와야 합니다.

추가: node 0.11부터 이는 기본 한계를 변경하여 작동합니다.

```javascript
require('events').EventEmitter.defaultMaxListeners = 0
```
