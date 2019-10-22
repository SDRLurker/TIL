# 2 Git의 기초

## 2.1 Git 저장소 만들기

### Git 저장소 만들기
1. 기존 디렉토리를 Git 저장소로 만들기
2. 다른 어딘가에서 Git 저장소를 Clone 하는 방법

#### 1. 기존 디렉토리를 Git 저장소로 만들기
* 프로젝트 디렉토리로 이동.
```shell
$ cd /home/user/my_project
```

* 아래 명령 실행
```shell
project$ git init
```

* 위 명령은 .git 하위 디렉토리를 만듬.
	* (.git 디렉터리) 저장소에 필요한 뼈대 파일(Skeleton)이 만들어짐.

* Git이 파일을 관리하게 하려면 저장소에 파일을 추가하고 commit해야 함.
	* ```git add```로 (commit할) 파일을 추가.
	* ```git commit```으로 커밋.

```shell
project$ git add *.c
project$ git add LICENSE
project$ git commit -m 'initial project version'
```

#### 기존 저장소를 Clone 하기
* ```git clone``` : Git 저장소를 복사할 때 사용.
* git clone <url> 명령으로 저장소를 clone(복제)
	* libgit2 라이브러리 clone 예시

```shell
$ git clone https://github.com/libgit2/libgit2
```

* 아래처럼 다른 디렉터리 이름으로 clone도 가능.

```shell
$ git clone https://github.com/libgit2/libgit2 mylibgit
```

* Git은 다양한 프로토콜을 지원
	* https:// 
	* git://
	* ssh://

## 2.2 Git의 기초 - 수정하고 저장소에 저장하기

### 수정하고 저장소에 저장하기
* Untracked : 관리대상이 아닌 파일. Git 저장소가 모르는 파일.
* Tracked : 스냅샷에 포함된 파일. Git 저장소가 알고 있는 파일.
	* Unmodified(수정하지 않음) / Modified(수정함)
	* Staged : 커밋으로 저장소에 기록할 파일.
* 처음 저장소를 Clone하면 모든 파일은 Tracked이면서 Unmodified 상태.
* Tracked 파일을 수정하면 Git은 그 파일을 Modified 상태로 인식함.
* Git 파일의 라이프사이클

![](https://git-scm.com/book/en/v2/images/lifecycle.png)

#### 파일의 상태 확인하기

* ```git status``` 파일의 상태를 확인
* Clone 한 후 이 명령을 실행하면 다음과 같은 메시지를 볼 수 있음.

```shell
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
nothing to commit, working directory clean
```

* 프로젝트에 `README` 파일 만들기. 
* 이 파일은 새로 만들었기 때문에 `git status`하면 'Untracked files'에 있음.

```shell
$ echo 'My Project' > README
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Untracked files:
  (use "git add <file>..." to include in what will be committed)

    README

nothing added to commit but untracked files present (use "git add" to track)
```

#### 파일을 새로 추적하기

* `git add`로 파일을 새로 추적 가능. `README` 파일을 추적.

```shell
$ git add README
```

`git status` 명령 실행.  `README` 파일이 Tracked 상태이면서 커밋에 추가될 Staged 상태 확인.

```
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    new file:   README
```

#### Modified 상태의 파일을 Stage 하기

* 이미 Tracked 상태인 파일을 수정하는 방법
	* CONTRIBUTED.md라는 파일을 수정한 뒤 git status
```shell
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    new file:   README

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   CONTRIBUTING.md
```

* 이것은 수정한 파일이 Tracked 상태이지만 아직 Staged 상태는 아님.
* `git add`
	* 새로 추적할 때도 사용
	* **수정된 파일을 Staged 상태로 만들 때도** 사용.
	* 다음 커밋에 추가할거라고 받아들이는 게 좋음.
* `git add` 명령을 실행하여 CONTRIBUTED.md Staged 상태. `git status` 명령으로 결과 확인.

```console
$ git add CONTRIBUTING.md
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    new file:   README
    modified:   CONTRIBUTING.md
```

* 아직 더 수정해야 한다는 것을 알게 되어 바로 커밋하지 못하는 상황
* 이 상황에서 `CONTRIBUTING.md` 파일을 열고 수정
* `git status` 명령으로 파일의 상태를 다시 확인

```console
$ vim CONTRIBUTING.md
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    new file:   README
    modified:   CONTRIBUTING.md

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   CONTRIBUTING.md
```

* `git add` 명령을 실행한 후에 또 파일을 수정하면 `git add` 명령을 다시 실행해서 최신 버전을 Staged 상태로 만들어야 함.

```console
$ git add CONTRIBUTING.md
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    new file:   README
    modified:   CONTRIBUTING.md
```

#### 파일 상태를 짤막하게 확인하기
* `git status -s` 또는 `git status --short`

```console
$ git status -s
 M README
MM Rakefile
A  lib/git.rb
M  lib/simplegit.rb
?? LICENSE.txt
```

* 아직 추적하지 않는 새 파일 앞에는 `??` 표시
* 새로 생성한 파일 앞에는 `A` 표시
* 수정한 파일 앞에는 `M` 표시
* 왼쪽에는 Staging Area에서의 상태
* 오른쪽에는 Working Tree에서의 상태

####  파일 무시하기
* 어떤 파일은 Git이 관리할 필요가 없음.
	* 보통 로그 파일이나 빌드 시스템이 자동으로 생성한 파일.
* `.gitignore` 파일의 예

```console
$ cat .gitignore
*.[oa]
*~
```

* `.gitignore`  파일에 입력하는 패턴은 아래 규칙을 따름
	- 아무것도 없는 라인이나, `#`로 시작하는 라인은 무시
    -  표준 Glob 패턴을 사용. 프로젝트 전체에 적용
    -   슬래시(`/`)로 시작하면 하위 디렉토리에 적용되지(Recursivity) 않는다.
    -   디렉토리는 슬래시(`/`)를 끝에 사용하는 것으로 표현한다.    
    -   느낌표(`!`)로 시작하는 패턴의 파일은 무시하지 않는다.

* `.gitignore`  파일의 예

```
# 확장자가 .a인 파일 무시
*.a

# 윗 라인에서 확장자가 .a인 파일은 무시하게 했지만 lib.a는 무시하지 않음
!lib.a

# 현재 디렉토리에 있는 TODO파일은 무시하고 subdir/TODO처럼 하위디렉토리에 있는 파일은 무시하지 않음
/TODO

# build/ 디렉토리에 있는 모든 파일은 무시
build/

# doc/notes.txt 파일은 무시하고 doc/server/arch.txt 파일은 무시하지 않음
doc/*.txt

# doc 디렉토리 아래의 모든 .pdf 파일을 무시
doc/**/*.pdf
```

#### Staged와 Unstaged 상태의 변경 내용을 보기
* `README` 파일을 수정해서 Staged 상태로 만들고 `CONTRIBUTING.md` 파일은 그냥 수정만 함.
* git status

```console
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    modified:   README

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   CONTRIBUTING.md
```

* `git diff` 명령을 실행하면 수정했지만 아직 staged 상태가 아닌 파일을 비교 가능.

```console
$ git diff
diff --git a/CONTRIBUTING.md b/CONTRIBUTING.md
index 8ebb991..643e24f 100644
--- a/CONTRIBUTING.md
+++ b/CONTRIBUTING.md
@@ -65,7 +65,8 @@ branch directly, things can get messy.
 Please include a nice description of your changes when you submit your PR;
 if we have to read the whole diff to figure out why you're contributing
 in the first place, you're less likely to get feedback and have your change
-merged in.
+merged in. Also, split your changes into comprehensive chunks if your patch is
+longer than a dozen lines.

 If you are starting to work on a particular area, feel free to submit a PR
 that highlights your work in progress (and note in the PR title that it's
```

* `git diff --staged` 옵션(`git diff --cached`)
	* 저장소에 커밋한 것과 Staging Area에 있는 것을 비교

```console
$ git diff --staged
diff --git a/README b/README
new file mode 100644
index 0000000..03902a1
--- /dev/null
+++ b/README
@@ -0,0 +1 @@
+My Project
```

* 수정한 파일을 모두 Staging Area에 넣었다면 `git diff` 명령은 아무것도 출력하지 않음.

* `CONTRIBUTING.md` 파일을 Stage 한 후에 다시 수정해도 `git diff` 명령을 사용가능. 이때는 Staged 상태인 것과 Unstaged 상태인 것을 비교

```console
$ git add CONTRIBUTING.md
$ echo '# test line' >> CONTRIBUTING.md
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    modified:   CONTRIBUTING.md

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   CONTRIBUTING.md
```

* `git diff` 명령으로 Unstaged 상태인 변경 부분을 확인

```console
$ git diff
diff --git a/CONTRIBUTING.md b/CONTRIBUTING.md
index 643e24f..87f08c8 100644
--- a/CONTRIBUTING.md
+++ b/CONTRIBUTING.md
@@ -119,3 +119,4 @@ at the
 ## Starter Projects

 See our [projects list](https://github.com/libgit2/libgit2/blob/development/PROJECTS.md).
+# test line
```

##### 외부 도구로 비교하기
* `git difftool` 명령을 사용해서 emerge, vimdiff 같은 도구로 비교

#### 변경사항 커밋하기
* 커밋하기 전에 `git status` 명령으로 모든 것이 Staged 상태인지 확인가능.
* `git commit` 을 실행하여 커밋

```console
$ git commit
```

* `git config --global core.editor` 명령으로 어떤 편집기를 사용할지 설정가능.
* vim 편집기가 아래와 같은 내용을 표시
```

# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
# On branch master
# Your branch is up-to-date with 'origin/master'.
#
# Changes to be committed:
#	new file:   README
#	modified:   CONTRIBUTING.md
#
~
~
~
".git/COMMIT_EDITMSG" 9L, 283C
```

* 정확히 뭘 수정했는지도 보여주려면, `git commit` 에 -v 옵션을 추가하면 편집기에 diff 메시지도 추가

* commit 메시지를 인라인으로 첨부가능. `commit` 명령을 실행할 때 아래와 같이 `-m` 옵션을 사용

```console
$ git commit -m "Story 182: Fix benchmarks for speed"
[master 463dc4f] Story 182: Fix benchmarks for speed
 2 files changed, 2 insertions(+)
 create mode 100644 README
```

*  (`master`) 브랜치에 커밋. 체크섬은 (`463dc4f`).

#### Staging Area 생략하기
* `git commit` 명령을 실행할 때 `-a` 옵션을 추가시 Git은 Tracked 상태의 파일을 자동으로 Staging Area에 넣음.

```console
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   CONTRIBUTING.md

no changes added to commit (use "git add" and/or "git commit -a")
$ git commit -a -m 'added new benchmarks'
[master 83e38c7] added new benchmarks
 1 file changed, 5 insertions(+), 0 deletions(-)
```

#### 파일 삭제하기
* Git의 Staging Area에서 파일을 삭제하려면 `git rm` 명령 사용.
* 작업 디렉터리에서 파일을 삭제하면 Unstaged 상태라고 표시됨.

```console
$ rm PROJECTS.md
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes not staged for commit:
  (use "git add/rm <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

        deleted:    PROJECTS.md

no changes added to commit (use "git add" and/or "git commit -a")
```

* `git rm` 명령을 실행하면 삭제한 파일은 Staged 상태가 됨.

```console
$ git rm PROJECTS.md
rm 'PROJECTS.md'
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    deleted:    PROJECTS.md
```

* `git rm --cached` : Staging Area에서만 제거하고 워킹 디렉토리에 있는 파일은 지우지 않는 방법

```console
$ git rm --cached README
```

* 여러 개의 파일이나 디렉토리를 한꺼번에 삭제가능.

```console
$ git rm log/\*.log
```

* `~`로 끝나는 파일 모두 삭제.

```console
$ git rm \*~
```

#### 파일 이름 변경하기

* git에서 파일명 변경하기 
```console
$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    renamed:    README.md -> README
```

* `git mv` 명령은 아래 명령어를 수행한 것과 같음.

```console
$ mv README.md README
$ git rm README.md
$ git add README
```

## 2.3 Git의 기초 - 커밋 히스토리 조회하기

### 커밋 히스토리 조회하기

* simplegit 프로젝트로 확인.

```console
$ git clone https://github.com/schacon/simplegit-progit
```

* `git log` 명령을 실행

```console
$ git log
commit ca82a6dff817ec66f44342007202690a93763949
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Mon Mar 17 21:52:11 2008 -0700

    changed the version number

commit 085bb3bcb608e1e8451d4b2432f8ecbe6306e7e7
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Sat Mar 15 16:40:33 2008 -0700

    removed unnecessary test

commit a11bef06a3f659402fe7563abf99ad00de2209e6
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Sat Mar 15 10:31:28 2008 -0700

    first commit
```

* `-p`, `--patch 는 각 커밋의 diff 결과를 보여줌.

```console
$ git log -p -2
commit ca82a6dff817ec66f44342007202690a93763949
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Mon Mar 17 21:52:11 2008 -0700

    changed the version number

diff --git a/Rakefile b/Rakefile
index a874b73..8f94139 100644
--- a/Rakefile
+++ b/Rakefile
@@ -5,7 +5,7 @@ require 'rake/gempackagetask'
 spec = Gem::Specification.new do |s|
     s.platform  =   Gem::Platform::RUBY
     s.name      =   "simplegit"
-    s.version   =   "0.1.0"
+    s.version   =   "0.1.1"
     s.author    =   "Scott Chacon"
     s.email     =   "schacon@gee-mail.com"
     s.summary   =   "A simple gem for using Git in Ruby code."

commit 085bb3bcb608e1e8451d4b2432f8ecbe6306e7e7
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Sat Mar 15 16:40:33 2008 -0700

    removed unnecessary test

diff --git a/lib/simplegit.rb b/lib/simplegit.rb
index a0a60ae..47c6340 100644
--- a/lib/simplegit.rb
+++ b/lib/simplegit.rb
@@ -18,8 +18,3 @@ class SimpleGit
     end

 end
-
-if $0 == __FILE__
-  git = SimpleGit.new
-  puts git.show
-end
```

* `--stat` 옵션 : 히스토리 통계

```console
$ git log --stat
commit ca82a6dff817ec66f44342007202690a93763949
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Mon Mar 17 21:52:11 2008 -0700

    changed the version number

 Rakefile | 2 +-
 1 file changed, 1 insertion(+), 1 deletion(-)

commit 085bb3bcb608e1e8451d4b2432f8ecbe6306e7e7
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Sat Mar 15 16:40:33 2008 -0700

    removed unnecessary test

 lib/simplegit.rb | 5 -----
 1 file changed, 5 deletions(-)

commit a11bef06a3f659402fe7563abf99ad00de2209e6
Author: Scott Chacon <schacon@gee-mail.com>
Date:   Sat Mar 15 10:31:28 2008 -0700

    first commit

 README           |  6 ++++++
 Rakefile         | 23 +++++++++++++++++++++++
 lib/simplegit.rb | 25 +++++++++++++++++++++++++
 3 files changed, 54 insertions(+)
```

* `--pretty` 옵션
	* `oneline` 옵션

```console
$ git log --pretty=oneline
ca82a6dff817ec66f44342007202690a93763949 changed the version number
085bb3bcb608e1e8451d4b2432f8ecbe6306e7e7 removed unnecessary test
a11bef06a3f659402fe7563abf99ad00de2209e6 first commit
```

* _저자(Author)_ : 원래 작업을 수행한 원작자
* _커밋터(Committer) : 이 작업을 적용한(저장소에 포함시킨) 사람.
* `oneline` 옵션과 `format` 옵션은 `--graph` 옵션과 함께 사용

```console
$ git log --pretty=format:"%h %s" --graph
* 2d3acf9 ignore errors from SIGCHLD on trap
*  5e3ee11 Merge branch 'master' of git://github.com/dustin/grit
|\
| * 420eac9 Added a method for getting the current branch.
* | 30e367c timeout code and tests
* | 5a09431 add timeout protection to grit
* | e1193f8 support for heads with slashes in them
|/
* d6016bc require time for xmlschema
*  11d191e Merge branch 'defunkt' into local
```

#### 조회 제한조건
* 지난 2주 동안 만들어진 커밋들만 조회하는 명령

```console
$ git log --since=2.weeks
```

* `--author` 옵션으로 저자를 지정 가능.
* `--grep` 옵션으로 커밋 메시지에서 키워드 검색 가능.
* `-S` 코드에서 추가, 제거된 내용 중 특정 테스트가 포함되어 있는지 검색

```console
$ git log -S function_name
```

## 2.4 Git의 기초 - 되돌리기

### 되돌리기
* 완료된 (현재) commit을 수정할 때
  - 어떤 파일을 빼먹었을 때 그리고 커밋 메시지를 잘못 적었을 때 
  - 편집기가 실행되면 이전 커밋 메시지가 자동으로 포함됨

```console
$ git commit --amend
```

* 커밋을 했는데 Stage 하는 것을 깜빡하고 빠트린 파일이 있으면 다음처럼 작업.
  - 2번째 commit 명령은 첫 번째 commit을 덮어씀.

```console
$ git commit -m 'initial commit'
$ git add forgotten_file
$ git commit --amend
```

### 파일 상태를 Unstage로 변경하기
* 두 파일 중 하나를 unstaged로 변경하는 법

```console
$ git reset HEAD CONTRIBUTING.md
Unstaged changes after reset:
M	CONTRIBUTING.md
$ git status
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    renamed:    README.md -> README

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   CONTRIBUTING.md
```

#### Modified 파일 되돌리기
* 수정한 파일을 되돌리는 방법

```console
$ git checkout -- CONTRIBUTING.md
$ git status
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

    renamed:    README.md -> README
```

* Git으로 _커밋_ 한 모든 것은 언제나 복구가능
* 커밋하지 않고 잃어버린 것은 절대로 되돌릴 수 없다!
