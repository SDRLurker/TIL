출처 

[http://stackoverflow.com/questions/750172/change-the-author-of-a-commit-in-git](http://stackoverflow.com/questions/750172/change-the-author-of-a-commit-in-git)

# Git에서 commit의 author(작가)를 변경하기

저는 학교 컴퓨터로 간단한 스크립트를 작성하면서 (집의 컴퓨터에서 복제된 내 pendrive에 있던 저장소에 있는) Git의 변경사항들을 commit하였습니다. 몇 개를 commit하고 나서 저는 root user로 commit했다는 것을 알았습니다.

제 이름으로 이 commit들의 author(작가)를 변경할 수 있는 방법이 있을까요?

----

## 39 개의 답변 중 1개의 답변

**참고: 이 답변은 SHA1을 변경하므로 이미 push된 branch에서 사용할 때 주의하십시오. 이름의 철자를 수정하거나 오래된 이메일을 업데이트하려는 경우 git을 사용하면 `.mailmap`을 사용하여 기록을 다시 쓰지 않고도 이 작업을 수행할 수 있습니다. 저의 다른 답변을 참조하십시오.**

### Interactive Rebase 사용

당신은 

```shell
git rebase -i -p <변경할 모든 commit 이전의 임의의 HEAD>
```

합니다. 다음에 rebase 파일에서 당신이 변경할(bad) commit에서 "edit"로 변경합니다. 만약 당신의 첫 번째 commit을 변경하고 싶다면, rebase 파일에 (다른 줄에 있는 포멧에 따라) 첫 번째 줄에 commit할 내용을 추가해야 합니다. 그리고 git에게 각 commit을 변경할 때 마다 다음을 수행합니다.

```shell
git commit --amend --author "New Author Name <email@address.com>" 
```

수정 또는 열었던 편집기를 닫았으면 다음을 수행합니다.

```shell
git rebase --continue
```

이 명령을 rebase를 계속 이어서 합니다.

만약 `--no-edit`을 추가함으로서 편집기를 열지 않을 수 있고 그 명령은 다음과 같습니다.

```shell
git commit --amend --author "New Author Name <email@address.com>" --no-edit && \
git rebase --continue
```

### 하나의 commit

몇몇 댓글달은 분들의 글로부터, 만약 가장 최근 commit만 변경하고 싶으시면 rebase 명령은 필요없습니다. 단지, 다음 명령만 수행하면 됩니다.

```shell
git commit --amend --author "New Author Name <email@address.com>"
```

이는 특정 이름의 author(작가)로 바뀌겠지만 committer는 당신이 `git config user.name`와 `git config user.email`으로 설정했던 사용자로 설정될 것입니다. 만약 committer를 같이 설정하고 싶으시면 다음처럼 쓰시면 됩니다.

```shell
git -c user.name="New Author Name" -c user.email=email@address.com commit --amend --reset-author
```

## commit을 merge하는 것에 대한 참고 사항

원래 답변에 약간의 오류가 있었습니다. 현재 `HEAD`와 `<모든 잘못된 커밋 이전의 일부 HEAD>` 사이에 commit을 merge할 경우 `git rebase`는 이를 병합합니다(그런데 GitHub pull 요청을 사용하는 경우 병합이 엄청나게 많을 것입니다. 기록에 커밋). 이것은 매우 자주 매우 다른 기록으로 이어질 수 있으며(중복된 변경 사항이 "리베이스 아웃"될 수 있으므로) 최악의 경우 `git rebase`가 어려운 병합 충돌(이미 commit을 merge했을 때 해결되었을 가능성이 있음)을 해결하도록 요청할 수 있습니다. 해결책은 `git rebase`에 `-p` 플래그를 사용하여 기록의 merge 구조를 보존하는 것입니다. `git rebase`에 대한 맨페이지는 -p 및 -i를 사용하면 문제가 발생할 수 있다고 경고하지만 `BUGS` 섹션에는 "commit을 편집하고 commit 메시지를 다시 작성하면 잘 작동해야 합니다."라고 나와 있습니다.

위의 명령에 `-p`를 추가했습니다. 가장 최근 커밋을 변경하는 경우에는 문제가 되지 않습니다.

## 최신 git 클라이언트용 업데이트(2020년 7월)

`-p` 대신 `--rebase-merge`를 사용합니다(`-p`는 더 이상 사용되지 않으며 심각한 문제가 있음).





