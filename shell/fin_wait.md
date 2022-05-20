출처  
[https://serverfault.com/questions/7689/how-do-i-get-rid-of-sockets-in-fin-wait1-state](https://serverfault.com/questions/7689/how-do-i-get-rid-of-sockets-in-fin-wait1-state)

## FIN_WAIT1 상태를 소켓에서 어떻게 제거하나요?

kill이 필요한 프로세스에 의해 차단된 포트가 있습니다. (충돌한 telnet 대몬). 프로세스가 성공적으로 종료되었지만 포트는 여전히 'FIN_WAIT1' 상태입니다. 그것은 나오지 않으며, 그 시간 초과는 '십년'으로 설정된 것 같습니다.

포트를 해제할 수 있는 유일한 방법은 전체 시스템을 재부팅하는 것인데, 이는 당연히 하고 싶지 않은 일입니다.

```shell
$ netstat -tulnap | grep FIN_WAIT1 
tcp        0  13937 10.0.0.153:4000         10.0.2.46:2572 
```

리부팅 없이 블록되지 않도록 포트를 처리하는 방법에 대해 아시는 분 있나요?

---

### 9개의 답변 중 1개의 답변

```shell
# tcp_max_orphans의 현재 값을 기록
original_value=$(cat /proc/sys/net/ipv4/tcp_max_orphans)

#tcp_max_orphans를 임시로 0 으로 설정
echo 0 > /proc/sys/net/ipv4/tcp_max_orphans

# /var/log/messages 확인
# 그것은 "kernel: TCP: too many of orphaned sockets"를 뱉어낼 것입니다.
# 접속이 없어지는 데 오래 걸리지 않을 것입니다. 

# 이전에 있던 tcp_max_orphans 의 값을 복구합니다.
echo $original_value > /proc/sys/net/ipv4/tcp_max_orphans

# 다음으로 검증합니다.
netstat -an|grep FIN_WAIT1
```
