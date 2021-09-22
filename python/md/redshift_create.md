출처 : [https://stackoverflow.com/questions/48232367/redshift-create-table-not-working-via-python](https://stackoverflow.com/questions/48232367/redshift-create-table-not-working-via-python)

# Python으로 Redshift의 create table이 작동하지 않습니다.


[IAM 역할 자격 증명을 사용하는 Python에서 S3로 unload하기](https://stackoverflow.com/questions/48177244/unload-to-s3-with-python-using-iam-role-credentials)글을 참조하여 unload 문이 완벽하게 작동했습니다. COPY 및 SELECT 문과 같은 다른 명령도 시도했습니다.

하지만, 저는 테이블을 생성하는 쿼리를 실행하였습니다. CREATE TABLE 쿼리는 오류없이 실행되었지만, select 구문에 도달했을 때 `relation "public.test"가 존재하지 않는다는` 오류가 발생하였습니다.

왜 테이블이 적절히 만들어지지 않았는지 알 수 있을까요? 쿼리는 아래 있습니다.

```python
import sqlalchemy as sa
from sqlalchemy.orm import sessionmaker
import config
import pandas as pd

#>>>>>>>> 여기를 바꿨습니다. >>>>>>>>
DATABASE = "db"
USER = "user"
PASSWORD = getattr(config, 'password') # David Bern의 답변을 보세요. https://stackoverflow.com/questions/43136925/create-a-config-file-to-hold-values-like-username-password-url-in-python-behave/43137301
HOST = "host"
PORT = "5439"
SCHEMA = "public"      #default is "public"

########## 접속 및 세션 생성 ##########
connection_string = "redshift+psycopg2://%s:%s@%s:%s/%s" % (USER,PASSWORD,HOST,str(PORT),DATABASE)
engine = sa.create_engine(connection_string)
session = sessionmaker()
session.configure(bind=engine)
s = session()
SetPath = "SET search_path TO %s" % SCHEMA
s.execute(SetPath)

-- create table 예시
query2 = '''\ 
create table public.test (
id integer encode lzo,
user_id integer encode lzo,
created_at timestamp encode delta32k,
updated_at timestamp encode delta32k
)
distkey(id)
sortkey(id)
'''

r2 = s.execute(query2)

--select 예시
query4 = '''\ 
select * from public.test
'''

r4 = s.execute(query4)

########## SQL query 출력으로 DataFrame 생성 ##########
df = pd.read_sql_query(query4, connection_string)

print(df.head(50))

########## 마지막에 세션 닫기 ##########
s.close()
```

Redshift에서 직접 실행하면 잘 작동합니다 

--편집내용--

시도해 본 것 중에 일부입니다.

* query에서 \를 제거하였습니다.
* 문자열 끝에 ";"를 추가하였습니다.
* "public.test"를 "test"로 변경하였습니다.
* SetPath = "SET search_path TO %s" % SCHEMA 와 s.execute(SetPath) 구문 제거
* create 구문을 break. 예상되는 오류 생성
* create 후에 S3 명령으로 copy를 추가하였지만 다시 테이블이 생성되지 않습니다.
* copy 명령에서 생성된 파일에 존재하지 않는 문을 생성하기 위해 열을 추가하면 예상 오류가 생성됩니다.
* r4 = s.execute(query)를 추가 - 오류 없이 실행되지만 Redshift에서 다시 테이블이 생성되지 않습니다.

---

## 1개의 답변 

테이블을 생성하려면 s.commit()을 추가해야합니다. COPY 명령을 통해 채우거나 다음 위치에 INSERT INTO하는 경우 : COPY 명령 다음에 추가하십시오 (테이블 생성 후는 선택 사항임). 기본적으로 CREATE/ALTER 명령을 자동 커밋하지 않습니다!

[http://docs.sqlalchemy.org/en/latest/orm/session_basics.html#session-faq-whentocreate](http://docs.sqlalchemy.org/en/latest/orm/session_basics.html#session-faq-whentocreate)
[http://docs.sqlalchemy.org/en/latest/core/connections.html#understanding-autocommit](http://docs.sqlalchemy.org/en/latest/core/connections.html#understanding-autocommit)
