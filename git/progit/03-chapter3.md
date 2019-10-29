# 3 Git 브랜치

## 3.1 Git 저장소 만들기
* Git의 브랜치는 매우 가볍다.
* 브랜치를 만들고 그 사이를 이동할 수 있음.
* Git은 브랜치를 만들어 작업하고 나중에 merge 하는 방법을 권장함.

### 브랜치란 무엇인가
* 파일이 3개 있는 디렉토리가 하나 있고 이 파일을 Staging Area에 저장하고 커밋하는 예제

```console
$ git add README test.rb LICENSE
$ git commit -m 'The initial commit of my project'
```

* 이 작업을 마치고 나면 Git 저장소에는 다섯 개의 데이터 개체가 생김.

![](https://git-scm.com/book/en/v2/images/commit-and-tree.png)

* 다시 파일을 수정하고 커밋하면 이전 커밋이 무엇인지도 저장

![](https://git-scm.com/book/en/v2/images/commits-and-parents.png)

* 기본적으로 Git은 `master` 브랜치를 사용.
* 이 `master` 브랜치가 생성된 커밋을 가리킴.
* commit을 만들면 `master` 브랜치는 자동으로 가장 마지막 커밋을 가리킴.
	* `master`는 `git init`할 때 만들어 지는 default branch

![](https://git-scm.com/book/en/v2/images/branch-and-history.png)

#### 새 브랜치 생성하기
* 아래와 같이 `git branch` 명령으로 `testing` 브랜치를 생성.

```console
$ git branch testing
```

![](https://git-scm.com/book/en/v2/images/two-branches.png)

* Git은 'HEAD’라는 특수한 포인터가 있음. 이 포인터는 지금 작업하는 로컬 브랜치를 의미함.

![](https://git-scm.com/book/en/v2/images/head-to-master.png)

* `git log` 명령에 `--decorate` 옵션을 사용하면 쉽게 브랜치가 어떤 커밋을 가리키는지도 확인가능.

```console
$ git log --oneline --decorate
f30ab (HEAD -> master, testing) add feature #32 - ability to add new formats to the central interface
34ac2 Fixed bug #1328 - stack overflow under certain conditions
98ca9 The initial commit of my project
```

#### 브랜치 이동하기

* testing branch로 이동.

```console
$ git checkout testing
```

![](https://git-scm.com/book/en/v2/images/head-to-testing.png)

* HEAD는 testing 브랜치를 가리킨다

* 커밋을 새로 한 번 실행.

```console
$ vim test.rb
$ git commit -a -m 'made a change'
```

![](https://git-scm.com/book/en/v2/images/advance-testing.png)

* `master` 브랜치는 여전히 이전 커밋을 가리킴. `master` 브랜치로 되돌아 감.

```console
$ git checkout master
```

![](https://git-scm.com/book/en/v2/images/checkout-master.png)

* 브랜치를 이동하면 워킹 디렉터리 파일이 변경됨. 워킹 디렉토리의 파일은 그 브랜치에서 가장 마지막으로 했던 작업 내용으로 변경된다.

파일을 수정하고 다시 커밋

```console
$ vim test.rb
$ git commit -a -m 'made other changes'
```

![](https://git-scm.com/book/en/v2/images/advance-master.png)

* 두 작업 내용은 서로 독립적으로 각 브랜치에 존재함. 커밋 사이를 자유롭게 이동하다가 때가 되면 두 브랜치를 Merge.
* `git log --oneline --decorate --graph --all` 이라고 실행하면 히스토리를 출력

```console
$ git log --oneline --decorate --graph --all
* c2b9e (HEAD, master) made other changes
| * 87ab2 (testing) made a change
|/
* f30ab add feature #32 - ability to add new formats to the
* 34ac2 fixed bug #1328 - stack overflow under certain conditions
* 98ca9 initial commit of my project
```

* 다른 버전관리 도구 : 브랜치가 필요할 때 프로젝트를 통째로 복사
* git은 커밋을 할 때마다 이전 커밋의 정보를 저장하기 때문에 Merge 할 때 어디서부터(Merge Base) 합쳐야 하는지 안다

## 3.2 Git 브랜치 - 브랜치와 Merge 의 기초

### 브랜치와 Merge 의 기초

* 브랜치와 Merge는 보통 이런 식으로 진행
1. 웹사이트가 있고 뭔가 작업을 진행하고 있다.
2. 새로운 이슈를 처리할 새 Branch를 하나 생성한다.
3. 새로 만든 Branch에서 작업을 진행한다.

#### 브랜치의 기초
* `master` 브랜치에 커밋을 몇 번 했다고 가정

![](https://git-scm.com/book/en/v2/images/basic-branching-1.png)

* 브랜치를 만들면서 Checkout까지 한 번에 하려면 `git checkout` 명령에 `-b` 라는 옵션을 추가

```
$ git checkout -b iss53
Switched to a new branch "iss53"

# 위와 아래는 같은 의미
$ git branch iss53
$ git checkout iss53
```

![](https://git-scm.com/book/en/v2/images/basic-branching-2.png)

* 이 상태에서 뭔가 일을 하고 커밋하면 `iss53` 브랜치가 앞으로 나아간다.

```
$ vim index.html
$ git commit -a -m 'added a new footer [issue 53]'
```

![](https://git-scm.com/book/en/v2/images/basic-branching-3.png)

* 아직 커밋하지 않은 파일이 Checkout 할 브랜치와 충돌 나면 브랜치를 변경할 수 없음.
	- 이런 문제를 다루는 방법은(주로, Stash이나 커밋 Amend에 대해) 나중에 [Stashing과 Cleaning](https://git-scm.com/book/ko/v2/ch00/_git_stashing) 에서 다룰 것

* 지금은 작업하던 것을 모두 커밋하고 master 브랜치로 옮김.

```
$ git checkout master
Switched to branch 'master'
```

* `hotfix`라는 브랜치를 만들고 새로운 이슈를 해결할 때까지 사용

```
$ git checkout -b hotfix
Switched to a new branch 'hotfix'
$ vim index.html
$ git commit -a -m 'fixed the broken email address'
[hotfix 1fb7853] fixed the broken email address
 1 file changed, 2 insertions(+)
```

![](https://git-scm.com/book/en/v2/images/basic-branching-4.png)

* 최종적으로 운영환경에 배포하기 위히 hotfix 브랜치를 master 브랜치에 합쳐야 한다
* `git merge` 명령으로 아래와 같이 진행

```
$ git checkout master
$ git merge hotfix
Updating f42c576..3a0874c
Fast-forward
 index.html | 2 ++
 1 file changed, 2 insertions(+)
```

![](https://git-scm.com/book/en/v2/images/basic-branching-5.png)

* 이제 `hotfix` 브랜치는 `master` 브랜치에 포함됐고 운영환경에 적용할 수 있는 상태가 되었다고 가정

![](https://git-scm.com/book/en/v2/images/basic-branching-5.png)

* 더 이상 필요없는 `hotfix` 브랜치는 삭제
* `git branch` 명령에 `-d` 옵션을 주고 브랜치를 삭제

```
$ git branch -d hotfix
Deleted branch hotfix (3a0874c).
```

* 이슈 53번을 처리하던 환경으로 되돌아가서 하던 일을 계속진행

```
$ git checkout iss53
Switched to branch "iss53"
$ vim index.html
$ git commit -a -m 'finished the new footer [issue 53]'
[iss53 ad82d7a] finished the new footer [issue 53]
1 file changed, 1 insertion(+)
```

![](https://git-scm.com/book/en/v2/images/basic-branching-6.png)

#### Merge 의 기초
* 53번 이슈를 다 구현하고 master 브랜치에 Merge 하는 과정

```
$ git checkout master
Switched to branch 'master'
```

![](https://git-scm.com/book/en/v2/images/basic-merging-1.png)

* git merge 명령으로 합칠 브랜치에서 합쳐질 브랜치를 Merge 하면됨.

```
$ git merge iss53
Merge made by the 'recursive' strategy.
index.html |    1 +
1 file changed, 1 insertion(+)
```

* 3-way Merge 의 결과를 별도의 커밋으로 만들고 나서 해당 브랜치가 그 커밋을 가리키도록 이동
  - 이런 커밋은 부모가 여러 개고 Merge 커밋이라 부름.

![](https://git-scm.com/book/en/v2/images/basic-merging-2.png)

* 다음 명령으로 브랜치를 삭제하고 이슈의 상태를 처리 완료로 표시

```
$ git branch -d iss53
```

#### 충돌의 기초
* 53번 이슈와 hotfix 가 같은 부분을 수정했다면 Git은 Merge 하지 못하고 아래와 같은 충돌(Conflict) 메시지를 출력

```
$ git merge iss53
Auto-merging index.html
CONFLICT (content): Merge conflict in index.html
Automatic merge failed; fix conflicts and then commit the result.
```

* Merge 충돌이 일어났을 때 Git이 어떤 파일을 Merge 할 수 없었는지 살펴보려면 `git status` 명령을 이용

```
$ git status
On branch master
You have unmerged paths.
  (fix conflicts and run "git commit")

Unmerged paths:
  (use "git add <file>..." to mark resolution)

    both modified:      index.html

no changes added to commit (use "git add" and/or "git commit -a")
```

* 충돌이 일어난 파일은 unmerged 상태로 표시됨.
* 충돌 난 부분은 해당 파일에 아래와 같이 표시됨.

```
<<<<<<< HEAD:index.html
<div id="footer">contact : email.support@github.com</div>
=======
<div id="footer">
 please contact us at support@github.com
</div>
>>>>>>> iss53:index.html
```

* 위쪽의 내용은 `HEAD` 버전(merge 명령을 실행할 때 작업하던 `master` 브랜치)의 내용이고 아래쪽은 `iss53` 브랜치의 내용
* 아래는 아예 새로 작성하여 충돌을 해결하는 예제
	- <<<,===,>>>가 포함된 행 삭제.

```
<div id="footer">
please contact us at email.support@github.com
</div>
```

* 이렇게 충돌한 부분을 해결하고 `git add 명령으로 다시 Git에 저장

* git mergetool 명령으로 실행

```
$ git mergetool

This message is displayed because 'merge.tool' is not configured.
See 'git mergetool --tool-help' or 'git help config' for more details.
'git mergetool' will now attempt to use one of the following tools:
opendiff kdiff3 tkdiff xxdiff meld tortoisemerge gvimdiff diffuse diffmerge ecmerge p4merge araxis bc3 codecompare vimdiff emerge
Merging:
index.html

Normal merge conflict for 'index.html':
  {local}: modified file
  {remote}: modified file
Hit return to start merge resolution tool (opendiff):
```

* Merge 도구를 종료하면 Git은 잘 Merge 했는지 물어본다. 잘 마쳤다고 입력하면 자동으로 git add 가 수행되고 해당 파일이 Staging Area에 저장된다.
* `git status` 명령으로 충돌이 해결된 상태인지 확인.

```
$ git status
On branch master
All conflicts fixed but you are still merging.
  (use "git commit" to conclude merge)

Changes to be committed:

    modified:   index.html
```

* 충돌을 해결하고 나서 해당 파일이 Staging Area에 저장됐는지 확인했으면 git commit 명령으로 Merge 한 것을 커밋

```
Merge branch 'iss53'

Conflicts:
    index.html
#
# It looks like you may be committing a merge.
# If this is not correct, please remove the file
#	.git/MERGE_HEAD
# and try again.


# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
# On branch master
# All conflicts fixed but you are still merging.
#
# Changes to be committed:
#	modified:   index.html
#
```

* 왜 그렇게 해결했는지에 대해서 자세하게 기록
* 자세한 기록은 이 merge commit을 이해하는데 도움을 준다.

## 3.3 Git 브랜치 - 브랜치 관리

### 브랜치 관리

* 브랜치 목록 보기

```
$ git branch
  iss53
* master
  testing
```

* `*` 기호가 붙어 있는 master 브랜치는 현재 Checkout 해서 작업하는 브랜치
* git branch -v 명령을 실행하면 브랜치마다 마지막 커밋 메시지도 함께 보여줌.

```console
$ git branch -v
  iss53   93b412c fix javascript issue
* master  7a98805 Merge branch 'iss53'
  testing 782fd34 add scott to the author list in the readmes
```

* `--merged` 와 `--no-merged` 옵션을 사용하여 Merge 된 브랜치인지 그렇지 않은지 필터링
* `git branch --merged` 명령으로 이미 Merge 한 브랜치 목록을 확인

```
$ git branch --merged
  iss53
* master
```

* 현재 Checkout 한 브랜치에 Merge 하지 않은 브랜치를 살펴보려면 `git branch --no-merged` 명령을 사용

* 아직 Merge 하지 않은 커밋을 담고 있기 때문에 git branch -d 명령으로 삭제되지 않는다

```
$ git branch -d testing
error: The branch 'testing' is not fully merged.
If you are sure you want to delete it, run 'git branch -D testing'.
```

* Merge 하지 않은 브랜치를 강제로 삭제하려면 `-D` 옵션으로 삭제
