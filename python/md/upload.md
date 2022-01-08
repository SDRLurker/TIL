출처 : [https://stackabuse.com/how-to-upload-files-with-pythons-requests-library/](https://stackabuse.com/how-to-upload-files-with-pythons-requests-library/)

# Python requests 라이브러리로 파일 업로드하는 방법

## 소개

Python은 HTTP를 통한 데이터 전송을 단순화하는 많은 라이브러리에서 지원됩니다. `requests` 라이브러리는 웹 스크래핑에 많이 사용되기 때문에 가장 인기 있는 Python 패키지 중 하나입니다. 서버와의 상호 작용에도 인기가 있습니다! 라이브러리를 사용하면 JSON과 같은 인기 있는 형식으로 데이터를 쉽게 업로드할 수 있고 파일 업로드도 쉽게 할 수 있습니다.

이 자습서에서는 Python의 `requests` 라이브러리를 사용하여 파일을 업로드하는 방법을 살펴보겠습니다. 이 기사는 `requests` 라이브러리와 `post()` 함수 서명을 다루는 것으로 시작됩니다. 다음으로 `requests` 패키지를 사용하여 단일 파일을 업로드하는 방법을 다룰 것입니다. 마지막으로 하나의 요청으로 여러 파일을 업로드합니다.

