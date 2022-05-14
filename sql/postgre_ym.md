출처 : [https://stackoverflow.com/questions/17492167/group-query-results-by-month-and-year-in-postgresql](https://stackoverflow.com/questions/17492167/group-query-results-by-month-and-year-in-postgresql)

# postgresql에서 년월로 결과 쿼리를 그룹화하기

Postgres 서버에 다음과 같은 데이터베이스 테이블이 있습니다.

```
id      date          Product Sales
1245    01/04/2013    Toys    1000     
1245    01/04/2013    Toys    2000
1231    01/02/2013    Bicycle 50000
456461  01/01/2014    Bananas 4546
```

저는 다음처럼 `Sales`의 년월로 결과를 그룹화한 `합계`를 제공하는 쿼리를 만들고 싶습니다.

```
Apr    2013    3000     Toys
Feb    2013    50000    Bicycle
Jan    2014    4546     Bananas
```

이를 할 수 있는 간단한 방법이 있을까요?

---

## 7개의 답변 중 1개

저는 채택한 답변이 많은 좋아요 추천을 받았다는 것을 믿을 수 없습니다. -- 끔직한 방법입니다.

[data\_trunc](http://www.postgresql.org/docs/current/static/functions-datetime.html#FUNCTIONS-DATETIME-TRUNC)로 이를 할 수 있는 올바른 방법입니다.

```SQL
   SELECT date_trunc('month', txn_date) AS txn_month, sum(amount) as monthly_sum
     FROM yourtable
 GROUP BY txn_month
```

간단한 쿼리에서는 나쁜 방법이지만 다음처럼 사용할 수도 있습니다.

```SQL
 GROUP BY 1
```

만약 날짜를 select하지 않는다면 다음처럼 사용할 수 있습니다.

```SQL
 GROUP BY date_trunc('month', txn_date)
```
