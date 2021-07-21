출처 : [https://stackoverflow.com/questions/30506489/python-multiprocessing-leading-to-many-zombie-processes/32442923](https://stackoverflow.com/questions/30506489/python-multiprocessing-leading-to-many-zombie-processes/32442923)

# 많은 좀비 프로세스를 이끄는 파이썬 멀티프로세싱

저는 worker의 pool을 사용하여 파이썬의 멀티프로세싱 라이브러리를 구현하고 있습니다. 저는 다음 코드를 구현하였습니다.

```python
import main1
t1 = time.time()
p = Pool(cores) 
result = p.map(main1, client_list[client])
if result == []:
    return []
p.close()
p.join()
print "Time taken in performing request:: ", time.time()-t1
return shorted(result)
```

하지만, 프로세스를 실행한 후에 저의 응용프로그램에 백그라운드 프로세스가 많이 나왔습니다. 저의 응용프로그램에 ps aux를 실행한 후에 스냅샷입니다.

![](https://i.stack.imgur.com/naoU4.png)

Stackoverflow에서 [멀티프로세싱 모듈에 의해 생성된 좀비 프로세스를 죽이는 방법](https://stackoverflow.com/questions/19322129/how-to-kill-zombie-processes-created-by-multiprocessing-module) 같은 비슷한 질문을 많이 읽었습니다. 이미 구현한 .join()을 사용해야 하며 여기에서 [Python Multiprocessing Kill Processes](https://stackoverflow.com/questions/18477320/python-multiprocessing-kill-processes?lq=1)에서 이러한 모든 프로세스를 종료하는 방법을 배웠습니다. 그러나 제 코드에 무엇이 잘못 될 수 있는지 알고 싶습니다. main1 함수에서 모든 코드를 공유할 수는 없지만 main 코드의 오류로 인해 좀비 프로세스가 발생할 수 있는 경우를 피하기 위해 try catch 블록에 전체 코드 블록을 넣었습니다.

```python
def main1((param1, param2, param3)):
    try:
       resout.append(some_data) # 오류가 없는 경우 resout
    except:
        print traceback.format_exc()
        resout = []  # 오류가 발생한 경우 비어 있는 resout 리턴
    return resout
```

저는 병렬 프로그래밍 및 디버깅 문제라는 개념에 대해 아직 매우 신입이고 매우 까다롭습니다. 어떤 도움이든 매우 감사하겠습니다.

---

### 1개의 답변

보통 가장 보편적인 문제는 pool이 생성되었지만 닫지 않는 것입니다.

제가 알고있는 최고의 방법은 try/finally 구문을 사용하여 pool이 닫히도록 보장하는 것 입니다.

```python
try:
    pool = Pool(ncores)
    pool.map(yourfunction, arguments)
finally:
    pool.close()
    pool.join()
```

`multiprocessing`과 싸우고 싶지 않다면, 저는 제 인생(잠재적으로 당신의 인생도)을 더 쉽게 멀티프로세싱을 wrapping 한 `parmap`이라 불리는 간단한 패키지를 작성하였습니다.

`pip install parmap`

```python
import parmap
parmap.map(yourfunction, arguments)
```

여기서부터는 parmap 사용법 입니다.

* 간단한 병렬처리 예시:

```python
import parmap
y1 = [myfunction(x, argument1, argument2) for x in mylist]
y2 = parmap.map(myfunction, mylist, argument1, argument2)
y1 == y2
```

* 튜플의 리스트를 순회하기

```python
# 당신이 원하는 것:
z = [myfunction(x, y, argument1, argument2) for (x,y) in mylist]
z = parmap.starmap(myfunction, mylist, argument1, argument2)


# 당신이 원하는 것:
listx = [1, 2, 3, 4, 5, 6]
listy = [2, 3, 4, 5, 6, 7]
param = 3.14
param2 = 42
listz = []
for (x, y) in zip(listx, listy):
    listz.append(myfunction(x, y, param1, param2))
# 병렬로 실행:
listz = parmap.starmap(myfunction, zip(listx, listy), param1, param2)
```
