출처 : [https://stackoverflow.com/questions/19461744/how-to-make-parent-wait-for-all-child-processes-to-finish](https://stackoverflow.com/questions/19461744/how-to-make-parent-wait-for-all-child-processes-to-finish)

# 부모가 모든 자식 프로세스가 끝날 때까지 기다리는 방법?

저는 fork를 계속하기 전에 부모가 *모든* 자식 프로세스가 끝날 때까지 기다리는 방법을 밝힐 수 있기를 바랍니다. 실행하려는 정리 코드가 있지만 이러한 일이 발생하기 전에 자식 프로세스가 반환되어야 합니다.

```c
for (int id=0; id<n; id++) {
  if (fork()==0) {
    // 자식
    exit(0);
  } else {
    // 부모
    ...
  }
  ...
}
```

## 3개의 답변 중 1개의 답변

```c
pid_t child_pid, wpid; 
int status = 0; 

// 아버지 코드 (자식 프로세스가 시작하기 전에) 

for (int id=0; id<n; id++) { 
    if ((child_pid = fork()) == 0) { 
        // 자식 코드
        exit(0); 
    } 
} 

while ((wpid = wait(&status)) > 0); // 이 방법으로 아버지는 모든 자식 프로세스를 기다립니다.

// 아버지 코드 (모든 자식 프로세스가 끝난 후에)
```

[`wait`](http://man7.org/linux/man-pages/man2/wait.2.html) 는 하위 프로세스가 종료 될 때까지 대기하고 해당 하위 프로세스의 `pid`를 리턴합니다. 오류가 발생하면 (예 : 하위 프로세스가 없는 경우) `-1`이 반환됩니다. 따라서 기본적으로 코드는 `wait`(대기) 오류가 끝날 때까지 자식 프로세스가 완료될 때까지 계속 기다렸다가 모두 완료되었음을 알 수 있습니다.
