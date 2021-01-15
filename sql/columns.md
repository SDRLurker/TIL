출처 : [http://stackoverflow.com/questions/924729/mysql-select-many-fields-how-best-to-display-in-terminal](http://stackoverflow.com/questions/924729/mysql-select-many-fields-how-best-to-display-in-terminal)

# SELECT가 많은 칼럼 필드들을 가질 때 터미널에 최고의 방법으로 표시하는 방법은?

저는 Putty를 사용하고 있고 다음 쿼리를 실행하였습니다.

```SQL
mysql> SELECT * FROM sometable;
```

'sometable'은 많은 필드 칼럼들이 있고 터미널에 표시될 많은 칼럼들이 결과로 있습니다. 다음 줄까지 필드들이 표시되어 있어서 필드 값과 컬럼 제목을 보는데 어렵습니다.

터미널에서 이러한 데이터를 보는 해결책은 무엇이 있을까요?

(추신 : 저는 phpMyAdmin이나 다른 GUI 인터페이스에 접근할 수 없습니다.)

MySQL 쿼리 결과를 text나 CVS로 명령어로 저장하는 방법이나 명령어를 입력하늡 방법같은 해결책을 찾아주셨으면 합니다.

---------------

## 8 개의 답변 중 2개의 답변만 추려냄.

1. 이 방법이 유용할 것입니다. (윈도우는 안됨):

```SQL
mysql> pager less -SFX
mysql> SELECT * FROM sometable;
```

이 쿼리는 위의 파라미터로 된 less 명령어를 통해 파이프로 출력할 것입니다. 또한 이 쿼리는 테이블 출력을 화살표 키를 이용하여 가로 세로 스크롤(이동)되게 할 수 있습니다.

`q` 키를 누르면 이 보기를 끝내며 `less` 툴을 종료할 것입니다.

2.

```SQL
SELECT * FROM sometable\G
```

위 쿼리의 행은 다음처럼 표시될 것입니다.

```
*************************** 1. row ***************************
             id: 1
```
