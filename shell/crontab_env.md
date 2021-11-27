출처 : [https://stackoverflow.com/questions/2229825/where-can-i-set-environment-variables-that-crontab-will-use](https://stackoverflow.com/questions/2229825/where-can-i-set-environment-variables-that-crontab-will-use)

# crontab이 사용할 환경 변수를 어디에서 설정할 수 있습니까?

저는 매 시간마다 실행하는 crontab이 있습니다. 그것을 실행하는 사용자는 터미널에서 작업을 실행할 때 작동하는 `.bash_profile`의 환경 변수를 가지고 있습니다. 하지만, 그것을 실행할 때 환경변수가 crontab에 의해 선택되지는 않습니다.

저는 환경변수를 `.profile`과 `.bashrc`에서 설정을 시도했지만, 그 환경변수는 선택되지 않는 듯 합니다. crontab이 선택할 수 있도록 환경 변수을 어디에 넣어야 하는지 아시는 분 있으신가요?

---

## 20개의 답변 중 1개

명령어 라인에서 `crontab -e`를 실행했을 때 그 crontab 자체에서 환경 변수를 정의할 수 있습니다.

```shell
LANG=nb_NO.UTF-8
LC_ALL=nb_NO.UTF-8
# m h  dom mon dow   command

* * * * * sleep 5s && echo "yo"
```

이 특징은 cron의 특정 구현에서만 가능합니다. 우분투와 데비안은 crontab 파일 (GNU [mcron](https://www.gnu.org/software/mcron/manual/mcron.html#Guile-Syntax) 도) 에서 선언을 허용하는 [vixie-cron](http://manpages.ubuntu.com/cgi-bin/search.py?cx=003883529982892832976%3A5zl6o8w6f0s&cof=FORID%3A9&ie=UTF-8&titles=404&lr=lang_en&q=crontab.5)을 현재 사용합니다.

[Archlinux](https://archlinux.org/packages/core/x86_64/cronie/)와 [Redhat](http://docs.redhat.com/docs/en-US/Red_Hat_Enterprise_Linux/6/html/Migration_Planning_Guide/ch04s14.html)은 환경변수를 허용하지 **않고** cron.log에서 문법 오류를 발생하는 [cronie](https://fedorahosted.org/cronie/)를 사용합니다. 작업은 엔트리 마다 할 수 있습니다.

```shell
# m h  dom mon dow   command
* * * * * export LC_ALL=nb_NO.UTF-8; sleep 5s && echo "yo"
```
