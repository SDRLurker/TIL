출처 : [http://stackoverflow.com/questions/305035/how-to-use-ssh-to-run-shell-script-on-a-remote-machine](http://stackoverflow.com/questions/305035/how-to-use-ssh-to-run-shell-script-on-a-remote-machine)

# ssh를 사용하여 원격 컴퓨터의 shell script를 실행하는 방법?

원격 컴퓨터에서 shell script를 실행하는 방법을 저에게 제안해주실 수 있나요?

A 컴퓨터와 B컴퓨터 모두에서 설정된 ssh가 있습니다. 저의 스크립트는 B컴퓨터에서 수행할 내용이 A컴퓨터에 있습니다.

----

## 9개의 답변 중 1개의 답변만 추려냄.

만약 A 컴퓨터가 Windows box라면 -m 파라미터와 함께 Plink(의 일부)를 사용하실 수 있고 원격 서버에 로컬(내 컴퓨터의) 스크립트를 실행할 것입니다.

```shell
plink root@MachineB -m local_script.sh
```

만약 A컴퓨터가 유닉스를 기반으로한 시스템이면 다음처럼 사용할 수 있습니다.

```shell
ssh root@MachineB 'bash -s' < local_script.sh
```

이를 수행하기 위해 스크립트를 원격 컴퓨터로 복사하실 필요가 없습니다.

----

(번역과 관련없는) 추가내용

ssh 뿐만이 아니라 rsh도 똑같은 방법으로 실행이 가능합니다.

ssh root@MachineB 'bash -s' < local_script.sh

ssh  계정명@원격호스트(IP)  수행할명령어 < 로컬_스크립트.sh

만약 원격 컴퓨터의 프로그램이 수행이 되지 않는다면 

로컬_스크립트.sh에서 export LD_LIBRARY_PATH를 원격컴퓨터에 맞게 설정하셔야 됩니다.

예) export LD_LIBRARY_PATH=/usr/lib:/usr/local/lib
