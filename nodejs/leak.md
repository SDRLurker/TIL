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

## 12개의 답변 중 1개의 답변만 추려냄.

기본으로 최대 10개의 listener가 하나의 이벤트마다 등록될 수 있습니다. 

다음이 당신의 코드라면 maxListeners를 다음을 통해 지정할 수 있습니다.

```javascript
const emitter = new EventEmitter()
emitter.setMaxListeners(100)
// or 0 to turn off the limit
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
