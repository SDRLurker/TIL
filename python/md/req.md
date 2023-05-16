**출처**

[https://stackoverflow.com/questions/31684375/automatically-create-requirements-txt](https://stackoverflow.com/questions/31684375/automatically-create-requirements-txt)

# 자동으로 requirements.txt 생성하기

때때로 저는 `github`로부터 파이썬 소스 코드를 다운받지만 모든 의존성이 어떻게 설치되는지 모릅니다. 만약 `requirements.txt` 파일이 없다면 직접 손으로 그것을 만들어야 합니다.  
질문  
파이썬 소스 디렉터리가 주어졌을 때, 자동으로 import 섹션으로부터 `requirements.txt`을 생성하는 것이 가능할까요?

---

## 25 개의 답변 중 2 개의 답변

개발 흐름을 개선하려면 [Pipenv](https://packaging.python.org/en/latest/tutorials/managing-dependencies/#managing-dependencies) 또는 기타 도구를 사용하는 것이 좋습니다.

```
pip3 freeze > requirements.txt  # Python3
pip freeze > requirements.txt  # Python2
```

가상환경을 사용하지 않는다면 [pigar](https://github.com/Damnever/pigar)가 좋은 선택이 될 수 있습니다.

---

자동으로 requirements.txt 생성하기 위해 다음 코드를 사용할 수 있습니다.

pipreqs에 대한 더 관련된 정보는 [여기](https://github.com/bndr/pipreqs)서 찾을 수 있습니다.

가끔 `pip freeze`를 사용할 수 있지만 현재 프로젝트에서 사용하지 않는 패키지를 포함하여 환경의 모든 패키지가 저장됩니다.
