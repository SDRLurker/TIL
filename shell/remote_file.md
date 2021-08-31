출처 : [https://stackoverflow.com/questions/12845206/check-if-file-exists-on-remote-host-with-ssh](https://stackoverflow.com/questions/12845206/check-if-file-exists-on-remote-host-with-ssh)

# ssh로 원격 호스트의 파일이 존재하는 지 확인

저는 원격 호스트에서 특정 파일이 있는지 확인하고 싶습니다. 그래서 다음을 시도했습니다.

```shell
$ if [ ssh reg@localhost -p 19999 -e /home/reg/Dropbox/New_semiosNET/Research_and_Development/Puffer_and_Traps/Repeaters_Network/UBC_LOGS/log1349544129.tar.bz2 ] then echo "okidoke"; else "not okay!" fi
-sh: syntax error: unexpected "else" (expecting "then") 
```

------

## 13개의 답변 중 1 개의 답변

여러 답변에 근거하여 다음처럼 짧게 사용하는 방법이 있습니다.

```shell
ssh -q $HOST [[ -f $FILE_PATH ]] && echo "File exists" || echo "File does not exist";
```

`-q`는 quiet 모드입니다. 이는 warning과 메세지를 덜 나오게 할 것입니다.

@Mat님이 언급했듯이 이처럼 테스트하는 것의 이득은 `-f`를 `-nt`, `-d`, `-s`... 등과 같은 다른 테스트 연산자로 쉽게 바꿀 수 있기 때문에 좋습니다.

**테스트 연산자:** [http://tldp.org/LDP/abs/html/fto.html](http://tldp.org/LDP/abs/html/fto.html)



