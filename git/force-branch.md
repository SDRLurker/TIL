출처 : [http://stackoverflow.com/questions/5471174/git-move-branch-pointer-to-different-commit](http://stackoverflow.com/questions/5471174/git-move-branch-pointer-to-different-commit)

# Git: branch 포인터를 특정 commit으로 이동하게 하기.

check-out된 branch의 포인터를 이동하기 위해 `git reset --hard` 명령을 사용할 수 있습니다. 하지만 check-out되지 않은 branch의 포인터를 다른 commit을 가리키도록 이동하는 방법을 알고 싶습니다. (기존에 추적중인 원격 branch같은 다른 정보들은 유지되어야 합니다.)

---

## 11 개의 답변 중 1개의 답변

```shell
git branch -f <branch-name> [<new-tip-commit>]
```

만약 `new-tip-commit` 이 생략되면 기본적으로 현재 commit을 가리킵니다.

`new-tip-commit` 은 branch 이름이 될 수 있습니다 (예, master, origin/master).
