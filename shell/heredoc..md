출처 : [https://stackoverflow.com/questions/2500436/how-does-cat-eof-work-in-bash](https://stackoverflow.com/questions/2500436/how-does-cat-eof-work-in-bash)

# bash에서 어떻게 "cat << EOF"가 작동합니까?

저는 프로그램(`psql`)에 여러줄의 입력을 넣기(enter) 위해 스크립트를 작성할 필요가 있습니다.

구글링을 좀 한 결과 다음 문법이 작동함을 발견하였습니다.

```shell
cat << EOF | psql ---params
BEGIN;

`pg_dump ----something`

update table .... statement ...;

END;
EOF
```

이는 (`BEGIN;`부터 `END;`까지) 여러줄 문자열을 생성하고 `psql`의 입력으로 pipe 전달을 합니다.

하지만 저는 어떻게/왜 작동하는지 모르겠습니다. 아무나 설명해주실 수 있을까요?

저는 주로 `cat << EOF`를 참조하고 있습니다. 저는 `>`는 파일로 출력하기, >> 파일로 추가(append)하기, `<`는 파일로부터 읽기라는 것을 알고 있습니다.

`<<`는 정확히 무엇을 합니까?

그리고 이에 대한 man page가 있나요?

---

## 9 Answers

이는 표준입력으로 문자열을 제공하는 *heredoc* 포멧으로 불립니다. 더 자세한 내용은 [https://en.wikipedia.org/wiki/Here_document#Unix_shells](https://en.wikipedia.org/wiki/Here_document#Unix_shells) 를 보시면 됩니다.

---

`man bash`에서

> ## Here Documents
> 이러한 유형의 redirection은 특정 단어만 포함된 행(뒤에 공백 없음)이 표시될 때까지 쉘이 현재 소스에서 입력을 읽도록 지시합니다.
>
> 그 지점까지 읽은 모든 행은 명령의 표준 입력으로 사용됩니다.
>
> 여기 문서의 형식은 다음과 같습니다.
> 
> ```
>           <<[-]word
>                   here-document
>          delimiter
>```
>
> 매개 변수 확장, 명령 대체, 산술 확장 또는 경로 이름 확장은 **단어(word)**에서 수행되지 않습니다. **단어(word)**의 문자가 인용된 경우 **구분(delimeter)** 기호는 단어에서 따옴표 부호를 제거한 결과이며 **here-document**의 행은 확장되지 않습니다. **단어(word)**가 따옴표가 없을 경우 **here-document**의 모든 행은 매개 변수 확장, 명령 대체 및 산술 확장의 대상이 됩니다. 후자의 경우 문자 시퀀스 \ <newline>은 무시되며  인용하는 데 `\`, `$` 및 ` 문자를 사용해야 합니다.
>
> redirection 연산자가 `<<-` 이면 모든 탭이 먼저오는 문자가 입력 줄과 **구분(delimiter)** 기호를 포함하는 줄에서 제거됩니다. 이를 통해 쉘 스크립트 내의 here-document를 자연스럽게 들여 쓰기 할 수 있습니다.
