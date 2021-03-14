출처 : [https://stackoverflow.com/questions/4796872/how-to-do-a-full-outer-join-in-mysql](https://stackoverflow.com/questions/4796872/how-to-do-a-full-outer-join-in-mysql)

## MySQL에서 FULL OUTER JOIN 하는 방법?

저는 MySQL에서 Full Outer Join을 하고 싶습니다. 이것이 가능할까요? MySQL에 의해 Full Outer Join이 지원되나요?

### 14개의 답변 중 1개의 답변만 추려냄

MySQL에 FULL JOIN 구문을 없습니다만 확실히 FULL JOIN을 똑같이 할 수 있습니다.

다음 코드 샘플은 두개의 테이블 t1, t2에 대해 당신이 질문한 내용을 작성한 내용입니다.

```SQL
SELECT * FROM t1
LEFT JOIN t2 ON t1.id = t2.id
UNION
SELECT * FROM t1
RIGHT JOIN t2 ON t1.id = t2.id
```

FULL OUTER JOIN 연산의 특별한 경우 위 쿼리는 중복된 행을 만들지 않습니다. 위 쿼리는 UNION set 연산을 하는데 쿼리 패턴에 의해 소개된 중복된 열을 제거합니다. 우리는 2번째 쿼리(방법으)로 anti-join 패턴을 사용하여 중복된 행을 제거할 수 있고 두 개의 집합을 함치기 위해 UNION ALL을 사용할 수 있습니다. 더 일반적인 경우로 FULL OUTER JOIN은 중복된 행을 리턴한다면 이것을 할 수 있습니다.

```SQL
SELECT * FROM t1
LEFT JOIN t2 ON t1.id = t2.id
UNION ALL
SELECT * FROM t1
RIGHT JOIN t2 ON t1.id = t2.id
WHERE t1.id IS NULL
```
