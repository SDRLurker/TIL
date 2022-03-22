출처 : [https://stackoverflow.com/questions/43267157/python-attributeerror-module-object-has-no-attribute-ssl-st-init](https://stackoverflow.com/questions/43267157/python-attributeerror-module-object-has-no-attribute-ssl-st-init)

# 파이썬 AttributeError: 'module' 객체는 'SSL\_ST\_INIT' 속성이 없습니다

저의 파이썬 스크립트는 다음 오류가 발생했습니다.

```python
Traceback (most recent call last):
  File "./inspect_sheet.py", line 21, in <module>
    main()
  File "./inspect_sheet.py", line 12, in main
    workbook_name=workbook_name,
  File "./google_sheets.py", line 56, in __init__
    self.login()
  File "./google_sheets.py", line 46, in login
    self.client = gspread.authorize(credentials)
  File "/usr/local/lib/python2.7/site-packages/gspread/client.py", line 335, in authorize
    client.login()
  File "/usr/local/lib/python2.7/site-packages/gspread/client.py", line 98, in login
    self.auth.refresh(http)
  File "/usr/local/lib/python2.7/site-packages/oauth2client/client.py", line 598, in refresh
    self._refresh(http.request)
  File "/usr/local/lib/python2.7/site-packages/oauth2client/client.py", line 769, in _refresh
    self._do_refresh_request(http_request)
  File "/usr/local/lib/python2.7/site-packages/oauth2client/client.py", line 795, in _do_refresh_request
    body = self._generate_refresh_request_body()
  File "/usr/local/lib/python2.7/site-packages/oauth2client/client.py", line 1425, in _generate_refresh_request_body
    assertion = self._generate_assertion()
  File "/usr/local/lib/python2.7/site-packages/oauth2client/client.py", line 1554, in _generate_assertion
    private_key, self.private_key_password), payload)
  File "/usr/local/lib/python2.7/site-packages/oauth2client/crypt.py", line 162, in from_string
    from OpenSSL import crypto
  File "/usr/local/lib/python2.7/site-packages/OpenSSL/__init__.py", line 8, in <module>
    from OpenSSL import rand, crypto, SSL
  File "/usr/local/lib/python2.7/site-packages/OpenSSL/SSL.py", line 118, in <module>
    SSL_ST_INIT = _lib.SSL_ST_INIT
AttributeError: 'module' object has no attribute 'SSL_ST_INIT'
```

## 22개의 답변 중 1개의 답변

저는 pip로 `pyopenssl` 업그레이드 하는 것이 `pip`와 관련된 명령어 중에서는 작동하는 것이 없었습니다. `easy_install`로 `pyopenssl`을 업그레이드 함으로써, 위의 문제는 해결될 수 있습니다.

```python
sudo python -m easy_install --upgrade pyOpenSSL
```

credit @delimiter ([Answer](https://stackoverflow.com/questions/45188413/python-pip-install-is-failing-with-attributeerror-module-object-has-no-att#answer-46475845))

이것은 (xenial의 stock python-openssl) 버전 0.15.1?로 업그레이드한 버전 17.3.0을 설치했습니다. 이후 버전에서 앞에 0을 삭제했을 수 있는 버전의 대규모 변경에 유의하십시오.

어쨌든 그 후 pip와 ansible이 다시 작동하기 시작했습니다.
