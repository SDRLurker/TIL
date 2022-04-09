출처 : [https://stackoverflow.com/questions/24636412/sql-using-case-in-count-and-group-by](https://stackoverflow.com/questions/24636412/sql-using-case-in-count-and-group-by)

# group by와 count에서 CASE를 사용하는 SQL

저는 테이블에서 데이터를 분류하기 위해 `CASE`를 사용하여 그들의 개수를 세었지만 결과가 정확하지 않습니다.

실제 데모는 [여기](http://sqlfiddle.com/#!2/f1f44/8) 있습니다.

```SQL
select DATE(date) as day, count(*),
count(distinct case when name = 'fruit' then 1 else 0 end) as fruits,
count(distinct case when name = 'vege' then 1 else 0 end) as vege,
count(distinct case when name = 'sweets' then 1 else 0 end) as sweets
from food
group by day
with rollup
```

문제가 `CASE`에 있는지 또는 문자열 매칭 `=`에 있는지 확실하지 않습니다. 'sweets'가 없는데 여전히 1로 계산됩니다. 어떤 지적 사항이든 감사합니다.

---------------

## 3 개의 답변 중 1개의 답변

당신의 문제는 `NULL`이 아닌 모든 결과도 세는데 `COUNT`를 사용한 것입니다. 당신은 다음처럼 사용했습니다.

```SQL
COUNT(distinct case when name = 'sweets' then 1 else 0 end)
```

그래서 이름이 `sweets`가 아닐 때, 그것을 `0`으로 세야 합니다. 게다가 `DISTINCT`를 사용했기 때문에, 하나 또는 두 개의 값을 셀 것입니다. 당신은 `SUM`을 사용하고 `DISTINCT`를 삭제하고 `ELSE 0`을 사용해야 합니다.

```SQL
SELECT  DATE(date) as day, 
        COUNT(*),
        SUM(CASE WHEN name = 'fruit' THEN 1 ELSE 0 END) as fruits,
        SUM(CASE WHEN name = 'vege' THEN 1 ELSE 0 END) as vege,
        SUM(CASE WHEN name = 'sweets' THEN 1 ELSE 0 END) as sweets
FROM food
GROUP BY DAY
WITH ROLLUP
```

또는

```SQL
SELECT  DATE(date) as day, 
        COUNT(*),
        COUNT(CASE WHEN name = 'fruit' THEN 1 ELSE NULL END) as fruits,
        COUNT(CASE WHEN name = 'vege' THEN 1 ELSE NULL END) as vege,
        COUNT(CASE WHEN name = 'sweets' THEN 1 ELSE NULL END) as sweets
FROM food
GROUP BY DAY
WITH ROLLUP
```

[여기](http://sqlfiddle.com/#!2/f1f44/10)는 수정된 sqlfiddle입니다.
