참고주소 : [https://stackoverflow.com/questions/33907835/docker-error-cannot-delete-docker-container-conflict-unable-to-remove-reposito](https://stackoverflow.com/questions/33907835/docker-error-cannot-delete-docker-container-conflict-unable-to-remove-reposito)

# Docker 오류 docker container를 삭제할 수 없음, 충돌 : 저장소 참조를 제거할 수 없음

저는 Docker에서 container를 삭제하고 싶지만, 지우기를 원할 때 오류가 발생합니다.

container를 삭제하기 전에 다음 단계에서 존재하는 container의 목록을 확인하세요.

```shell
sts@Yudi:~/docker$ sudo docker ps -as

CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                    PORTS               NAMES                  SIZE
78479ffeba5c        ubuntu              "/bin/bash"         42 hours ago        Exited (0) 42 hours ago                       sharp_wescoff          81 B (virtual 187.7 MB)
0bd2b54678c7        training/webapp     "python app.py"     5 days ago          Exited (0) 5 days ago                         backstabbing_ritchie   0 B (virtual 323.7 MB)
0adbc74a3803        training/webapp     "python app.py"     5 days ago          Exited (143) 5 days ago                       drunk_feynman          0 B (virtual 323.7 MB)
```

저는 이 목록 중 하나 **"training / webapp"** 을 지우고 싶지만 다음 오류가 발생합니다.

```shell
sts@Yudi:~/docker$ sudo docker rmi training/webapp
Error response from daemon: conflict: unable to remove repository reference "training/webapp" (must force) - container 0bd2b54678c7 is using its referenced image 54bb4e8718e8
Error: failed to remove images: [training/webapp]
```

이미지에 container가 실행 중인건가요?

도와주세요.

---

## 11개 답변 중 1

docker image와 docker container에는 차이가 있습니다. 이 [질문](https://stackoverflow.com/questions/23735149/what-is-the-difference-between-a-docker-image-and-a-container)을 확인하세요.

즉, container는 이미지의 실행 가능한 인스턴스입니다. 이미지로부터 실행중인 컨테이너가 있다면 지울 수 없는 이유가 됩니다. **당신은 먼저 container를 지워야 합니다.**

```shell
docker ps -a                # container 목록 (과 그들이 어떤 이미지로 만들어 졌는지 확인)

docker images               # image 목록 

docker rm <container_id>    # 멈춰진 container를 삭제

docker rm -f <container_id> # 실행 중인 container를 (SIGKILL을 사용하여) 강제로 삭제

docker rmi <image_id>       # image 삭제
                            # 이미지의 인스턴스가 실행중이면 실패. 예. container

docker rmi -f <image_id>    # 여러 저장소에서 참조될 지라도 image를 강제로 삭제.
                            # 예. 여러개의 names/tags인 같은 image id
                            # image를 참조하는 docker container가 있으면 여전히 실패.
``` 

### Docker 1.13+ 업데이트 (2017년 1월 부터)

> Docker 1.13에서는 모든 명령이 상호 작용하는 논리적 객체 아래에 위치하도록 다시 그룹화했습니다.

기본적으로, 위의 명령은 다음처럼 다시 작성될 수 있습니다.

```shell
docker container ls -a
docker image ls
docker container rm <container_id>
docker image rm <image_id>
```

만약 [모두](https://docs.docker.com/engine/reference/commandline/system_prune/) 삭제하고 싶으면 다음을 사용할 수 있습니다.

```shell
docker system prune -a
```

> 주의! 이는 다음을 삭제합니다.
> * 모든 멈춘 container
> * 하나 이상의 사용되지 않는 모든 네트워크
> * 모든 사용되지 않는 image
> * 모든 build 된 캐시

