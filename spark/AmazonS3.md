출처 : [https://cwiki.apache.org/confluence/display/HADOOP2/AmazonS3](https://cwiki.apache.org/confluence/display/HADOOP2/AmazonS3)

# 아파치 하둡에서 S3 지원

아파치 하둡은 URL 접두사로 "s3a:"의 "S3A"라 불리는 S3로 접속 Connector를 제공합니다. 이전 connector "s3"와 "s3n"은 현재 하둡 버전에서는 더 이상 사용되지 않거나 삭제되었습니다.

1. S3A Connector를 사용하는 상세 사항은 [최근 하둡 문서](https://github.com/apache/hadoop/blob/branch-2/hadoop-tools/hadoop-aws/src/site/markdown/tools/hadoop-aws/index.md
)를 참조하세요.
2. Hadoop 2.x 릴리즈의 경우 최신 [문제 해결 문서](https://github.com/apache/hadoop/blob/branch-2/hadoop-tools/hadoop-aws/src/site/markdown/tools/hadoop-aws/index.md)
3. Hadoop 3.x 릴리즈의 경우 최신 [문제 해결 문서](https://github.com/apache/hadoop/blob/trunk/hadoop-tools/hadoop-aws/src/site/markdown/tools/hadoop-aws/troubleshooting_s3a.md)

## Amazon EMR의 S3 지원
Amazon의 EMR 서비스는 Apache Hadoop을 기반으로 하지만 수정 사항과 자체로 폐쇄적인 소스 S3 클라이언트를 포함합니다. [이에 대한 Amazon의 문서](https://docs.aws.amazon.com/emr/latest/ManagementGuide/emr-plan-file-systems.html)를 참조하십시오. 아마존만 S3 지원과 관련된 버그 보고서를 제공할 수 있습니다.

## 중요 : 클래스 경로 설정
1. S3A 커넥터는 hadoop-aws JAR에 구현됩니다. classpath에 없는 경우 스택을 추적(stack trace)하세요.
2. "hadoop-aws" 버전과 다른 버전의 다른 hadoop을 섞어서 사용하지 마십시오. 그들은 정확히 같은 배포판(release)에서 나온 것이어야합니다. 그렇지 않으면 스택을 추적(stack trace)하세요.
3. S3A 커넥터는 AWS SDK JAR에 따라 다릅니다. classpath에 없는 경우 스택을 추적(stack trace)하세요.
4. hadoop 버전이 빌드된 것과 다른 amazon S3 SDK JAR을 사용하지 마십시오. 그렇지 않으면 스택을 추적해야할 가능성이 높습니다.
5. adoop-aws JAR의 특정 버전의 종속성 목록은 Maven에 저장되어 있으며 [mvnrepsitory](https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-aws)에서 볼 수 있습니다.

## MapReduce, Spark 및 Hive 작업의 대상으로 Amazon S3를 사용하려면 일관성 있는 계층이 필요합니다.
S3 파일 시스템 클라이언트는 HDFS의 drop-in 대체품으로 사용할 수 없습니다. Amazon S3는 다음을 제공하는 "객체 저장소"입니다.
* 최종 일관성 : 한 응용 프로그램 (작성, 업데이트 및 삭제)에 의한 변경 사항은 정의될 때 까지 표시되지 않습니다.
* Non-atomic한 이름 바꾸기 및 삭제 작업. 큰 디렉토리의 이름을 바꾸거나 삭제하는 것은 항목 수에 비례하여 시간이 걸리며 이 시간 동안 다른 프로세스에서 볼 수 있습니다. 실제로 최종 일관성이 해결 될 때까지 표시됩니다. 이렇게 하면 이러한 모든 응용 프로그램에서 사용되는 커밋 프로토콜이 중단되어 작업 내에서 여러 작업의 출력을 안전하게 커밋 할 수 있습니다.
Hadoop 3.x는 일관성을 위해 [S3Guard](https://github.com/apache/hadoop/blob/trunk/hadoop-tools/hadoop-aws/src/site/markdown/tools/hadoop-aws/s3guard.md)와 함께 제공되고 커밋 작업을 위해 [S3A Committers](https://github.com/apache/hadoop/blob/trunk/hadoop-tools/hadoop-aws/src/site/markdown/tools/hadoop-aws/committers.md)와 함께 제공됩니다.
Amazon EMR의 경우 "Consistent EMR"을 사용하여 store에서 일관된 관점으로 사용할 수 있습니다.
참고 : 타사 S3 호환 store에는 이 제한이 없을 수 있습니다. 해당 설명서를 참조하십시오.

## 중요 : 보안
Amazon Secret Access Key는 다음과 같습니다. 알려진 경우 보안 자격 증명 페이지로 이동하여 권한을 취소해야합니다. 로그에 출력하거나 XML 구성 파일을 개정 제어로 확인하십시오.
1. 개정 관리 시스템에 check in 하지 마십시오. 
2. 클라이언트가 (현재) URI에 자격 증명을 포함하는 것을 지원하지만 이것은 매우 위험합니다 : 로그와 오류 메시지에 나타납니다. 이것을 피하십시오. 
3. S3A는 EC2 VM에서 실행될 때 현재 IAM 역할의 자격 증명을 자동으로 받습니다.
