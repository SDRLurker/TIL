출처 : [http://git-scm.com/docs/git-log](http://git-scm.com/docs/git-log)

## 이름

git-log - commit history(커밋 히스토리)를 조회합니다.

## 요약

```
git log [<옵션들> [revision range] [[\--] <경로>...]
```

## 설명

commit history(커밋 히스토리) 로그를 조회합니다.

이 명령은 커밋에 관해 보여주는 방법에 대해 제어하는 git rev-list와 각 commit이 어떻게 변했는지 제어하는 git diff-* 명령에 적용될 수 있는 옵션을 사용합니다.

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
