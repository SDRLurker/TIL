출처 : [https://stackoverflow.com/questions/37201918/spark-job-keep-on-running](https://stackoverflow.com/questions/37201918/spark-job-keep-on-running)

# Spark 작업을 실행한 채 유지하기

저는 다음 명령을 사용하여 ambari-server에서 저의 Spark 작업을 제출 하였습니다.

```shell
./spark-submit --class  customer.core.classname --master yarn --numexecutors 2 --driver-memory 2g --executor-memory 2g --executor-cores 1 /home/hdfs/Test/classname-0.0.1-SNAPSHOT-SNAPSHOT.jar newdata host:6667
```

이 명령은 잘 작동하였습니다.

하지만, 명령 프롬프트를 닫거나 그 작업을 kill하려고 할 때 그 작업은 실행한 채 유지하여야 합니다.

어떠한 도움이든 감사합니다.

---

## 4개 답변 중 1개의 답변

몇 가지 방법으로 이를 이룰 수 있습니다.

1) `nohup`을 사용하여 백그라운드(background)로 드라이버(driver) 프로세스를 spark-submit으로 실행할 수 있습니다.

```shell
nohup  ./spark-submit --class  customer.core.classname \
  --master yarn --numexecutors 2 \
  --driver-memory 2g --executor-memory 2g --executor-cores 1 \
  /home/hdfs/Test/classname-0.0.1-SNAPSHOT-SNAPSHOT.jar \
  newdata host:6667 &
```

2) 드라이버 프로세스가 다른 노드에서 실행되도록 배포 모드에서 클러스터로 실행하십시오.
