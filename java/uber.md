출처 : [https://stackoverflow.com/questions/11947037/what-is-an-uber-jar](https://stackoverflow.com/questions/11947037/what-is-an-uber-jar)

# uber jar는 무엇입니까?

저는 maven 문서를 읽고 있었는데 "uber jar"라는 이름이 나왔습니다.

uber-jar의 의미는 무엇이며 그 특징 / 장점은 무엇입니까?

---

## 4 개의 답변 중 1 개의 답변만 추려냄.

 `Über`는 독일어로 `above` 또는 `over`입니다. 이전 국가의 한 줄 가사로 나옵니다. `Deutschland, Deutschland, über alles`(독일, 모든 다른 것 위에 독일)

 그래서 이 문맥에서 "uber-jar"는 간단한 jar보다 한 단계 "위의 jar" 입니다. "uber-jar"는 하나의 jar 파일에 당신의 패키지와 모든 의존성을 포함하여 정의됩니다. 그 이름은 "보통을 뛰어넘는다"와 비슷한 뜻을 가지는 ultrageek, superman, hyperspace, 그리고 metadata와 동일한 의미에서 비롯된 것으로 생각할 수 있습니다.

 장점은 uber-jar가 실제로 종속성이 없으므로 의존성에 관련하여 설치된 게 있는지 여부에 상관없이 이를 배포할 수 있습니다.

 uber-jar 내에서 자신의 모든 의존성은 해당 종석성의 모든 종속성과 마찬가지로 그 uber-jar 내부에 있습니다. 
