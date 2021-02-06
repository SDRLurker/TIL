출처 : [https://unix.stackexchange.com/questions/3586/what-do-the-numbers-in-a-man-page-mean](https://unix.stackexchange.com/questions/3586/what-do-the-numbers-in-a-man-page-mean)

# man 페이지에 숫자들은 무엇입니까?

예를 들면, 제가 `man ls`를 쳤을 때 저는 `LS(1)`를 볼 수 있습니다. 하지만 `man apachectl`을 쳤을 때 `APACHECTL(8)`을 볼 수 있었고 `man cd`를 쳤다면 `cd(n)`로 끝났습니다.

저는 괄호 안에 숫자의 의미가 무엇인지 궁금합니다.

------

## 8개의 답변 중 1 개의 답변만 추려냄.

숫자는 페이지의 매뉴얼이 무슨 섹션인가를 뜻합니다. 1은 사용자 명령이고 8은 시스템 관리 도구입니다. man 페이지 그 자체(`man man`)는 이를 설명하고 그 표준을 보여줍니다.

```
매뉴얼 섹션
    매뉴얼의 표준 섹션은 다음을 포함합니다.
    
    1 사용자 명령
    2 시스템 콜
    3 C 라이브러리 함수
    4 장치와 특별한 파일
    5 파일 포멧과 규칙
    6 게임 외
    7 기타
    8 시스템 관리 도구 및 대몬
    
매뉴얼 배포는 추가적인 섹션을 주로 포함하는 그 세부사항에 대한 매뉴얼 섹션을 사용자 정의합니다.
```

다른 섹션에 특정 용어가 있을 수 있습니다. (예 섹션 1에 shell 명령으로 `printf` 와 섹션 3에 `stdlib` 라이브러리 함수로써 `printf`) 이런 경우 `man` 명령어에 섹션 번호를 전달하여 원하는 것을 선택하거나 `man -a`를 사용하여 일치하는 모든 페이지를 연속으로 표시할 수 있습니다.

```shell
$ man 1 printf 
$ man 3 printf 
$ man -a printf
```

`man -k` (`apropos` 명령과 같음)를 통해 용어(term)가 어느 섹션에 속하는지 물어볼 수 있습니다. 이는 부분문자열이 일치 하더라도 찾을 것입니다. (`man -k printf`
를 실행한다면 sprintf를 보여줄 것입니다.) 그래서 이를 제한하려면 `^term`를 사용해야 합니다.

```shell
$ man -k '^printf' 
printf (1) - format and print data 
printf (1p) - write formatted output 
printf (3) - formatted output conversion 
printf (3p) - print formatted output 
printf [builtins] (1) - bash built-in commands, see bash(1)
```

섹션에는 때때로 하위 섹션이 포함될 수 있습니다 (예 : 위의 `1p` 및 `3p`의 `p`). `p` 하위 섹션은 POSIX 사양을 위한 것입니다. `x` 하위 섹션은 X Window System 문서 용입니다.
