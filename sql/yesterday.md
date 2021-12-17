출처 : [https://stackoverflow.com/questions/13572083/grab-where-curdate-and-the-day-before-with-mysql](https://stackoverflow.com/questions/13572083/grab-where-curdate-and-the-day-before-with-mysql)

# MySQL로 어제 날짜와 CURDATE() 얻기

```SQL
offers.date = CURDATE()
```

제가 가지고 있는 쿼리입니다.

CURDATE()는 오늘을 얻지만, 어제 값에 대한 주문도 얻고 싶습니다.

어제 날짜를 지정하지 않고 이를 어떻게 할 수 있을까요?

---

## 4개의 답변 중 1개

CURDATE를 interval(예시 어제) 값으로 빼기 혹은 더하기를 사용하기 위해 당신은 [DATE_ADD](https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_date-add) 함수를 사용할 수 있습니다.

```SQL
SELECT DATE_ADD(CURDATE(), INTERVAL -1 DAY);
```

당신의 쿼리같은 경우, 다음처럼 사용할 수 있습니다.

```SQL
WHERE offers.date = CURDATE() OR offers.date = DATE_ADD(CURDATE(), INTERVAL -1 DAY)
```

부가적으로 당신은 음수 간격을 사용하는 대신에 같은 의미로 양수 간격을 사용하는 DATE_SUB() 함수도 사용할 수 있습니다.

그러므로 `DATE_ADD(CURDATE(), INTERVAL -1 DAY)`는 `DATE_SUB(CURDATE(), INTERVAL 1 DAY)`가 될 수 있습니다.
