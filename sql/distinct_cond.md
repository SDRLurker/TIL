출처 : [https://stackoverflow.com/questions/14048098/count-distinct-with-conditions](https://stackoverflow.com/questions/14048098/count-distinct-with-conditions)

# 조건과 함께 중복제거 개수 세기(COUNT DISTINCT with CONDITIONS)

저는 특정 조건으로 열 이름으로 고유한 항목의 수를 계산하고 싶습니다. 예를 들어 다음과 같은 테이블이 있습니다.

```
tag | entryID
----+---------
foo | 0
foo | 0
bar | 3
```

고유 태그 수를 "태그 수"로 계산하고 항목 ID가 0보다 큰 고유 태그 수를 동일한 테이블에서 "양수 태그 수"로 계산하려면 어떻게 해야 합니까?

이제 두 번째 테이블에서 entryID가 0보다 큰 행만 선택한 두 개의 다른 테이블에서 계산하고 있습니다. 이 문제를 해결할 수 있는 더 간결한 방법이 있어야 한다고 생각합니다.

---

## 5개의 답변 중 1개

다음을 시도할 수 있습니다.

```SQL
select
  count(distinct tag) as tag_count,
  count(distinct (case when entryId > 0 then tag end)) as positive_tag_count
from
  your_table_name;
```

첫번째 `count(distinct...)`는 쉽습니다. 두번째는 다소 복잡해 보이지만, `case...when` 구문을 사용한 것만 제외하면 첫번째 것과 똑같습니다. `case...when` 구문에서 양수 값만 필터링합니다. 0과 음수 값은 `null`로 평가되며 개수를 계산하지 않습니다.

여기서 주목해야 할 점은 테이블을 한 번만 읽으면 이 작업을 수행할 수 있다는 것입니다. 같은 표를 두 번 이상 읽어야 할 것 같지만 실제로는 대부분의 시간에 한 번에 읽을 수 있습니다. 결과적으로 더 적은 입출력으로 작업을 훨씬 빠르게 완료합니다.
