출처 : [https://stackoverflow.com/questions/6318809/how-do-i-grab-an-ini-value-within-a-shell-script](https://stackoverflow.com/questions/6318809/how-do-i-grab-an-ini-value-within-a-shell-script)

# shell 스크립트에서 INI 파일 설정값을 얻는 방법은?

다음과 같은 parameters.ini 파일이 있습니다.

```shell
[parameters.ini]
    database_user    = user
    database_version = 20110611142248
```

bash 쉘 스크립트 내에서 parameters.ini 파일에 지정된 데이터베이스 버전을 읽고 사용하여 처리하려고 합니다.

```shell
#!/bin/sh
# 스크립트에서 사용하기 위해 parameters.ini 파일에서 데이터베이스 버전을 가져와야 함
PHP app/console doctrine:migrations:migrate $DATEBASE_VERSION
```

이를 어떻게 할 수 있을까요?

---

## 31개의 답변 중 1개

해당 라인의 값을 grep 하고난 뒤 awk를 사용하는 것은 어떨까요?

```shell
version=$(awk -F "=" '/database_version/ {print $2}' parameters.ini)
```
