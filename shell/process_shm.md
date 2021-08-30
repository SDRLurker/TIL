출처 : [http://stackoverflow.com/questions/5658568/how-to-list-processes-attached-to-a-shared-memory-segment-in-linux](http://stackoverflow.com/questions/5658568/how-to-list-processes-attached-to-a-shared-memory-segment-in-linux)

# 리눅스에 공유메모리에 접근한 프로세스의 목록을 아는 방법?

공유 메모리에 접근한 프로세스가 무엇인지 어떻게 알 수 있습니까?

```shell
awagner@tree:/home/awagner$ ipcs -m

------ Shared Memory Segments --------
key        shmid      owner      perms      bytes      nattch     status      
0x00000000 0          root       777        102400     1                       
0x00000000 32769      root       774        96         1          dest         
0x00000000 98306      awagner    600        393216     2          dest         
0x00000000 131075     awagner    600        393216     2          dest    
```

예시. 98305 공유메모리에 접근한 2개의 프로세스를 어떻게 알 수 있습니까?

---

## 5개의 답변 중 1개의 답변

표준 툴로는 이것을 알 수 없다고 생각합니다. 당신은 마지막으로 접근하거나 공유메모리와 분리된 프로세스의 ID를 `ipcs -mp` 를 사용하여 알 수 있지만 `ipcs`로 접근한 모든 프로세스를 아는 방법을 저는 모릅니다.

공유메모리에 접근한 두개의 프로세스가 현재도 공유메모리에 붙어있다고 가정하면 공유메모리를 생성한 PID `cpid`와 마지막에 접근한 PID `lpid`를 통하여 두개의 프로세스를 알 수는 있지만 그 이상으로 확장하여 알 수는 없기 때문에 유용함이 제한됩니다.

`cat /proc/sysvipc/shm` 방법은 비슷하게 제한적이지만 `/proc` 파일 시스템의 다른 부분으로 프로세스의 목록을 아는 방법이 있다고 믿습니다. 아래를 보시면,

모든 프로세스에 대응하는 `procfs`(파일시스템)에서 `grep`을 할 때, `cpid`와 `lpid`의 프로세스들을 포함하는 목록을 얻었습니다.

예를 들어, 다음 공유메모리를 `ipcs -m` 명령으로 얻었습니다.

```shell
------ Shared Memory Segments --------
key        shmid      owner      perms      bytes      nattch     status      
0x00000000 123456     pax        600        1024       2          dest
```

그리고 주어진 공유메모리 id (123456)을 통해 `ipcs -mp`로 `cpid`는 3956 이고 `lpid`는 9999 라는 것을 알아내었습니다. 

그리고 명령어 `grep 123456 /proc/*/maps` 을 통해 다음을 알 수 있습니다.

```shell
/proc/3956/maps: blah blah blah 123456 /SYSV000000 (deleted)
/proc/9999/maps: blah blah blah 123456 /SYSV000000 (deleted)
```

그래서 이것이 공유메모리에 접근한 프로세스 목록을 얻는 방법입니다. `dest` 상태와 `(deleted)` 표시가 마지막 분리가 발생했을 때 삭제를 위한 표시를 생성자가 했기 때문에 이미 그 공유메모리가 파괴된 것이 아님을 저는 매우 확신합니다.

`/proc/*/maps` 파일들을 검사함으로서, 당신은 주어진 공유메모리에 현재 접근하고 있는 PID들을 발견할 수 있습니다.

---

출처 : [http://www.linuxforums.org/forum/red-hat-fedora-linux/168472-unable-remove-shared-memory.html](http://www.linuxforums.org/forum/red-hat-fedora-linux/168472-unable-remove-shared-memory.html)

## 안 지워지는 공유메모리 삭제하는 방법

저 같은 경우에는 위의 방법으로 접근한 프로세스의 목록을 얻은 다음 해당 프로세스를 kill하여 문제를 해결하였습니다.

다음 내용은 위의 출처에서 답변만 번역 하였습니다.

man 페이지는 ipcrm -m <id>는 삭제를 위해 표시만을 한다고 쓰여 있습니다. ipcs -p는 공유메모리에 접근한 프로세스 id를 제공합니다. 해제하고자 하는 공유메모리에 접근하고 있는 프로세스를 kill하면 그 공유메모리가 사라진 것을 확인하실 수 있습니다.
