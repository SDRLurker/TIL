출처 : [http://git-scm.com/docs/git-log](http://git-scm.com/docs/git-log)

## 이름

git-log - commit log를 보여줍니다.

## 요약

```
git log [<옵션들> [revision range] [[\--] <경로>...]
```

## 설명

commit log를 보여줍니다.

주어진 commit에서 `부모(parent)` 링크를 따라 도달할 수 있는 commit을 나열하지만 앞에 ^가 있는 commit에서 도달 가능한 commit은 제외합니다. 출력은 기본적으로 시간 역순으로 제공됩니다.

이것을 집합 연산으로 생각할 수 있습니다. 명령줄에 지정된 commit에서 도달할 수 있는 commit은 집합을 형성한 다음 앞에 ^가 표시된 커밋에서 도달할 수 있는 커밋을 해당 집합에서 뺍니다. 나머지 커밋은 명령의 출력으로 나오는 것입니다. 다양한 기타 옵션 및 경로 매개변수를 사용하여 결과를 추가로 제한할 수 있습니다.

다음 명령이 이 예시입니다.

```shell
$ git log foo bar ^baz
```

위 명령어는 "foo 또는 bar에서는 도달할 수 있지만 baz에서는 도달할 수 없는 모든 커밋을 나열"을 의미합니다.

특수 표기법 "<commit1>..<commit2>"는 "^<commit1> <commit2>"의 약어로 사용할 수 있습니다. 예를 들어 다음 중 하나를 서로 바꿔서 사용할 수 있습니다.
 
```shell
$ git log A B --not $(git merge-base --all A B)
$ git log A...B
```
 
이 명령은 [git-rev-list[1]](http://git-scm.com/docs/git-rev-list) 명령에 적용 가능한 옵션을 사용하여 표시되는 내용과 방법을 제어하고 [git-diff[1]](http://git-scm.com/docs/git-diff) 명령에 적용 가능한 옵션을 사용하여 각 이 도입하는 변경 사항이 표시되는 방법을 제어합니다.

## 옵션

**--follow**

이름 변경을 포함하여 하나의 파일의 history(히스토리)를 조회합니다. (하나의 파일에 대해서만 작동합니다.)

예시)

```shell
[root@test test]#  git log --follow --oneline ab.txt
1f61b72 second commit      // a.txt ==이름변경==> ab.txt 
00f7289 test - initial commit // a.txt
```

**--all**

`<commit>`의 명령어에 대하여 refs/의 모든 the refs(참조들) 출력될 것입니다.

**--no-decorate**

**--decorate[=short|full|no]**

commit의 the ref(참조) 이름을 출력합니다. short이면 the ref(참조) 이름의 접두사 refs/heads/, refs/tags/, refs/remotes는 출력되지 않을 것입니다. full이면 the ref(접두어를 포함하여)의 전체 참조가 출력될 것입니다. 기본 값은 short입니다.

**--graph**

출력의 왼쪽 부분에 commit history(커밋 히스토리)를 텍스트 기반의 그래픽 표현으로 그립니다.

이는 그래프 history를 적절히 그리기 위해 commit들 사이에 부가적인 행(line)이 있을 수 있습니다.

--no-walk와 결합해서 사용할 수 없습니다.

이는 기본으로 --topo-order 옵션을 내포하지만, --date-order 옵션도 지정될 수 있습니다.

예시)

```shell
 [root@test test]# git log --all --graph --oneline --decorate
* 303c84e (HEAD, dev) test dev - 3
* e0de443 test dev - 2
* dd60246 (master) test master - 1
```
