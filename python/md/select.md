출처 : [https://stackoverflow.com/questions/24720442/selecting-second-child-in-beautiful-soup-with-soup-select](https://stackoverflow.com/questions/24720442/selecting-second-child-in-beautiful-soup-with-soup-select)

# soup.select로 beautiful soup에서 두번째 child 선택하기

다음 태그가 있습니다.

```HTML
<h2 id='names'>Names</h2>
<p>John</p>
<p>Peter</p>
```

이제 h2 태그가 이미 있으면 Peter를 얻기 위한 이제 가장 쉬운 방법은 무엇입니까? 다음을 시도하였습니다.

```HTML
soup.select("#names > p:nth-child(1)")
```

여기에 nth-child에서 NotImplementedError: 오류가 발생하였습니다.

```python
NotImplementedError: Only the following pseudo-classes are implemented: nth-of-type.
```

그래서 나는 여기서 무슨 일이 일어나고 있는지 잘 모르겠습니다. 두 번째 방법은 모든 'p' 태그 자식들을 얻어 직접 \[1\]을 선택하는 것이지만 조금 무식한 try/except 구문으로 Peter를 얻기 위해 전부 시도하는 것을 요구하여 out of range 인덱스 오류의 위험이 있습니다.

soup.select() 함수로 nth-child를 선택하는 방법이 있습니까?

**편집:** 트릭같지만 nth-of-type로 nth-child를 대체합니다. 수정한 내용은:

```python
soup.select("#names > p:nth-of-type(1)")
```

nth-child를 받아들이지 않는 이유가 확실하지 않지만 nth-child와 nth-of-type는 같은 결과를 리턴하는 거 같습니다.

## 3개의 답변 중 1개의 답변만 추려냄

다른 사람이 더 쉽게 찾을 수 있도록 수정 사항을 답변으로 추가 :

`nth-of-type` 대신에 `nth-child`를 사용합니다.

```python
soup.select("#names > p:nth-of-type(1)")
```
