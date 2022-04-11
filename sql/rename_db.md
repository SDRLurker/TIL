출처 : [https://stackoverflow.com/questions/67093/how-do-i-quickly-rename-a-mysql-database-change-schema-name](https://stackoverflow.com/questions/67093/how-do-i-quickly-rename-a-mysql-database-change-schema-name)

# MySQL 데이터베이스의 이름(스키마 이름 변경)을 빠르게 변경하는 방법?

[MySQL 매뉴얼](http://web.archive.org/web/20160504181056/https://dev.mysql.com/doc/refman/5.1/en/rename-database.html)에서 이를 다루고 있습니다.

주로 저는 데이터베이스를 덤프하여 새로운 이름으로 불러오고는 했습니다. 이는 매우 큰 데이터베이스를 위한 선택지는 아닙니다. `RENAME {DATABASE | SCHEMA} db_name TO new_db_name;` 은 [나쁜 일을 하고 소수에 버전에서만 존재하며 무엇보다도 나쁜 생각입니다.](http://web.archive.org/web/20160504181056/https://dev.mysql.com/doc/refman/5.1/en/rename-database.html)

이것은 [MyISAM](https://en.wikipedia.org/wiki/MyISAM)과는 매우 다르게 [InnoDB](https://en.wikipedia.org/wiki/InnoDB)에서 작동해야 합니다.

---

## 51개의 답변 중 1개

**InnoDB**에서, 다음은 잘 작동합니다: 새로운 빈 데이터베이스를 만들고 새로운 데이터베이스로 각 테이블의 이름을 변경합니다.

```SQL
RENAME TABLE old_db.table TO new_db.table;
```

그 후에 권한을 조절할 필요가 있습니다.

shell에서 스크립트로 다음 중 하나를 사용할 수 있습니다.

```shell
mysql -u username -ppassword old_db -sNe 'show tables' | while read table; \ 
    do mysql -u username -ppassword -sNe "rename table old_db.$table to new_db.$table"; done
```

또는

```shell
for table in `mysql -u root -ppassword -s -N -e "use old_db;show tables from old_db;"`; do mysql -u root -ppassword -s -N -e "use old_db;rename table old_db.$table to new_db.$table;"; done;
```

메모:

* `-p` 옵션과 암호 사이에는 공백이 없습니다. 데이터베이스에 암호가 없는 경우 `-u username -ppassword` 부분을 제거하십시오.
* 일부 테이블에 트리거가 있는 경우 위의 방법을 사용하여 다른 데이터베이스로 이동할 수 없습니다. (`잘못된 스키마 트리거` 오류가 발생합니다) 이 경우 기존 방법을 사용하여 데이터베이스를 복제한 다음 이전 데이터베이스를 삭제하십시오.
```shell
mysqldump old_db | mysql new_db
```
* 만약 stored 프로시저가 있다면 다음처럼 복사할 수 있습니다.
```shell
mysqldump -R old_db | mysql new_db
```
