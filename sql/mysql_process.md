출처 : [https://stackoverflow.com/questions/929612/how-to-customize-show-processlist-in-mysql](https://stackoverflow.com/questions/929612/how-to-customize-show-processlist-in-mysql)

# MySQL에서 'show processlist' 조건을 주는 방법?

저는 시간으로 다음을 정렬하고 싶습니다만, 방법이 없어 보입니다 ?

```
mysql> show processlist;
+--------+-------------+--------------------+------+---------+--------+----------------------------------+------------------------------------------------------------------------------------------------------+
| Id     | User        | Host               | db   | Command | Time   | State                            | Info                                                                                                 |
+--------+-------------+--------------------+------+---------+--------+----------------------------------+------------------------------------------------------------------------------------------------------+
|      1 | system user |                    | NULL | Connect | 226953 | Waiting for master to send event | NULL                                                                                                 | 
|      2 | system user |                    | v3   | Connect |  35042 | Locked                           | update postings a
                                left join cities b on b.id=a.job_city_id
                                left join states h on h.id=b.stat | 
| 313888 | irnadmin    | 172.19.0.239:40136 | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 314075 | irnadmin    | 172.19.0.239:41113 | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 314118 | irnadmin    | 172.19.0.239:41282 | v3   | Query   |  34978 | freeing items                    | SELECT id, screen_name, type, active, bound, LastLogin, robotno, protocol FROM accounts WHERE email_ | 
| 314686 | irnadmin    | 172.19.0.239:43251 | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 314732 | irnadmin    | 172.19.0.239:43436 | v3   | Query   |  34978 | freeing items                    | SELECT id, screen_name, type, active, bound, LastLogin, robotno, protocol FROM accounts WHERE email_ | 
| 314984 | irnadmin    | 172.19.0.239:44366 | v3   | Sleep   |      2 |                                  | NULL                                                                                                 | 
| 315051 | irnadmin    | 172.19.0.239:44713 | v3   | Query   |      0 | NULL                             | NULL                                                                                                 | 
| 315198 | irnadmin    | 172.19.0.239:51569 | v3   | Sleep   |      2 |                                  | NULL                                                                                                 | 
| 315280 | irnadmin    | 172.19.0.239:51849 | v3   | Query   |  34978 | freeing items                    | SELECT id, email_address, type, closed, robotno FROM accounts WHERE screen_name = 'ShantanuS'        | 
| 315320 | irnadmin    | 172.19.0.239:52045 | v3   | Query   |  34978 | freeing items                    | SELECT id, screen_name, type, active, bound, LastLogin, robotno, protocol FROM accounts WHERE email_ | 
| 315384 | irnadmin    | 172.19.0.239:52463 | v3   | Sleep   |      1 |                                  | NULL                                                                                                 | 
| 452248 | irnadmin    | 172.19.0.28:54899  | v3   | Query   |  34978 | freeing items                    | SELECT id, email_address, type, closed, robotno FROM accounts WHERE screen_name = 'LIZW0218'         | 
| 452291 | irnadmin    | 172.19.0.28:55045  | v3   | Sleep   |      1 |                                  | NULL                                                                                                 | 
| 452316 | irnadmin    | 172.19.0.28:55144  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 452353 | irnadmin    | 172.19.0.28:55278  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 452382 | irnadmin    | 172.19.0.28:55371  | v3   | Query   |  34978 | freeing items                    | SELECT o.account_id FROM online o JOIN accounts a ON a.id=o.account_id WHERE o.server_id IS NULL AND | 
| 452413 | irnadmin    | 172.19.0.28:55479  | v3   | Sleep   |      1 |                                  | NULL                                                                                                 | 
| 452541 | irnadmin    | 172.19.0.28:55946  | v3   | Query   |  34978 | freeing items                    | SELECT o.account_id FROM online o JOIN accounts a ON a.id=o.account_id WHERE o.server_id IS NULL AND | 
| 452626 | irnadmin    | 172.19.0.28:56215  | v3   | Sleep   |      2 |                                  | NULL                                                                                                 | 
| 452711 | irnadmin    | 172.19.0.28:39916  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 452781 | irnadmin    | 172.19.0.28:40161  | v3   | Sleep   |      1 |                                  | NULL                                                                                                 | 
| 452904 | irnadmin    | 172.19.0.28:40955  | v3   | Query   |  34978 | freeing items                    | select a.id, aa.screen_name, i.requester from interview_requests i left join accounts aa on aa.id=i. | 
| 453014 | irnadmin    | 172.19.0.28:41291  | v3   | Query   |  34978 | freeing items                    | SELECT o.account_id FROM online o JOIN accounts a ON a.id=o.account_id WHERE o.server_id IS NULL AND | 
| 453057 | irnadmin    | 172.19.0.28:41377  | v3   | Query   |  34978 | freeing items                    | select a.id, aa.screen_name, i.requester from interview_requests i left join accounts aa on aa.id=i. | 
| 453084 | irnadmin    | 172.19.0.28:41441  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 453112 | irnadmin    | 172.19.0.28:41536  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 453156 | irnadmin    | 172.19.0.28:41653  | v3   | Query   |  34978 | freeing items                    | SELECT protocol FROM accounts WHERE email_address= '***@gtalk.jabber.jobirn.c | 
| 453214 | irnadmin    | 172.19.0.28:41800  | v3   | Sleep   |      5 |                                  | NULL                                                                                                 | 
| 453243 | irnadmin    | 172.19.0.28:41991  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 453313 | irnadmin    | 172.19.0.28:42255  | v3   | Query   |  34978 | freeing items                    | SELECT o.account_id FROM online o JOIN accounts a ON a.id=o.account_id WHERE o.server_id IS NULL AND | 
| 453396 | irnadmin    | 172.19.0.28:53718  | v3   | Sleep   |      2 |                                  | NULL                                                                                                 | 
| 453476 | irnadmin    | 172.19.0.28:54019  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 453561 | irnadmin    | 172.19.0.28:54352  | v3   | Sleep   |      3 |                                  | NULL                                                                                                 | 
| 453594 | irnadmin    | 172.19.0.28:54456  | v3   | Sleep   |      0 |                                  | NULL                                                                                                 | 
| 453727 | irnadmin    | 172.19.0.28:55166  | v3   | Query   |  34978 | freeing items                    | SELECT id, screen_name, type, active, bound, LastLogin, robotno, protocol FROM accounts WHERE email_ | 
| 453786 | irnadmin    | 172.19.0.28:55320  | v3   | Sleep   |      4 |                                  | NULL                                                                                                 | 
| 610140 | irnadmin    | 172.19.0.28:33848  | v3   | Query   |  34978 | freeing items                    | select a.id, aa.screen_name, i.requester from interview_requests i left join accounts aa on aa.id=i. | 
| 685119 | irnadmin    | 172.19.0.27:37251  | v3   | Query   |  34980 | Sending data                     | select postings.id id,category, job_desc_title,
        IF(c1.name is not null,c1.name,IF(c2.name is not n | 
| 685226 | irnadmin    | 172.19.0.139:57274 | v3   | Query   |  34735 | Locked                           | SELECT job_desc_title,job_desc,job_state_name,job_city_name,company_categories.name,postings.categor | 
| 685229 | irnadmin    | 172.19.0.139:57278 | v3   | Query   |  34735 | Locked                           | SELECT job_desc_title,job_desc,job_state_name,job_city_name,company_categories.name,postings.categor | 
| 685232 | irnadmin    | 172.19.0.139:57283 | v3   | Query   |  34734 | Locked                           | select job_desc_title,job_desc from postings where id=287650                                         | 
| 685233 | irnadmin    | 172.19.0.139:57286 | v3   | Query   |  34734 | Locked                           | SELECT accounts.screen_name,postings.url url, accounts.type owner_type, postings.id ID, postings.job | 
| 685235 | irnadmin    | 172.19.0.28:37502  | v3   | Query   |  34734 | Locked                           | SELECT accounts.screen_name,postings.url url, accounts.type owner_type, postings.id ID, postings.job | 
| 686496 | irnadmin    | 172.19.0.239:33306 | v3   | Query   |  32589 | Locked                           | SELECT accounts.screen_name,postings.url url, accounts.type owner_type, postings.id ID, postings.job | 
| 686503 | irnadmin    | 172.19.0.28:54051  | v3   | Query   |  32588 | Locked                           | SELECT job_desc_title, job_desc, IF(postings.category IS NOT NULL, postings.category, job_categories | 
| 709550 | root        | localhost          | v3   | Query   |      0 | NULL                             | show processlist                                                                                     | 
| 710084 | irnadmin    | 172.19.0.27:53285  | NULL | Query   |      0 | removing tmp table               | show status where Variable_name='Threads_running'                                                    | 
+--------+-------------+--------------------+------+---------+--------+----------------------------------+------------------------------------------------------------------------------------------------------+
49 rows in set (0.00 sec)
```

---

## 6개의 답변 중 1개를 추려냄

SQL의 새 버전은 information\_schema 에서 프로세스 리스트를 지원합니다.

```
SELECT * FROM INFORMATION_SCHEMA.PROCESSLIST
```

당신은 원하는 방법으로 정렬(ORDER BY)할 수 있습니다.

INFORMATION\_SCHEMA.PROCESSLIST는 MySQL 5.1.7에 추가되었습니다. 당신이 사용하는 버전을 다음처럼 알 수 있습니다.

```
SELECT VERSION()
```
