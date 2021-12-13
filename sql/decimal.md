출처 : [https://stackoverflow.com/questions/50494508/redshift-division-result-does-not-include-decimals](https://stackoverflow.com/questions/50494508/redshift-division-result-does-not-include-decimals)

# Redshift의 나누기 결과는 소수점을 포함하지 않습니다.

저는 Redshift에서 2개의 열을 나눈 퍼센트를 계산하기 위해 매우 기초적인 연산을 시도하였습니다. 하지만, 제가 다음 쿼리를 실행하였을 때 그 결과는 그냥 0이 나오고 소수점이 커버되지 않습니다.

코드:
```SQL
select 1701 / 84936;
```

결과:
| |컬럼?|
|-|-|
|1|0|

이것도 시도했는데
```SQL
select cast(1701 / 84936 as numeric (10,10));
```

결과는 `0.0000000000` 이었습니다.

어떻게 이를 해결할 수 있을까요?

---

## 2개의 답변 중 1개

위는 정수 나누기입니다. 하나의 값이 적어도 `NUMERIC`(정확한 데이터 타입)나 `FLOAT`(주의: 이는 근사의 데이터 타입) 타입임을 보장해야 합니다.

It is integer division. Make sure that at least one argument is: NUMERIC(accurate data type)/FLOAT(caution: it's approximate data type):

> / 나누기 (정수 나누기는 결과를 버립니다.)

```SQL
select 1701.0 / 84936;
-- 또는
SELECT 1.0 * 1701 / 84936;
-- 또는
SELECT CAST(1701 AS NUMERIC(10,4))/84936;
```

[DBFiddle Demo](https://dbfiddle.uk/?rdbms=postgres_10&fiddle=de43080be49c1b3da066394849fa2eb6)
