출처 : [https://stackoverflow.com/questions/35115585/remove-file-from-all-commits](https://stackoverflow.com/questions/35115585/remove-file-from-all-commits)

# 모든 commit에서 파일 지우기

저는 폰트 파일을 업로드 했고 github에 (그 폰트파일을) 몇가지 업데이트를 배포할 권리가 없습니다. 

저는 상대적으로 비활성화된 저장소를 가지고 있고 필요할 때 모든 멤버들에게 알릴 수 있는 능력이 있습니다. 저는 이 해결책 중 몇가지를 사용하였습니다. 저는 폰트의 이탈릭체와 진하기 버전의 이름 `%font%`가 있는 `Resources\Video\%font%.ttf` 라고 불리는 디렉터리에 파일을 지울 필요가 있습니다. 제가 사용해야 할 명령어는 무엇일까요?

## 3개의 답변 중 1개의 답변

이러한 경우 당신은 `--tree-filter` 옵션과 함께 [Git Filter Branch](https://git-scm.com/docs/git-filter-branch) 명령을 사용할 수 있습니다. 

문법은 `git filter-branch --tree-filter <명령> ...` 입니다.

```shell
git filter-branch --tree-filter 'rm -f Resources\Video\%font%.ttf' -- --all
```

**갱신된 편집**

`git filter-branch` `--index-filter`는 `--tree-filter`보다 매우 빠릅니다.

```shell
git filter-branch --index-filter 'rm -f Resources\Video\%font%.ttf' -- --all
```

> 윈도우즈에서는 `/` 대신에 `\`를 사용해야 합니다.

*명령어에 대한 설명:*

`< 명령 >` : 구체적인 어떤 shell 명령

`--tree-filter` : Git은 작업 디렉터리에서 각 commit을 체크하고 당신의 명령을 실행하고 다시 commit할 것입니다.

`--index-filter` : Git은 git 히스토리는 갱신하지만 작업 디렉터리는 갱신하지 않습니다.

`--all` : 모든 branch에서 모든 commit을 filter 적용을 합니다.

**참고** : 파일 경로가 확실하지 않으므로 파일 경로를 확인하십시오.

이것이 당신에게 도움이 되기를 바랍니다.
