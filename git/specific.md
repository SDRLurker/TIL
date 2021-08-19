출처 : [http://stackoverflow.com/questions/1186535/how-to-modify-a-specified-commit-in-git](http://stackoverflow.com/questions/1186535/how-to-modify-a-specified-commit-in-git)

# Git에서 특정 commit만 수정하는 방법?

저는 리뷰하면서 commit의 모든 목록을 제출합니다. 만약, 

1. `HEAD`
2. `Commit3`
3. `Commit2`
4. `Commit1`

저는 `git commit --amend`로 head commit을 수정할 수 있다는 걸 알지만, 어떻게 `HEAD` commit이 아닌 `Commit1`을 수정할 수 있을까요?

----

## 16 개의 답변 중 1개의 답변만 추려냄.

당신은 [git rebase](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase)를 사용할 수 있습니다. 만약 commit `bbc643cd`으로 돌아가서 수정하고 싶으시면, 다음을 실행합니다.

```shell
$ git rebase --interactive 'bbc643cd^'
```

당신이 [수정하기 원하는 commit의 *이전* commit](https://stackoverflow.com/questions/1955985/what-does-the-caret-character-mean)을 다시 rebase 하기 때문에 명령어 마지막에 캐럿 `^`을 주의하세요.

기본 편집기로, 언급한 'bbc643cd' 줄에서 `pick`을 `edit`로 변경합니다. 

파일을 저장하고 종료합니다. git은 파일의 명령어들을 자동으로 실행하고 해석할 것입니다. 당신이 commit `bbc643cd` 을 만들었던 이전 상황에서 스스로 찾을 수 있을 것입니다.

이 지점에서, `bbc643cd`은 마지막 commit이고 당신이 쉽게 수정할 수 있습니다. 원하는 대로 작업 디렉터리를 변경하고 이전과 같은 메세지로 다음처럼 commit 합니다.

```shell
$ git commit --all --amend --no-edit
```

commit을 변경하고, 다음을 입력하여

```shell
$ git rebase --continue
```

이전 HEAD commit으로 돌아옵니다.

**경고** : 이는 **모든 자식들까지 포함하여** SHA-1 commit이 바뀌게 됨을 아셔야 됩니다. 다른 말로, 이는 그 지점부터 앞으로 history (commit)을 다시 쓰게 됩니다. 만약 `git push --force` 명령을 사용하여 push를 하면 [이를 수행한 저장소는 망가질 수 있습니다.](https://stackoverflow.com/questions/3926768/amend-a-commit-that-wasnt-the-previous-commit/3926832#3926832)

---

역자주

https://git-scm.com/docs/git-commit

```shell
git commit --all --amend --no-edit
```

* --all : -a와 같은 의미로 수정되거나 삭제된 파일을 자동으로 stage 영역으로 올립니다.
* --amend : commit을 추가하지 않고 마지막 commit을 수정합니다.
* --no-edit : --amend와 결합하여 commit 메세지 변화없이 마지막 commit을 수정합니다.
