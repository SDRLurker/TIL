출처 : [http://stackoverflow.com/questions/15691977/why-start-a-shell-command-with-a-backslash](http://stackoverflow.com/questions/15691977/why-start-a-shell-command-with-a-backslash)

# 왜 쉘 명령어가 \(백슬래시)로 시작하나요?

```shell
\curl -L https://get.rvm.io | bash -s stable
```

왜 이 명령어가 `\`로 시작하나요 ? [여기를 클릭하면 제가 본 사이트입니다.](https://www.digitalocean.com/community/articles/how-to-install-ruby-on-rails-on-ubuntu-12-04-lts-precise-pangolin-with-rvm)

---

## 2개의 답변

```shell
alias curl='curl --some --default --options'
```

만약 당신이 `curl` 이란 alias를 설정해 놓았고 이 alias를 사용하고 싶지 않다면 백슬래시를 넣음으로써 alias를 사용하게 않고 curl 바이너리를 직접 실행하도록 합니다.

이는 현재 사용중인 쉘(shell)에서만 적용됩니다. alias는 쉘 스크립트에서는 효과가 없기 때문에 거기엔 넣을 필요가 없습니다. 

---

[Bourne/POSIX 쉘 명세서](https://pubs.opengroup.org/onlinepubs/9699919799/utilities/V3_chap02.html#tag_18_03_01)는 says 현재 사용중인 쉘에서 **alias 치환**은 명령어가 인용에 사용하는 문자들이 있을 때 억제된다고 설명이 되어 있습니다. 백슬래시가 그 중 한 방법이고 작은 따옴표와 큰 따옴표를 통해 다른 알려진 방법으로 인용하는 방법도 있습니다. 다음 모두는 alias 치환을 억제할 것입니다.
 
 ```shell
 \curl
 cur\l
 \c\u\r\l
 "c"url
 "curl"
 "c""u""r""l"
 'curl'
 'cu'"rl"
 ```
 
`\curl` 를 사용하는 것은 가장 보편적이고 읽기 쉬운 방법입니다. 이는 표준화된 특징이기 때문에 모든 Bourne 쉘에서 잘 작동할 수 있습니다.

`\curl`은 약간 TeX 명령처럼 보이지 않습니까? :-)
