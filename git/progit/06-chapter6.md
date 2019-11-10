# 6 GitHub

## 6.1 계정 만들고 설정하기
* GitHub : 가장 큰 Git 저장소 호스트
* 계정을 생성해서 관리하는 방법
* Git 저장소를 만들고 사용하는 방법
* 프로젝트에 기여하거나 다른 사람의 기여를 받아들이는 방법
* 프로그래밍 가능한 GitHub 인터페이스

### 계정 만들고 설정하기

![](https://git-scm.com/book/en/v2/images/signup.png)
* [https://github.com](https://github.com/)에 방문해서 사용자이름과 이메일 주소, 암호를 입력하고 “Sign up for GitHub” 이라는 큰 녹색 버튼 클릭

#### SSH 사용하기
* SSH 리모트를 쓰려면 공개키를 설정해야 함.
* 아래 Windows의 오른쪽 꼭대기에 있는 계정 설정 링크를 클릭
![](https://git-scm.com/book/en/v2/images/account-settings.png)

* 왼쪽 메뉴에서 “SSH keys” 를 선택

![](https://git-scm.com/book/en/v2/images/ssh-keys.png)
* 여기서 "`Add an SSH key`" 버튼을 클릭
* `~/.ssh/id_rsa.pub` 파일의 내용을 입력 칸에 복사해 넣음
* “Add key” 버튼을 클릭

#### 아바타
* “SSh Keys” 탭 위에 있는 “Profile” 탭으로 가서 “Upload new picture” 를 클릭

![](https://git-scm.com/book/en/v2/images/your-profile.png)

* 여러분의 하드디스크에 있을 Git 로고를 선택하고 필요한 만큼 자름

![](https://git-scm.com/book/en/v2/images/avatar-crop.png)

#### 사용자 이메일 주소
* GitHub는 Git 커밋에 있는 이메일 주소를 보고 어떤 사용자인지 식별
* 사용자가 이메일 주소를 여러 개 사용해서 커밋했어도 GitHub에 그 이메일을 모두 등록하기만 했으면 GitHub는 잘 처리함.
* “Emails” 화면에서 모두 등록

![](https://git-scm.com/book/en/v2/images/email-settings.png)

#### 투팩터 인증
* 2FA 설정 화면은 계정 설정 페이지의 Security 탭에 있음.

![](https://git-scm.com/book/en/v2/images/2fa-1.png)
* “TOTP(Time based One-Time 암호” 를 생성하는 스마트폰 앱을 사용하는 방식
* GitHub가 인증 코드를 SMS로 전송해주는 방식
* 마음에 드는 인증 방법을 고르고 지시에 따라 2FA를 설정

## 6.2 GitHub - GitHub 프로젝트에 기여하기

### GitHub 프로젝트에 기여하기
* 프로젝트에 참여하는 방법

#### 프로젝트 Fork 하기
* 참여하고 싶은 프로젝트가 생기면 아마 그 프로젝트에 Push 할 권한은 없을 테니까 “Fork” 해야 한다
* “Fork” 하면 GitHub이 프로젝트를 통째로 복사함.
* 그 복사본은 사용자 네임스페이스에 있고 Push 할 수 있음.
* 프로젝트 페이지를 방문해서 오른쪽 꼭대기에 있는 “Fork” 버튼을 클릭

![](https://git-scm.com/book/en/v2/images/forkbutton.png)

#### GitHub 플로우
* [Git 브랜치](https://git-scm.com/book/ko/v2/ch00/ch03-git-branching) 에서 설명했던 [토픽 브랜치](https://git-scm.com/book/ko/v2/ch00/_topic_branch) 중심으로 일하는 방식

1.  프로젝트를  `Fork`  한다.
    
2.  `master`  기반으로 토픽 브랜치를 만든다.
    
3.  뭔가 수정해서 커밋한다.
    
4.  자신의 GitHub 프로젝트에 브랜치를 Push 한다.
    
5.  GitHub에 Pull Request를 생성한다.
    
6.  토론하면서 그에 따라 계속 커밋한다.
    
7.  프로젝트 소유자는 Pull Request를 Merge 하고 닫는다.

#### Pull Request 만들기
* 매초 깜빡이는 것보다 3초에 한 번 깜빡이는 게 더 좋을 것 같음. 
* 프로그램을 수정하고 원 프로젝트에 다시 보내기

```
```console
$ git clone https://github.com/tonychacon/blink (1)
Cloning into 'blink'...

$ cd blink
$ git checkout -b slow-blink (2)
Switched to a new branch 'slow-blink'

$ sed -i '' 's/1000/3000/' blink.ino (macOS) (3)
# If you're on a Linux system, do this instead:
# $ sed -i 's/1000/3000/' blink.ino (3)

$ git diff --word-diff (4)
diff --git a/blink.ino b/blink.ino
index 15b9911..a6cc5a5 100644
--- a/blink.ino
+++ b/blink.ino
@@ -18,7 +18,7 @@ void setup() {
// the loop routine runs over and over again forever:
void loop() {
  digitalWrite(led, HIGH);   // turn the LED on (HIGH is the voltage level)
  [-delay(1000);-]{+delay(3000);+}               // wait for a second
  digitalWrite(led, LOW);    // turn the LED off by making the voltage LOW
  [-delay(1000);-]{+delay(3000);+}               // wait for a second
}

$ git commit -a -m 'three seconds is better' (5)
[slow-blink 5ca509d] three seconds is better
 1 file changed, 2 insertions(+), 2 deletions(-)

$ git push origin slow-blink (6)
Username for 'https://github.com': tonychacon
Password for 'https://tonychacon@github.com':
Counting objects: 5, done.
Delta compression using up to 8 threads.
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 340 bytes | 0 bytes/s, done.
Total 3 (delta 1), reused 0 (delta 0)
To https://github.com/tonychacon/blink
 * [new branch]      slow-blink -> slow-blink
```

1.  Fork 한 개인 저장소를 로컬에 Clone 한다.
    
2.  무슨 일인지 설명이 되는 이름의 토픽 브랜치를 만든다.
    
3.  코드를 수정한다.
    
4.  잘 고쳤는지 확인한다.
    
5.  토픽 브랜치에 커밋한다.
    
6.  GitHub의 개인 저장소에 토픽 브랜치를 Push 한다.

* Fork 한 내 저장소에 가면 GitHub은 토픽 브랜치가 하나 Push 됐다는 것을 알려주고 원 저장소에 Pull Request를 보낼 수 있는 큰 녹색 버튼이 있음.

![](https://git-scm.com/book/en/v2/images/blink-02-pr.png)

* 왜 수정했는지 얼마나 가치 있는지 설명해서 관리자를 설득해야함.
* 수정된 내용을 “unified diff” 형식으로 보여줌. 이 수정 내용이 프로젝트 관리자가 Merge 할 내용임.

![](https://git-scm.com/book/en/v2/images/blink-03-pull-request-open.png)

* _Create pull request_ 버튼을 클릭하면 프로젝트 원소유자는 누군가 코드를 보냈다는 알림을 받음

#### Pull Request 놓고 감 놓고 배 놓기
* Pull Request가 오면 프로젝트 소유자는 변경 점이 무엇인지 확인한 후, Merge 혹은 거절하거나 코멘트를 달 수 있음.

![](https://git-scm.com/book/en/v2/images/blink-04-pr-comment.png)

* 관리자가 코멘트를 달면 Pull Request를 만든 사람에게 알림이 간다

![](https://git-scm.com/book/en/v2/images/blink-04-email.png)

* [Pull Request 토론 페이지](https://git-scm.com/book/ko/v2/ch00/_pr_discussion)를 보면 프로젝트 소유자가 코드에 코멘트를 달거나 Pull Request 자체에 코멘트를 달면서 토론

![](https://git-scm.com/book/en/v2/images/blink-05-general-comment.png)

* Tony는 자신이 작업한 내용을 코멘트로 남겼다. 그러면 프로젝트 소유자는 무슨 일이 있었는지 쉽게 알 수 있음.

![](https://git-scm.com/book/en/v2/images/blink-06-final.png)
* GitHub은 Pull Request가 Merge 될 수 있는지 검사해서 서버에서 Merge 할 수 있도록 Merge 버튼을 제공
* 이 버튼은 저장소에 쓰기 권한이 있는 사람만 볼 수 있음
* `master` 브랜치에 Merge 해서 GitHub에 Push 하면 자동으로 해당 Pull Request가 닫힘.
* 마지막에는 Merge하고 Request를 닫음.

#### Pull Request 팁
##### Patch를 Pull Request로 보내기
* Pull Request의 Patch가 완벽하고 큐처럼 꼭 순서대로 적용돼야 한다고 생각하지 않음.
* GitHub의 Pull Request는 어떤 주제를 두고 논의하는 자리다. 논의가 다 무르익으면 Merge함.
* 기존 브랜치에 좀 더 커밋하고 Push 했을 뿐
* 나중에 시간이 지나서 이 Pull Request를 다시 읽으면 왜 이런 방향으로 결정했는지에 대한 맥락을 쉽게 알 수 있음.

##### Pull Request를 최신으로 업데이트하기
* [깨끗하게 Merge 할 수 없는 Pull Request](https://git-scm.com/book/ko/v2/ch00/_pr_fail) 같은 메시지를 보면 해당 브랜치를 고쳐서 녹색으로 만들어야 함.
* 녹색으로 해결하는 방법 두가지
	- 대상 브랜치(보통은 `master` 브랜치)를 기준으로 Rebase 하는 방법
	- **대상 브랜치를 Pull Request 브랜치에 Merge 하는 방법**
* 원저자가 뭔가 수정을 했는데 Pull Request와 충돌

```console
$ git remote add upstream https://github.com/schacon/blink (1)

$ git fetch upstream (2)
remote: Counting objects: 3, done.
remote: Compressing objects: 100% (3/3), done.
Unpacking objects: 100% (3/3), done.
remote: Total 3 (delta 0), reused 0 (delta 0)
From https://github.com/schacon/blink
 * [new branch]      master     -> upstream/master

$ git merge upstream/master (3)
Auto-merging blink.ino
CONFLICT (content): Merge conflict in blink.ino
Automatic merge failed; fix conflicts and then commit the result.

$ vim blink.ino (4)
$ git add blink.ino
$ git commit
[slow-blink 3c8d735] Merge remote-tracking branch 'upstream/master' \
    into slower-blink

$ git push origin slow-blink (5)
Counting objects: 6, done.
Delta compression using up to 8 threads.
Compressing objects: 100% (6/6), done.
Writing objects: 100% (6/6), 682 bytes | 0 bytes/s, done.
Total 6 (delta 2), reused 0 (delta 0)
To https://github.com/tonychacon/blink
   ef4725c..3c8d735  slower-blink -> slow-blink
```

1.  원 저장소를 “upstream” 이라는 이름의 리모트로 추가한다
    
2.  리모트에서 최신 데이터를 Fetch 한다
    
3.  대상 브랜치를 토픽 브랜치에 Merge 한다
    
4.  충돌을 해결한다
    
5.  동일한 토픽 브랜치에 도로 Push 한다

* Pull Request는 자동으로 업데이트되고 깨끗하게 Merge 할 수 있는지 재확인

![](https://git-scm.com/book/en/v2/images/pr-02-merge-fix.png)


#### 참조
* 모든 Pull Request와 Issue에는 프로젝트 내에서 유일한 번호를 하나 할당
* 이미 브랜치를 Rebase 했고 Pull Request를 새로 만들었음.

![](https://git-scm.com/book/en/v2/images/mentions-01-syntax.png)

* 이 Pull Request를 보내면 [Pull Request의 상호 참조.](https://git-scm.com/book/ko/v2/ch00/_pr_references_render)처럼 보임

![](https://git-scm.com/book/en/v2/images/mentions-02-render.png)

* 원래 있던 Pull Request를 닫으면 새 Pull Request에는 기존 Pull Request가 닫혔다고 언급됨
* 이 Pull Request에 방문하는 사람은 예전 Pull Request가 닫혔는지 알 수 있고 그 링크가 있어서 바로 클릭해서 예전 것을 볼 수 있음.
* 이슈뿐만 아니라 커밋의 SHA도 참조가능

#### GitHub Flavored Markdown
* Markdown 형식으로 글을 쓰면 그냥 텍스트로 쓴 글이지만 형식을 갖춰 미끈하고 아름답게 렌더링됨

![](https://git-scm.com/book/en/v2/images/markdown-01-example.png)

##### Task List
타스크 리스트는 아래와 같이 사용한다.:

```text
- [X] Write the code
- [ ] Write all the tests
- [ ] Document the code
```

![](https://git-scm.com/book/en/v2/images/markdown-02-tasks.png)

* GitHub은 이슈나 Pull Requests에 있는 타스크 리스트를 집계해서 목록 화면에 보여줌.

* [Pull Request 목록 화면에서 보여주는 타스크 현황.](https://git-scm.com/book/ko/v2/ch00/_task_list_progress)
![](https://git-scm.com/book/en/v2/images/markdown-03-task-summary.png)

##### 코드 조각

* 댓글(코멘트)에 코드 조각도 넣을 수 있음.

```java
for(int i=0 ; i < 5 ; i++)
{
   System.out.println("i is : " + i);
}
```

##### 인용

* 아래와 같이 인용
```text
> Whether 'tis Nobler in the mind to suffer
> The Slings and Arrows of outrageous Fortune,

How big are these slings and in particular, these arrows?
```

* 인용 예제

![](https://git-scm.com/book/en/v2/images/markdown-05-quote.png)

##### Emoji
* 댓글(코멘트)를 쓸 때 `:` 문자로 Emoji 입력가능.

![](https://git-scm.com/book/en/v2/images/markdown-06-emoji-complete.png)

##### 이미지

* 그냥 이미지를 바로 Drag-and-Drop으로 붙여 넣을 수 있음

![](https://git-scm.com/book/en/v2/images/markdown-08-drag-drop.png)

## 6.3 GitHub - GitHub 프로젝트 관리하기

### GitHub 프로젝트 관리하기

#### 새 저장소 만들기

* 대시보드 오른쪽에 있는 “New repository” 버튼클릭

![](https://git-scm.com/book/en/v2/images/newrepo.png)

* 또는 맨 위 툴바의 사용자이름 옆에 있는 `+` 버튼을 클릭

![](https://git-scm.com/book/en/v2/images/newrepoform.png)

#### 동료 추가하기
* 저장소에 커밋 권한을 주고 싶은 동료가 있으면 “Collaborator” 로 추가
* 오른쪽 밑에 있는 `Settings`  링크를 클릭
* 왼쪽 메뉴에서 “Collaborators” 를 선택
* 텍스트 박스에 사용자이름을 입력하고 “Add collaborator” 를 클릭

![](https://git-scm.com/book/en/v2/images/collaborators.png)

#### Pull Request 관리하기
* 프로젝트를 만들고 코드도 넣고 동료가 Push 할 수 있게 세팅함.

##### 이메일 알림
* 어떤 사람이 코드를 수정해서 Pull Request를 보내왔다. 그러면 새로운 Pull Request가 왔다는 메일이 담당자에게 간다.

![](https://git-scm.com/book/en/v2/images/maint-01-email.png)

* `git pull <url> patch-1`라는 명령이 궁금할 텐데 이렇게 하면 리모트 브랜치를 간단히 Merge 가능.

#### Pull Request로 함께 일하기
* 답 메일을 보내면 Pull Request의 코멘트로 달린다
* 먼저 Fork 한 저장소를 리모트로 추가하고 Fetch 해서 Merge함.

* 기서 어떻게 해야 하는지 _command line_ 힌트 링크를 클릭하면 [Merge 버튼과 Pull Request를 수동으로 Merge 하기.](https://git-scm.com/book/ko/v2/ch00/_merge_button)

![](https://git-scm.com/book/en/v2/images/maint-02-merge.png)

* Pull Request를 Merge 하지 않기로 했다면 그냥 닫으면 됨.

#### Pull Request의 Ref
* GitHub는 이럴 때 사용하는 방법을 제공한다. 이 내용은 [Refspec](https://git-scm.com/book/ko/v2/ch00/_refspec)에서 자세히 설명할 거고 조금 어려울 수 있다

#### Pull Request 이어가기
* Pull Request을 어디로 보낼지 대상을 선택 가능.
![](https://git-scm.com/book/en/v2/images/maint-04-target.png)

#### 멘션과 알림
* GitHub는 어떤 팀이나 사람에게 질문하거나 피드백을 받을 수 있도록 쉽고 편한 알림 시스템을 제공

![](https://git-scm.com/book/en/v2/images/maint-05-mentions.png)

* 해당 저장소를 'Watching’하는 상태이거나, 코멘트를 단 경우에도 구독 상태가 됨.
* 알림을 받고 싶지 않으면 화면의 “Unsubscribe” 버튼으로 멈출 수 있음

![](https://git-scm.com/book/en/v2/images/maint-06-unsubscribe.png)

#### 알림 페이지
* 설정의 “Notification center” 탭에 가면 설정할 수 있는 옵션

![](https://git-scm.com/book/en/v2/images/maint-07-notifications.png)
* 알림을 이메일로 받을지 웹으로 받을지 선택가능

#### 웹 알림
* GitHub 사이트에서만 확인가능.
* Notification Center

![](https://git-scm.com/book/en/v2/images/maint-08-notifications-page.png)

#### 이메일 알림
* 이메일 알림을 켜 놓으면 이메일로도 GitHub 알림을 확인가능.

#### 특별한 파일
* GitHub가 사용하는 몇 가지 특이한 파일

#### README
* `README` 파일이든 `README.md` 파일이든 `README.asciidoc` 파일이든 GitHub가 자동으로 렌더링해서 보여줌.

#### CONTRIBUTING
* [CONTRIBUTING 파일이 있음을 보여준다.](https://git-scm.com/book/ko/v2/ch00/_contrib_file)과 같이 링크

![](https://git-scm.com/book/en/v2/images/maint-09-contrib.png)

* 이 파일에는 프로젝트에 기여하는 방법과 Pull Request 규칙 같은 것을 작성.

#### 프로젝트 관리
##### 기본 브랜치 변경하기
* Pull Request를 열 때 설정한 기본 브랜치가 기본으로 선택
* 기본 브랜치는 저장소 설정 페이지의 “Options” 탭에서 변경가능

![](https://git-scm.com/book/en/v2/images/maint-10-default-branch.png)

##### 프로젝트 넘기기
* “Options” 탭을 보면 페이지 아래쪽에 “Transfer ownership” 항목
* `Transfer` 버튼으로 프로젝트를 넘길 수 있음.

![](https://git-scm.com/book/en/v2/images/maint-11-transfer.png)

* 맡던 프로젝트를 다른 사람에게 넘겨주거나 프로젝트가 커져서 Organizaiton 계정으로 옮기고 싶을 때 유용
* 저장소만 옮겨지는 것이 아니라 'Watching’하는 사람이나 'Star’한 사람까지도 함께 옮겨짐.
* URL은 Redirect되는데 웹 접속뿐만 아니라 Clone 이나 Fetch 요청까지도 Redirect됨.
