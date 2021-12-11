출처 : [https://stackoverflow.com/questions/10856447/sed-command-find-and-replace-in-even-lines-of-a-file](https://stackoverflow.com/questions/10856447/sed-command-find-and-replace-in-even-lines-of-a-file)

# sed에서 파일의 짝수 줄만 찾아 치환하는 명령

저는 이 stackoverflow에서 신입입니다. 저는 SED를 사용하여 파일의 짝수 줄만 표현식으로 치환하고 싶습니다. 제 문제는 원래 파일의 변화는 원래 파일에서 변한 내용을 저장하는 방법을 생각할 수 없다는 점입니다. (예: 파일에서 변한 내용을 덮어쓰는 방법) 저는 다음을 시도해 보았습니다.

```shell
sed -n 'n;p;' filename | sed 's/aaa/bbb/'
```

하지만 이는 변한 내용을 저장할 수 없습니다. 이에 대한 당신의 도움에 감사드립니다.

---

### 4개의 답변 중 1 개의 답변

다음을 시도하세요.

```shell
sed -i '2~2 s/aaa/bbb/' filename
```

-i 옵션은 `sed`에게 해당 filename에서 작동하도록 표준출력(stdout)으로 수정된 버전을 쓰지 않고 즉 원래 파일을 나두지 않고 파일의 변경 내용을 적용하도록 합니다. `2~2`부분은 명령어를 통해 적용되어야 할 sed의 줄(라인)에 대한 주소입니다. `2~2`는 짝수 줄만 편집(수정)함을 의미합니다. `1~2`는 홀수 줄만 편집(수정)함을 의미합니다. `5~6`는 6줄마다 편집(수정)하고 5번째 줄부터 시작합니다.

-- 역자 추가 내용--

참고주소

https://www.gnu.org/software/sed/manual/html_node/Addresses.html

