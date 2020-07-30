출처 

[http://stackoverflow.com/questions/750172/change-the-author-of-a-commit-in-git](http://stackoverflow.com/questions/750172/change-the-author-of-a-commit-in-git)

# Git에서 commit의 author(작가)를 변경하기

저는 학교 컴퓨터로 간단한 스크립트를 작성하면서 (집의 컴퓨터에서 복제된 내 pendrive에 있던 저장소에 있는) Git의 변경사항들을 commit하였습니다. 몇 개를 commit하고 나서 저는 root user로 commit했다는 것을 알았습니다.

제 이름으로 이 commit들의 author(작가)를 변경할 수 있는 방법이 있을까요?

----

## 25 개의 답변 중 1개의 답변만 추려냄.

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

### 하나의 commit

몇몇 댓글달은 분들의 글로부터, 만약 가장 최근 commit만 변경하고 싶으시면 rebase 명령은 필요없습니다. 단지, 다음 명령만 수행하면 됩니다.

```shell
git commit --amend --author "New Author Name <email@address.com>"
```

이는 특정 이름의 author(작가)로 바뀌겠지만 committer는 당신이 git config user.name와 git config user.email으로 설정했던 사용자로 설정될 것입니다. 만약 committer를 같이 설정하고 싶으시면 다음처럼 쓰시면 됩니다.

```shell
git -c user.name="New Author Name" -c user.email=email@address.com commit --amend --reset-author
```









