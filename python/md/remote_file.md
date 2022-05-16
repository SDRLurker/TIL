출처 : [https://stackoverflow.com/questions/14392432/checking-a-file-existence-on-a-remote-ssh-server-using-python](https://stackoverflow.com/questions/14392432/checking-a-file-existence-on-a-remote-ssh-server-using-python)

# Python을 사용하여 원격 SSH 서버에서 파일 존재 확인

A와 B 2개의 서버가 있습니다. A 서버에서 이미지 파일을 B 서버로 보낸다고 가정합니다. 하지만 A 서버에서 파일을 보내기 전에 B 서버에 비슷한 파일이 있는지 확인하고 싶습니다. 저는 os.path.exists()를 사용하였지만 작동하지 않았습니다.

```python
print os.path.exists('ubuntu@serverB.com:b.jpeg')
```

결과는 B 서버에 존재하는 파일이 있어도 false를 리턴합니다. 저는 이것이 구문 오류인지 확실하지 않으며 이 문제를 해결하기 위한 더 좋은 방법이 있을까요? 감사합니다.

---

## 1개의 답변

`os.path` 함수는 같은 컴퓨터의 파일에서만 작동합니다. 이는 경로(paths)에서만 작동하며, `ubuntu@serverB.com:b.jpeg`은 경로가 아닙니다.

이를 이루기 위해서, 원격으로 스크립트를 실행해야 합니다. 다음처럼 하면 작동할 것입니다.

```python
def exists_remote(host, path):
    """SSH로 접근가능한 호스트의 경로에 파일이 있는지 검사합니다."""
    status = subprocess.call(
        ['ssh', host, 'test -f {}'.format(pipes.quote(path))])
    if status == 0:
        return True
    if status == 1:
        return False
    raise Exception('SSH failed')
```

다른 서버에 파일이 있는지 알려면

```python
if exists_remote('ubuntu@serverB.com', 'b.jpeg'):
    # 원격 파일이 있으면...
```

이 방법은 100ms 이상 걸릴수도 있고 *매우* 느릴 수 있습니다.
