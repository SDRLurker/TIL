출처 : [https://stackoverflow.com/questions/25826752/using-tr-to-replace-newline-with-space/25826920](https://stackoverflow.com/questions/25826752/using-tr-to-replace-newline-with-space/25826920)

# tr을 사용하여 줄바꿈을 공백으로 바꾸기

`sed`의 출력이 있습니다.

```shell
http://sitename.com/galleries/83450
72-profile
```

2개의 문자열은 다음처럼 하나로 합치고 각각은 공백으로 구분됩니다.

```shell
http://sitename.com/galleries/83450 72-profile
```

줄바꿈을 공백으로 바꾸기 위해 2개의 문자열을 파이프를 통해 `tr`로 넘겼습니다.

```shell
tr '\n' ' '
```

하지만 이는 작동하지 않고 결과는 입력과 같습니다.

ASCII 코드 `'\032'`로 공백을 표시하면 `\n`을 인쇄할 수 없는 문자로 바꿉니다.

무엇이 잘못되었습니까? 저는 Windows에서 Git Bash를 사용하고 있습니다.

## 1개의 답변

최고의 추측은 당신은 윈도우즈를 사용하고 당신의 줄 마지막 값 설정은 윈도우즈를 위한 값으로 설정되어 있습니다. 이 내용을 읽어보세요 : [줄 마지막 값 설정을 변경하는 방법](https://stackoverflow.com/questions/10418975/how-to-change-line-ending-settings)

또는 다음을 사용하세요.

```shell
tr '\r\n' ' '
```
