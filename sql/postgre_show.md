출처 : [https://stackoverflow.com/questions/769683/postgresql-show-tables-in-postgresql](https://stackoverflow.com/questions/769683/postgresql-show-tables-in-postgresql)

# PostgreSQL에서 테이블 보기

(MySQL의) `show tables`와 똑같은 쿼리가 PostgreSQL에서는 무엇인가요?

---

## 25개의 답변 중 1개

`psql` 명령어 라인 인터페이스에서

첫째로 원하는 데이터베이스를 선택합니다.

```
\c database
```

그리고 이 명령어로 현재 스키마에서 모든 테이블을 볼 수 있습니다.

```
\dt
```

---

(`psql` 인터페이스로도 당연히 작동하는) 프로그래밍 쿼리로는

```SQL
SELECT * FROM pg_catalog.pg_tables;
```

시스템 테이블은 `pg_catalog` 데이터베이스에 있습니다.
