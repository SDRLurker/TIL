출처 :[https://stackoverflow.com/questions/2472229/insert-into-select-from-on-duplicate-key-update](https://stackoverflow.com/questions/2472229/insert-into-select-from-on-duplicate-key-update)

# INSERT INTO … SELECT FROM … ON DUPLICATE KEY UPDATE

저는 만약 키가 이미 있을 경우 대부분의 많은 컬럼이 갱신될 필요가 있는 insert 쿼리를 실행하려 합니다.  
다음처럼 진행하려 하는데요.

```SQL
INSERT INTO lee(exp_id, created_by, 
                location, animal, 
                starttime, endtime, entct, 
                inact, inadur, inadist, 
                smlct, smldur, smldist, 
                larct, lardur, lardist, 
                emptyct, emptydur)
SELECT id, uid, t.location, t.animal, t.starttime, t.endtime, t.entct, 
       t.inact, t.inadur, t.inadist, 
       t.smlct, t.smldur, t.smldist, 
       t.larct, t.lardur, t.lardist, 
       t.emptyct, t.emptydur 
FROM tmp t WHERE uid=x
ON DUPLICATE KEY UPDATE ...; 
//update all fields to values from SELECT, 
//       except for exp_id, created_by, location, animal, 
//       starttime, endtime
```

저는 `UPDATE` 구문을 위한 문법에 무엇이 와야 되는지 확실하지 않습니다. `SELECT` 구문으로부터 현재 행을 어떻게 참조할 수 있을까요?

### 2개의 답변 중 1개의 답변만 추려냄

MySQL은 INSERT INTO 구문에서 등호 앞에 부분이 컬럼(필드)명을 참조하고 SELECT 컬럼(필드)를 등호 뒤에 두번째 부분에서 참조한다고 가정합니다.

```SQL
INSERT INTO lee(exp_id, created_by, location, animal, starttime, endtime, entct, 
                inact, inadur, inadist, 
                smlct, smldur, smldist, 
                larct, lardur, lardist, 
                emptyct, emptydur)
SELECT id, uid, t.location, t.animal, t.starttime, t.endtime, t.entct, 
       t.inact, t.inadur, t.inadist, 
       t.smlct, t.smldur, t.smldist, 
       t.larct, t.lardur, t.lardist, 
       t.emptyct, t.emptydur 
FROM tmp t WHERE uid=x
ON DUPLICATE KEY UPDATE entct=t.entct, inact=t.inact, ...
```

#### 2개의 팁 추가 번역

-   @dnagirl: **팁**: PK 컬럼중 어느것도 update하지 마세요. 리스트에서 갱신할 필요가 있는 것만 update하세요.
-   당신이 제안한 문법은 작동하며 `t.`가 요구됩니다. 저는 xaprb의 블로그글([xaprb.com/blog/2006/02/21/flexible-insert-and-update-in-mysql](http://www.xaprb.com/blog/2006/02/21/flexible-insert-and-update-in-mysql/))에서 이 문법 `on duplicate key update b = values(b), c = values(c)`을 보았습니다. 이것도 작동합니다.
