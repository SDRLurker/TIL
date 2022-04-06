출처 : [https://stackoverflow.com/questions/2013091/coloured-git-diff-to-html](https://stackoverflow.com/questions/2013091/coloured-git-diff-to-html)

# Git diff를 HTML에 색칠하여 표시하기

저는 파일에서 변경된 단어를 분명히 보기 위해 `git diff --color-words`를 사용하는 것을 즐깁니다.

![](https://i.stack.imgur.com/HnM4B.png)

하지만 git이나 색칠한 터미널 없이 다른 누군가와 diff를 공유하고 싶습니다. HTML로 **색칠된 터미널 출력**으로 변환할 수 있는 도구나 방법을 아시는 분이 있으신가요?

---

## 9개 답변 중 1개의 답변

```shell
wget "http://www.pixelbeat.org/scripts/ansi2html.sh" -O /tmp/ansi2html.sh
chmod +x /tmp/ansi2html.sh
git diff --color-words --no-index orig.txt edited.txt | \
/tmp/ansi2html.sh > 2beshared.html
```

제가 필요했던 것은 [ANSI](https://en.wikipedia.org/wiki/ANSI_escape_code)를 HTML로 변환하는 것(converter)이었습니다. 그리고 저는 매우 어지간한 것을 [http://www.pixelbeat.org/](http://www.pixelbeat.org/) 에서 발견하였습니다.

참고 : `--color` 또는 `--color-words`를 포함시키지 않으면 색상이 표시되지 않을 수 있습니다. 이는 아마도 파이프(|)로 인해 git diff가 색상을 제외시키기 때문일 수 있습니다.

참고 2 : 특히 Mac을 사용하는 경우 gnu sed 및 awk를 설치해야 할 수 있습니다. `brew install gnu-sed gawk`로 설치 하십시오. 경로에 수동으로 추가해야 할 수도 있습니다. 예 : `ln -s /usr/local/Cellar/gnu-sed/4.2.2/bin/gsed  /usr/local/bin/` 을 사용하십시오.
