# 1 시작하기

## 1.1 버전 관리란?

### 버전 관리란?
* 파일 변화를 시간에 따라 기록했다가 나중에 특정 시점의 버전을 다시 꺼내올 수 있는 시스템
* 각 파일, 프로젝트를 이전 상태로 되돌릴 수 있음.
* 시간에 따라 수정 내용을 비교할 수 있음.
* 누가 문제를 일으켰는지 추적 가능.
* 누가 언제 만들어낸 이슈인지도 알 수 있음.

### 로컬 버전 관리
* 고전적? 디렉터리로 파일을 복사하는 방법 사용. (디렉터리 이름이 시간을 넣는 등)
* 로컬 VCS : 간단한 DB를 사용해 파일의 변경 정보를 관리.

### 중앙집중식 버전 관리(CVCS)
* 서버가 별도로 있고 클라이언트가 중앙 서버에서 파일을 받아 사용(checkout)함.
* 서버 관리자는 누가 무엇을 할지 꼼꼼히 관리 가능.
* 중앙 서버가 다운되면 아무도 다른 사람과 협업이 불가.
* 중앙 DB에 문제가 생기면 프로젝트의 모든 히스토리를 잃음.

### 분산 버전 관리 시스템
* Clone : 저장소를 히스토리와 더불어 전부 복제함.
* 서버가 문제가 생기면 이 복제물로 다시 작업 가능.

## 1.2 시작하기 - 짧게 보는 Git의 역사
* Linux Kernel과 BitKeeper의 관계가 틀어짐. BitKeeper의 무료 사용이 깨짐.
* Linux 개발 커뮤니티(Linus Torvalds)가 자체 도구를 만드는 계기가 됨.
* Git의 목표
  - 빠른 속도
  - 단순한 구조
  - 비선형적인 개발(수천 개의 동시 다발적인 branch)
  - 완벽한 분산
  - Linux 커널 같은 대형 프로젝트에도 유용할 것.

## 1.3 시작하기 - Git 기초

### 차이가 아니라 스냅샷
* CVS, Subversion : 각 파일의 변화(**델타 기반** 버전 관리 시스템)를 시간순으로 관리하면서 파일들의 집합을 관리. 

![](https://git-scm.com/book/en/v2/images/deltas.png)

* Git : 버전에 따라 파일이 존재하는 그 순간(스냅샷)을 중요하게 여김. 이전 상태의 파일을 새로 저장하지 않고 링크만 저장.

![](https://git-scm.com/book/en/v2/images/snapshots.png)

### 거의 모든 명령을 로컬에서 실행
* 프로젝트의 히스토리를 조회할 때 로컬 데이터베이스에서 히스토리를 읽어서 보여준다.
* 현재 버전과 이전 상태를 비교할 때도 현재 파일과 이전 상태의 파일을 로컬에서 찾아서 비교.

### Git의 무결성
* 데이터를 저장하기 전에 항상 checksum을 구하고 checksum으로 데이터를 관리.
* SHA-1 해시를 사용하여 checksum을 만듬.
* Hash : [https://namu.wiki/w/%ED%95%B4%EC%8B%9C](https://namu.wiki/w/%ED%95%B4%EC%8B%9C)

### Git은 데이터를 추가할 뿐
* 일단 스냅샷을 commit하면 데이터를 잃어버리기 어렵다.

### 세 가지 상태
* Committed : 데이터가 로컬 DB에 안전하게 저장됨을 의미 (물건을 지른 상태)
* Modified : 수정한 파일을 아직 로컬 DB에 커밋하지 않은 상태.
* Staged : 수정한 파일을 commit할 것이라고 표시한 상태 (장바구니에 넣은 상태) - Index에 해당 정보 저장.

![](https://git-scm.com/book/en/v2/images/areas.png)

* Git이 하는 일
1. Working Tree(작업 디렉터리)에서 파일을 수정.
2. Staging Area에 파일을 Stage 해서 커밋할 스냅샷을 만듬. 모든 파일 또는 선택하여 추가 가능.
3. Staging Area에 있는 파일을 커밋해서 Git 디렉터리에 영구적인 스냅샷으로 저장.

## 1.4 시작하기 - CLI
* CLI = Command Line Interface
* Git의 모든 기능을 지원하는 것은 CLI

## 1.5 시작하기 - Git 설치
### Linux에 설치
* Fedora(RHEL, CentOS) 계열

```shell
$ sudo dnf install git-all
$ sudo yum install git
```

* Debian(Ubuntu등) 계열

```shell
$ sudo apt install git-all
```

### Mac에 설치
* git을 실행하면 설치가 시작됨.

```shell
$ git --version
```

* http://git-scm.com/download/mac

### Windows에 설치
* http://git-scm.com/download/win에 가면 자동 다운로드 시작.
* Git Chocolatey 패키지 - https://chocolatey.org/packages/git
* Github Desktop - http://desktop.github.com/

## 1.6 시작하기 - Git 최초 설정
### Git 최초 설정
* git config 설정 내용을 확인, 변경 가능.
1. /etc/gitconfig : 모든 사용자와 모든 저장소에 적용되는 설정. ```git config --system``` 옵션으로 이 파일을 읽고 쓸 수 있음.
2. ~/.gitconfig, ~/.config/git/config : 현재 사용자에게만 적용되는 설정. ```git config --global``` 옵션으로 이 파일을 읽고 쓸 수 있음.
3. .git/config : Git 디렉터리에 있고 특정 저장소에만 적용됨. ```--local``` 옵션(디폴트)을 사용하면 이 파일을 사용하도록 지정가능.
### 사용자 정보
* 사용자 이름과 이메일 주소를 설정해야 함.
* commit할 때 이 정보를 사용.

```console
$ git config --global user.name "John Doe"
$ git config --global user.email johndoe@example.com
```

### 편집기
* Git은 기본적으로 시스템 편집기를 사용.
* Emacs 같은 다른 편집기를 사용할 수 있음.

```shell
$ git config --global core.editor emacs
```

### 설정 확인
* git config --list 

```console
$ git config --list
user.name=John Doe
user.email=johndoe@example.com
color.status=auto
color.branch=auto
color.interactive=auto
color.diff=auto
...
```

## 1.7 시작하기 - 도움말 보기
* 도움말 보는 방법

```
$ git help <verb>
$ man git-<verb>
```

* git config 도움말

```console
$ git help config
```

* 각 명령에서 사용할 수 있는 옵션(-h, --help)

```
$ git add -h
```
