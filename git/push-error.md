출처 : [https://stackoverflow.com/questions/2816369/git-push-error-remote-rejected-master-master-branch-is-currently-checked](https://stackoverflow.com/questions/2816369/git-push-error-remote-rejected-master-master-branch-is-currently-checked)

# Git push 에러 '\[원격 거부됨\] master -> master (브랜치는 현재 체크 아웃되었습니다.)'

어제, 저는 한 머신에서 다른 머신으로 [Git](https://en.wikipedia.org/wiki/Git) 저장소를 성공적으로 복제하는 방법 저는 [git clone'을 다른 머신에서 어떻게 할 수 있나요?](https://stackoverflow.com/questions/2808177/how-can-i-git-clone-from-another-machine/2809612#2809612) 에 대한 질문을 작성하였습니다.

저는 성공적으로 제 원본(192.168.1.2)에서 목적지(192.168.1.1)로 Git 저장소를 성공적으로 복제하였습니다.  
하지만 파일을 편집을 하였고 `git commit -a -m "test"`와 `git push`를 하였는데 목적지에서 이 오류가 나왔습니다.

```
git push                                                
hap@192.168.1.2's password: 
Counting objects: 21, done.
Compressing objects: 100% (11/11), done.
Writing objects: 100% (11/11), 1010 bytes, done.
Total 11 (delta 9), reused 0 (delta 0)
error: refusing to update checked out branch: refs/heads/master
error: By default, updating the current branch in a non-bare repository
error: is denied, because it will make the index and work tree inconsistent
error: with what you pushed, and will require 'git reset --hard' to match
error: the work tree to HEAD.
error: 
error: You can set 'receive.denyCurrentBranch' configuration variable to
error: 'ignore' or 'warn' in the remote repository to allow pushing into
error: its current branch; however, this is not recommended unless you
error: arranged to update its work tree to match what you pushed in some
error: other way.
error: 
error: To squelch this message and still keep the default behaviour, set
error: 'receive.denyCurrentBranch' configuration variable to 'refuse'.
To git+ssh://hap@192.168.1.2/media/LINUXDATA/working
! [remote rejected] master -> master (branch is currently checked out)
error: failed to push some refs to 'git+ssh://hap@192.168.1.2/media/LINUXDATA/working'
```

저는 Git(원격에서는 1.7 로컬 머신에서는 1.5)의 2가지 버전을 사용하고 있습니다. 이것이 가능한 이유일까요?

## 30개의 답변 중 1개의 답변만 추려냄

당신은 간단하게 원격 저장소를 bare 저장소로 변경하면 됩니다. (bare 저장소에는 작업 복사본이 없습니다. - 폴더에는 실제 저장소 데이터만 포함됩니다)

당신의 원격 저장소 폴더에서 다음 명령어를 실행합니다.

```
git config --bool core.bare true
```

그리고 나서 .git 폴더를 제외하고 모든 파일을 지웁니다. 그리고 나서 당신은 오류 없이 원격 저장소로 `git push`를 실행할 수 있을 것입니다.
