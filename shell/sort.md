출처 : [https://stackoverflow.com/questions/29244351/how-to-sort-a-file-in-place/29244387](https://stackoverflow.com/questions/29244351/how-to-sort-a-file-in-place/29244387)

# 파일 자체를 정렬하는 방법

`sort file` 명령을 사용할 때, 파일의 내용을 정렬하여 보여줍니다. 표준 출력으로부터 얻는 것이 아니라 입력 파일에서 결과를 원하는 데 어떻게 해야 합니까?

---

## 7개 답변 중 1개

당신은 정렬된 출력으로 파일 redirection을 사용할 수 있습니다.

```shell
sort input-file > output_file
sort 입력파일 > 출력파일
```

아니면 당신은 `-o`, `--output=FILE` 옵션을 같은 입력과 출력 파일을 가리키며 정렬하여 사용할 수 있습니다.

```shell
sort -o file file
sort -o 파일 파일
```

파일 이름을 반복하지 않고 ( [bash 중괄호 확장](https://www.gnu.org/software/bash/manual/html_node/Brace-Expansion.html) 으로 )

```shell
sort -o file{,}
sort -o 파일{,}
```

**참고**: 일반적인 실수는 출력을 동일한 입력 파일로 redirect 하는 것입니다(예: `sort file > file`). 이것은 쉘이 redirection(**sort(1)** 프로그램이 아님)을 하고 있기 때문에 작동하지 않으며, **sort(1)** 프로그램에 읽을 기회를 주기 직전에 입력 파일(출력이기도 함)이 지워집니다.
