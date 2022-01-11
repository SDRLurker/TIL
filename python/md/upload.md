출처 : [https://stackabuse.com/how-to-upload-files-with-pythons-requests-library/](https://stackabuse.com/how-to-upload-files-with-pythons-requests-library/)

# Python requests 라이브러리로 파일 업로드하는 방법

## 소개

Python은 HTTP를 통한 데이터 전송을 단순화하는 많은 라이브러리에서 지원됩니다. `requests` 라이브러리는 웹 스크래핑에 많이 사용되기 때문에 가장 인기 있는 Python 패키지 중 하나입니다. 서버와의 상호 작용에도 인기가 있습니다! 라이브러리를 사용하면 JSON과 같은 인기 있는 형식으로 데이터를 쉽게 업로드할 수 있고 파일 업로드도 쉽게 할 수 있습니다.

이 자습서에서는 Python의 `requests` 라이브러리를 사용하여 파일을 업로드하는 방법을 살펴보겠습니다. 이 기사는 `requests` 라이브러리와 `post()` 함수 서명을 다루는 것으로 시작됩니다. 다음으로 `requests` 패키지를 사용하여 단일 파일을 업로드하는 방법을 다룰 것입니다. 마지막으로 하나의 요청으로 여러 파일을 업로드합니다.

## Python requests 라이브러리로 하나의 파일 업로드

이 튜토리얼은 파일을 보내는 방법을 다루며 파일이 어떻게 만들어지는지는 신경 쓰지 않습니다. 따라하기 위해 `my_file.txt`, `my_file_2.txt` 및 `my_file_3.txt`라는 세 개의 파일을 만듭니다.

가장 먼저 해야 할 일은 작업 공간에 `requests` 라이브러리를 설치하는 것입니다. 필수는 아니지만 가상 환경에 라이브러리를 설치하는 것이 좋습니다.

```shell
$ python3 -m venv .
```

전역 Python 설치에 영향을 미치지 않도록 가상 환경을 활성합니다.

```shell
$ . bin/activate
```

이제 `pip`로 `requests` 라이브러리를 설치합시다.

```shell
$ pip install requests
```

코드를 저장할 `single_uploader.py`라는 새 파일을 만듭니다. 해당 파일에서 `requests` 라이브러리를 가져오는 것으로 시작하겠습니다.

```python
import requests
```

이제 파일을 업로드할 준비가 되었습니다! 파일을 업로드할 때 파일을 열고 콘텐츠를 스트리밍해야 합니다. 결국 액세스 권한이 없는 파일은 업로드할 수 없습니다. 우리는 이것을 `open()` 함수로 할 것입니다.

`open()` 함수는 파일의 경로와 [모드](https://stackabuse.com/file-handling-in-python/)라는 두 가지 매개변수를 받습니다. 파일의 경로는 스크립트가 실행되는 위치에 대한 절대 경로 또는 상대 경로일 수 있습니다. 동일한 디렉토리에 파일을 업로드하는 경우 파일 이름만 사용할 수 있습니다.

두 번째 인수인 mode는 `rb`로 표시되는 "바이너리 읽기" 값을 사용합니다. 이 인수는 컴퓨터에 읽기 모드에서 파일을 열고 싶고 파일의 데이터를 바이너리 형식으로 사용하기를 원한다는 것을 알려줍니다.

```python
test_file = open("my_file.txt", "rb")
```

**참고**: 바이너리 모드에서 파일을 읽는 것이 중요합니다. `requests` 라이브러리는 일반적으로 바이트 단위 값인 `Content-Length` 헤더를 결정합니다. 바이트 모드에서 파일을 읽지 않으면 라이브러리가 `Content-Length`에 대해 잘못된 값을 얻을 수 있으며, 이로 인해 파일 제출 중에 오류가 발생할 수 있습니다.

이 튜토리얼에서는 무료 *httpbin* 서비스를 요청할 것입니다. 이 API를 통해 개발자는 HTTP 요청을 테스트할 수 있습니다. 파일을 게시할 URL을 저장하는 변수를 만들어 보겠습니다.

```python
test_url = "http://httpbin.org/post"
```

이제 요청을 할 수 있는 모든 것이 준비되었습니다. `requests` 라이브러리의 `post()` 메서드를 사용하여 파일을 업로드합니다. 이 작업을 수행하려면 서버의 URL과 `files` 속성이라는 두 가지 인수가 필요합니다. 또한 응답을 변수에 저장하고 다음 코드를 작성합니다.

```python
test_response = requests.post(test_url, files = {"form_field_name": test_file})
```

`files` 속성은 사전을 사용합니다. 키는 파일을 수락하는 양식 필드의 이름입니다. 값은 업로드하려는 열린 파일의 바이트입니다.

일반적으로 `post()` 메서드가 성공했는지 확인하기 위해 응답의 HTTP 상태 코드를 확인합니다. 응답(response) 객체 `test_url`의 `ok` 속성을 사용할 수 있습니다. 참이면 HTTP 서버의 응답을 출력합니다. 이 경우 요청을 반향합니다.

```python
if test_response.ok:
    print("Upload completed successfully!")
    print(test_response.text)
else:
    print("Something went wrong!")
```

시도해 봅시다! 터미널에서 당신의 스트립트를 `python`으로 실행합니다.

```shell
python single_uploader.py
```

출력은 다음과 비슷할 것입니다.

```
Upload completed successfully!
{
  "args": {}, 
  "data": "", 
  "files": {
    "form_field_name": "This is my file\nI like my file\n"
  }, 
  "form": {}, 
  "headers": {
    "Accept": "*/*", 
    "Accept-Encoding": "gzip, deflate", 
    "Content-Length": "189", 
    "Content-Type": "multipart/form-data; boundary=53bb41eb09d784cedc62d521121269f8", 
    "Host": "httpbin.org", 
    "User-Agent": "python-requests/2.25.0", 
    "X-Amzn-Trace-Id": "Root=1-5fc3c190-5dea2c7633a02bcf5e654c2b"
  }, 
  "json": null, 
  "origin": "102.5.105.200", 
  "url": "http://httpbin.org/post"
}
```

온전성 검사로 `form_field_name` 값이 파일에 있는 것과 일치하는지 확인할 수 있습니다.

## Python requests 라이브러리로 여러개의 파일 업로드

requests를 사용하여 여러 파일을 업로드하는 것은 단일 파일과 매우 유사하지만 주요 차이점은 list를 사용한다는 것입니다. `multi_uploader.py`라는 새 파일과 다음 설정 코드를 만듭니다.

```python
import requests

test_url = "http://httpbin.org/post"
```

이제 여러 이름과 파일이 있는 사전인 `test_files`라는 변수를 만듭니다.

```python
test_files = {
    "test_file_1": open("my_file.txt", "rb"),
    "test_file_2": open("my_file_2.txt", "rb"),
    "test_file_3": open("my_file_3.txt", "rb")
}
```

이전과 마찬가지로 키는 양식 필드의 이름이고 값은 바이트 단위의 파일입니다.

파일 변수를 튜플 목록으로 만들 수도 있습니다. 각 튜플에는 파일을 수락하는 양식 필드의 이름이 포함되며 그 뒤에 파일 내용이 바이트 단위로 표시됩니다.

```python
test_files = [("test_file_1", open("my_file.txt", "rb")),
              ("test_file_2", open("my_file_2.txt", "rb")),
              ("test_file_3", open("my_file_3.txt", "rb"))]
```

둘 중 하나가 작동하므로 원하는 것을 선택하십시오!

파일 목록이 준비되면 이전과 같이 요청을 보내고 응답을 확인할 수 있습니다.

```python
test_response = requests.post(test_url, files = test_files)

if test_response.ok:
    print("Upload completed successfully!")
    print(test_response.text)
else:
    print("Something went wrong!")
```

이 스트립트를 `python` 명령으로 실행합니다.

```shell
python multi_uploader.py
```

당신은 이 출력을 볼 것입니다.

```
Upload completed successfully!
{
  "args": {}, 
  "data": "", 
  "files": {
    "test_file_1": "This is my file\nI like my file\n", 
    "test_file_2": "All your base are belong to us\n", 
    "test_file_3": "It's-a me, Mario!\n"
  }, 
  "form": {}, 
  "headers": {
    "Accept": "*/*", 
    "Accept-Encoding": "gzip, deflate", 
    "Content-Length": "470", 
    "Content-Type": "multipart/form-data; boundary=4111c551fb8c61fd14af07bd5df5bb76", 
    "Host": "httpbin.org", 
    "User-Agent": "python-requests/2.25.0", 
    "X-Amzn-Trace-Id": "Root=1-5fc3c744-30404a8b186cf91c7d239034"
  }, 
  "json": null, 
  "origin": "102.5.105.200", 
  "url": "http://httpbin.org/post"
}
```

잘 했습니다! 당신은 `requests`로 하나와 여러개의 파일을 업로드할 수 있습니다!

## 결론

이 글에서는 `requests` 라이브러리를 사용하여 Python에서 파일을 업로드하는 방법을 배웠습니다. 단일 파일 또는 여러 파일인 경우 `post()` 메서드로 약간의 조정만 필요합니다. 또한 성공적으로 업로드되었는지 확인하기 위해 응답을 확인했습니다.
