**출처**

[https://unix.stackexchange.com/questions/295274/grep-to-find-the-correct-line-sed-to-change-the-contents-then-putting-it-back](https://unix.stackexchange.com/questions/295274/grep-to-find-the-correct-line-sed-to-change-the-contents-then-putting-it-back)

# 수정할 라인을 grep으로 찾고, 내용을 sed로 바꾼 뒤 원래 파일에 적용하는 방법?

저는 파일에서 특정 라인에 한 단어를 바꾸려고 합니다만 이 모두를 연결하는데 몇가지 문제가 있습니다.

기본적으로 제 파일의 한 라인은 키워드 'firmware\_revision'이며 이 라인에서만 'test' 단어를 'production'으로 변경하고 싶습니다.

저는 이렇게 해 보았습니다.

```
grep 'firmware_revision' myfile.py | sed 's/test/production'
```

이 방법으로 제가 원하는 라인을 선택하여 대체를 실행할 수 있겠지만, 이전 줄을 대체하기 위해 새 줄을 원본 파일로 적용하는 방법은 알 수 없습니다. 분명히 파일로 다시 redirection할 수는 없습니다. 어떻게 해야 할까요?

임시 파일을 사용하더라도 `grep`을 사용하여 필요한 줄만 가져 오면 파일의 다른 모든 데이터가 손실되므로 더 이상 모든 데이터를 임시 파일로 리디렉션 한 다음 원본을 임시 파일로 바꿀 수 없습니다.

편집-누군가가 추가 정보를 요청했습니다.

다음과 같은 줄로 가득 찬 파일이 있다고 가정해 봅시다.

```
[
  ('key_name1', str, 'value1', 'Description'),
  ('key_name2', str, 'value2', 'Description'),
  ('key_name3', str, 'value3', 'Description'),
  ('firmware_revision', str, 'my-firmware-name-test', 'Firmware revision name')
]
```

이제 'firmware\_revision'이 포함된 줄을 찾고 해당 줄에서 'test'라는 단어의 모든 인스턴스를 'production'으로 변경하는 스크립트 (이상적으로는 한 줄짜리)를 작성하고 싶습니다. '테스트'라는 단어는 해당 파일의 다른 위치에 있을 수 있으며 변경하지 않기를 바랍니다. 명확하게 하기 위해 위의 줄을 다음과 같이 변경하고 싶습니다.

```
('firmware_revision', str, 'my-firmware-name-production', 'Firmware revision name')
```

어떻게 해야 합니까?

---

## 2개 답변 중 1개

다음을 시도해 보세요.

```shell
sed -i.bak '/firmware_revision/ s/test/production/' myfile.py
```

여기 `/firmware_revision/`은 조건처럼 작동합니다. 정규식 `firmware_revision`과 매칭되는 라인이면 true(참) 이고 다른 라인에 대해서는 false(거짓)입니다. 조건이 true이면 다음 명령이 실행됩니다. 이 경우, 저 명령은 `test`를 `production`으로 처음 나왔을 때 대체하는 명령입니다.

다른 말로, 명령어 `s/test/production/`은 `firmware_revision`의 정규식과 매칭되는 라인에서만 실행됩니다. 모든 다른 라인은 변경되지 않고 지나갑니다.

기본적으로 sed는 표준출력으로 그것을 보냅니다. 하지만, 당신이 파일에 변경사항을 적용하고 싶다면, `-i` 옵션을 추가할 수 있습니다. 특별히, `-i.bak`은 `bak` 확장자로 지정된 백업 복사본으로 파일이 변경 되도록 합니다.

명령이 작동한다고 결정하고 위험하게 백업을 생성하지 않으려면 GNU sed (Linux)를 사용하여 다음을 사용하십시오.

```shell
sed -i '/firmware_revision/ s/test/production/' myfile.py
```

반면, BSD (OSX)에서는 `-i` 옵션은 argument를 가져야 합니다. 만약 백업을 남겨놓지 않길 바라면, 비어있는 argument를 제공해야 합니다. 다음처럼 사용하십시오.

## 편집

질문에 대한 편집에서 OP는 라인의 _모든_ `test` 발생이 `production`으로 대체 되도록 요청합니다. 이 경우 전역 (해당 행) 대체를 위해 대체 명령에 `g` 옵션을 추가합니다.

```shell
sed -i.bak '/firmware_revision/ s/test/production/g' myfile.py
```
