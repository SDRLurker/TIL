참고주소 : [https://stackoverflow.com/questions/7368163/how-can-i-convert-a-string-to-a-float-in-mysql](https://stackoverflow.com/questions/7368163/how-can-i-convert-a-string-to-a-float-in-mysql)

# mysql에서 문자열을 float으로 어떻게 형변환 할 수 있을까요?

저는 위도와 경도 값을 문자열(`VARCHAR`)로 저장한 테이블이 있습니다. 이를 `FLOAT (10,6)`으로 형변환하고 싶습니다.

하지만 `CAST()`와 `CONVERT()`를 사용하여 직관적인 방법으로는 할 수 없는 거 같습니다.

어떻게 이 열(컬럼)을 쉽게 형변환 할 수 있을까요? 한 번에 변환하고 싶습니다.

---

## 3개 답변 중 1개만 추려냄

`CAST()` 설명에서 `DECIMAL` 부분을 놓쳤음을 알게 되었습니다.

> `DECIMAL[(M[,D])]`  
> DECIMAL 데이터 타입의 값으로 형변환 합니다. 옵션 값인 M, D는 정확도 (M은 자리수의 전체 수)이며 십진수 값의 소수점 단위(scale, D는 소수점 자리 뒤에 자리수)입니다. 기본 정확도는 소수점 뒤에 2자리 입니다.

그러므로 다음 쿼리는 작동합니다.

```
UPDATE table SET
latitude = CAST(old_latitude AS DECIMAL(10,6)),
longitude = CAST(old_longitude AS DECIMAL(10,6));
```
