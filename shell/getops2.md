출처 : [https://stackoverflow.com/questions/22058316/bash-getopts-multiple-arguments-or-default-value](https://stackoverflow.com/questions/22058316/bash-getopts-multiple-arguments-or-default-value)

# bash getops 여러 인자 또는 기본 값

저는 bash에서 getops에 관한 질문이 있습니다. 인자의 값이 있다면 그 값을 얻고 그렇지 않으면 기본 값을 사용하고 싶습니다. 따라서 스크립트는 directory와 정수를 취해야 하지만 지정되지 않은 경우 $PWD 및 3이 기본값이어야 합니다.

------

## 2개의 답변 중 1개의 답변

당신은 `while` 루프 이전에 기본 값을 제공할 수 있습니다.

```shell
directory=mydir
depth=123
while getopts "hd:l:" opt; do
    case $opt in
        d ) directory=$OPTARG;;
        l ) depth=$OPTARG;;
        h ) usage
        exit 0;;
        *) usage
        exit 1;;
    esac
done
echo "<$directory> <$depth>"
```
