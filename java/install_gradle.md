CentOS6에서 gradle을 설치할 일이 있어서 다음 주소들을 참고하여 shell 스크립트를 만들었습니다 :)  
**참고**

-   [https://gist.github.com/parzonka/9371885](https://gist.github.com/parzonka/9371885)
-   [http://millky.com/@origoni/post/1047](http://millky.com/@origoni/post/1047)
-   [https://gist.github.com/SDRLurker/d998a5221440884a0e6c677c6bb76b4c](https://gist.github.com/SDRLurker/d998a5221440884a0e6c677c6bb76b4c)

**Gradle 설치**

-   install-gradle-centos.sh
    -   root 계정에서 다음 스크립트를 실행하여 설치하였습니다.

```shell
# installs to /opt/gradle
# existing versions are not overwritten/deleted
# seamless upgrades/downgrades
# $GRADLE_HOME points to latest *installed* (not released)
gradle_version=3.4.1
wget -N https://services.gradle.org/distributions/gradle-${gradle_version}-all.zip
unzip gradle-${gradle_version}-all.zip -d /opt/gradle
ln -sfn gradle-${gradle_version} /opt/gradle/latest
printf "export GRADLE_HOME=/opt/gradle/latest\nexport PATH=\$PATH:\$GRADLE_HOME/bin" > /etc/profile.d/gradle.sh
. /etc/profile.d/gradle.sh
# check installation
gradle -v
```
