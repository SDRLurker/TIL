출처 : [stackoverflow.com/questions/42988977/what-is-the-purpose-of-pip-install-user](https://stackoverflow.com/questions/42988977/what-is-the-purpose-of-pip-install-user)

# "pip install --user ..."의 목적은?

`pip install --help` 로부터

```
--user 당신의 플랫폼의 Python 사용자 설치 디렉터리에 설치합니다. 일반적으로 ~/.local/, 또는 윈도우즈는 %APPDATA$\Python입니다. (전체 세부사항은 site.USER_BASE Python 문서를 보세요.)
```

site.USER_BASE 문서는 제가 이해하지 못하는 흥미로운 NIX 주제이며 무시무시 합니다.

일반 영어로 `--user`의 목적은 무엇입니까? 왜 패키지는 `~/.local`에 설치되나요? 왜 저의 $PATH에 어딘가 실행 프로그램을 놓지 않을까요?

---

## 8개 답변 중 1개만 추려냄

pip는 기본적으로 (`/usr/local/lib/python3.4` 같은) 시스템 디렉터리에 Python 패키지를 설치합니다. 이는 root 권한이 필요합니다.

pip의 `--user`는 대신에 당신의 home 디렉터리에 패키지를 설치합니다. 이는 특별한 권한을 요구하지 않습니다.

추가 참고주소 : [https://scicomp.stackexchange.com/questions/2987/what-is-the-simplest-way-to-do-a-user-local-install-of-a-python-package](https://scicomp.stackexchange.com/questions/2987/what-is-the-simplest-way-to-do-a-user-local-install-of-a-python-package)
