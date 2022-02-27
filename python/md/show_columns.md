출처 : [https://stackoverflow.com/questions/23786674/python-mysqldb-how-to-get-columns-name-without-executing-select-in-a-big-tab](https://stackoverflow.com/questions/23786674/python-mysqldb-how-to-get-columns-name-without-executing-select-in-a-big-tab)

# python: MySQLdb. 의 큰 테이블에서 select * 없이 컬럼명을 얻는 방법

저는 테이블에서 컬럼명을 얻고 싶습니다만 100만 개 이상의 데이터가 그 안에 있습니다. 그래서 다음 쿼리를 사용할 수 없습니다.

```python
cursor.execute("SELECT * FROM table_name")
print cursor.description
```

sqlite3에서 저는 이 방법을 사용하였습니다.

```python
crs.execute("PRAGMA table_info(%s)" %(tablename[0]))
for info in crs:
    print info
```

이는 python mysqldb에서는 작동하지 않습니다. 방법을 아는 분 계신가요?

---

## 5개 답변중 1개의 답변

당신은 [`SHOW columns`](https://dev.mysql.com/doc/refman/8.0/en/show-columns.html)을 사용할 수 있습니다.

```python
cursor.execute("SHOW columns FROM table_name")
print [column[0] for column in cursor.fetchall()]
```

참고 바람니다. 이는 본질적으로 [`desc`](https://dev.mysql.com/doc/refman/8.0/en/describe.html)를 사용하는 것과 같습니다.

```python
cursor.execute("desc table_name")
print [column[0] for column in cursor.fetchall()]
```
