출처 : [https://stackoverflow.com/questions/3125645/why-use-select-instead-of-sleep](https://stackoverflow.com/questions/3125645/why-use-select-instead-of-sleep)

# 왜 sleep() 대신에 select()를 사용하나요?

저는 iPhone 오디오에 대한 장을 살펴보고 있는데 이해할 수 없는 코드 섹션을 발견했습니다.

```c
while (aqc.playPtr < aqc.sampleLen) 
{
    select(NULL, NULL, NULL, NULL, 1.0);
}
```

([전체 코드 샘플은 163-166페이지에 있습니다](http://oreilly.com/catalog/9780596523190/preview)). 코드에 대해 내가 이해한 바에 따르면 오디오가 다른 스레드에서 처리되고 있고 while 루프는 오디오가 여전히 처리되는 동안 메인 스레드가 종료되는 것을 방지하기 위한 것입니다.

제가 이해할 수 없는 것은 왜 `select()`가 `sleep()` 대신에 사용되었는지 입니다.


내가 읽은 것에서 `select()`는 I/O의 변경 사항을 모니터링하는 데 사용되며 NULL을 전달해도 의미가 없습니다. `sleep()`을 사용하여 코드를 실행했으며 예상대로 작동합니다. (저수준 POSIX에 대한 나의 지식은 거의 존재하지 않습니다.)

------

## 6개의 답변 중 1 개의 답변

정확한 1초 미만 대기 허용을 선택하고 sleep 보다 이식성이 좋습니다. 대기하는 다른 방법이 있는데 [이 질문](https://stackoverflow.com/questions/264350/is-there-an-alternative-for-sleep-in-c)을 참조하십시오.

그러나 select의 timeout 매개변수는 float가 아니라 struct timeval에 대한 포인터여야 합니다. 나는 당신이 보여주는 코드가 심지어 컴파일된다는 것에 놀랐습니다. More : 이 이상한 조건부 선택 다음에 무조건적인 sleep(1)이 뒤따릅니다. 무의미해 보입니다.
