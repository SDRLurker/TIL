출처 : http://stackoverflow.com/questions/24671049/query-for-a-relation-between-calendar-object-and-espers-eql

# Calendar 객체와 Esper의 EQL 관계에 관한 질문.

There is a class becoming event objects. 

객체가 되려는 다음과 같은 클래스가 있습니다.

class StockTickEvent { Calendar timestamp; ... }



Also, there is an EQL statement. 

또한, 다음과 같은 EQL 문장이 있습니다.

select * from StockTickEvent.win:ext_timed(timestamp, 10 seconds)



Is the class right or valid for this EQL statement? If so, what type can timestamp be? For example, the type of timestamp can be Calendar, Date, or long(unix time value).

이 클래스가 이 EQL 문장에 맞나요? (유효한가요?) 그렇다면 timestamp 변수 타입은 무엇이 될 수 있나요? 예를 들자면 Calendar, Date, long이 있습니다.

## 답변

The ext-timed data window takes an expression returning a long value. You could add a method to the event returning the long-msec for the calendar.

ext-timed 데이터 윈도우는 long 값을 리턴하는 표현을 취합니다. calendar(로 사용한 변수)에 대해 long-msec을 리턴하는 이벤트를 가지는 메소드를 추가해야 합니다.
