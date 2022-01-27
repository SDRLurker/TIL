출처 : [https://stackoverflow.com/questions/51046454/how-can-we-use-selenium-webdriver-in-colab-research-google-com](https://stackoverflow.com/questions/51046454/how-can-we-use-selenium-webdriver-in-colab-research-google-com)

# colab.research.google.com에서 Selenium Webdriver를 사용하는 방법?

저는 빠른 처리를 위해 colab.research.google.com 에서 Selenium Webdriver를 사용하고 싶습니다. 저는 `!pip install selenium`을 사용하여 Selenium을 설치할 수 있었지만 크롬의 웹 드라이버는 webdriverChrome.exe의 경로를 요구합니다. 그것을 사용하려면 어떻게 합니까?

추신 - colab.research.google.com은 딥러닝과 관련된 빠른 연산 문제를 위해 GPU를 제공하는 온라인 플랫폼입니다. webdriver.Chrome(path)와 같은 솔루션을 삼가해 주세요.

---

## 6개의 답변 중 1개의 답변

크롬 웹 드라이버를 설치하고 Google Colab에서 충돌하지 않도록 몇 가지 옵션을 조정하여 수행할 수 있습니다.

```python
!pip install selenium
!apt-get update # apt install을 정확히 실행하기 위해 ubuntu 업데이트
!apt install chromium-chromedriver
!cp /usr/lib/chromium-browser/chromedriver /usr/bin
import sys
sys.path.insert(0,'/usr/lib/chromium-browser/chromedriver')
from selenium import webdriver
chrome_options = webdriver.ChromeOptions()
chrome_options.add_argument('--headless')
chrome_options.add_argument('--no-sandbox')
chrome_options.add_argument('--disable-dev-shm-usage')
wd = webdriver.Chrome('chromedriver',chrome_options=chrome_options)
wd.get("https://www.webite-url.com")
```
