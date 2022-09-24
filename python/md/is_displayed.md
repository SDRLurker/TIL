**출처**

* [https://www.geeksforgeeks.org/is_displayed-element-method-selenium-python/](https://www.geeksforgeeks.org/is_displayed-element-method-selenium-python/)

# is_displayed() element 메소드 - 셀레니움 파이썬

셀레니움의 파이썬 모듈은 파이썬의 자동화된 테스트를 수행하기 위해 만들어졌습니다. 셀레니움 파이썬은 기능/승인 테스트를 작성하는 간단한 API를 제공합니다.셀레니움 파이썬을 사용하여 웹페이지를 열려면 - 다음 [get 메소드를 사용하여 링크 탐색하기](https://www.geeksforgeeks.org/navigating-links-using-get-method-selenium-python/)을 확인하세요. 해당 주소로 갈 수 있다는 것만으로는 그다지 유용하지 않습니다. 우리가 정말 원하는 것은 페이지 또는 더 구체적으로 페이지의 HTML element와 상호작용하는 것 입니다. 셀레니움에서 사용하는 element를 찾는 다양한 전략이 있습니다. 다음 [찾는 전략](https://www.geeksforgeeks.org/locator-strategies-selenium-python/)을 확인하세요. 이 글은 셀리니움에서 is_displayed 메소드를 사용하는 방법에 대해 설명합니다.is_replayed 메소드는 element가 사용자에게 보이는지 아닌지 확인하는 데 사용됩니다. 이는 불리언 값 **True** 또는 **False**가 리턴됩니다.

**문법**

```python
element.is_displayed()
```

**예시**

```HTML
<a href="https://www.geeksforgeeks.org/" id="link" />Text Here</a>
```

element를 찾기 위해 찾는 전략의 하나를 사용할 필요가 있습니다. 예를 들어,

```python
element = driver.find_element_by_id("link")
element = driver.find_element_by_xpath("//a[@id='link']")
```

여러개의 element를 찾기 위해 우리는 다음을 사용할 수 있습니다.

```python
elements = driver.find_elements_by_id("link")
```

이제 이 element가 표시되는지 확인할 수 있습니다.

```python
text_length = element.is_displayed()
```

## 셀레니움 파이썬에서 is_displayed element 메소드를 사용하는 방법?

셀레니움 파이썬에서 이 메소드를 표현하기 위해 https://www.geeksforgeeks.org/ 를 사용합시다. geeksforgeeks에서 navigation bar에 course tab을 볼 수 있는지 확인합니다.

**프로그램**

```python
# import webdriver
from selenium import webdriver
 
# create webdriver object
driver = webdriver.Firefox()
 
# get geeksforgeeks.org
driver.get("https://www.geeksforgeeks.org/")
 
# get element
element = driver.find_element_by_link_text("Courses")
 
# print value
print(element.is_displayed())
```

** 출력**

```shell
(venv) [naveen@naveen articles]$ python run.py
True
(venv) [naveen@naveen articles]$
```
