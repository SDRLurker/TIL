출처 : [https://towardsdatascience.com/3-ways-to-load-csv-files-into-colab-7c14fcbdcb92](https://towardsdatascience.com/3-ways-to-load-csv-files-into-colab-7c14fcbdcb92)

# 시작하기: Colab에서 CSV 파일을 불러오는 3가지 방법

데이터 사이언스는 데이터 없이는 아무것도 아닙니다. 예 그것은 분명합니다. 분명하지 않은 것은 데이터를 탐색할 수 있는 형식으로 데이터를 가져오기 위한 단계입니다. (쉼표로 구분된 값을 줄여서) CSV 형식의 데이터 셋을 가질 수 있지만 다음에 수행할 작업은 알 수 없습니다. 이 글은 Colab에서 CSV 파일을 불러옴으로써 데이터 사이언스를 시작하는 데 도움이 될 것입니다.

**Colab**(Colaboratory를 줄여서)은 Python으로 코딩하도록 구글로부터 온 무료 플랫폼입니다. Colab은 본질적으로 Jupyter Notebook의 구글 Suite 버전입니다. Jupyter 위의 Colab의 몇가지 이점은 문서로 공유하기와 더 쉬운 패키지 설치가 있습니다. 아직, CSV 파일 처럼 파일을 불러올 때 몇가지 추가 코딩이 필요합니다. 저는 Colab에서 CSV 파일을 불러오기 위한 3가지 방법을 보여드리고 이를 Pandas 데이터프레임으로 추가할 것입니다.

(참고 : 공통 데이터 세트를 전달하는 Python 패키지가 있습니다. 이 기사에서 이러한 데이터 세트를 불러오는 것에 대해서는 논의하지 않을 것 입니다.)

시작하기 위해 당신의 구글 계정으로 로그인하여 Google Drive로 갑니다. 왼쪽에 **새로 만들기** 버튼을 클릭하고 만약 설치되었다면 **Colaboratory** 를 선택합니다. (**더 많은 앱 연결**을 클릭하지 않으면 Colaboratory을 검색하여 설치하십시오.) 여기에서 아래와 같이 Pandas를 import 합니다. (Colab에 이미 설치되어 있음).

```python
import pandas as pd
```

## 1) Github로 부터(파일 < 25MB)
CSV 파일을 업로드하는 가장 쉬운 방법은 GitHub 저장소를 사용하는 것입니다. 당신의 저장소에서 데이터 세트를 클릭하고 **View Raw**를 클릭합니다. raw 데이터 세트에 대한 링크를 복사하여 아래 표시된대로 Colab에 url이라는 문자열 변수로 저장합니다 (보다 깔끔한 방법이지만 필요하지는 않습니다). 마지막 단계는 URL을 Pandas read_csv로 불러와 데이터 프레임을 얻는 것입니다.

```python
url = '복사한_raw_GitHub_링크'

df1 = pd.read_csv(url)

# 데이터 세트는 Pandas Dataframe에 이제 저장됩니다.
```

## 2) local drive로 부터
당신의 local drive로부터 업로드하여, 다음 코드로 시작합니다.

```python
from google.colab import files
uploaded = files.upload()
```

선택할 파일이 프롬프트로 나올 것입니다. "**파일 선택하기**"를 클릭하여 파일을 선택하여 업로드 합니다. 파일이 100% 업로드 되기를 기다립니다. Colab이 파일을 업로드를 했을 때 파일의 이름이 보여야 합니다.

마지막으로, 데이터 프레임으로 그것을 import하기 위해 다음 코드를 타이핑합니다. (파일 이름은 업로드한 파일의 이름과 똑같은지 확인해야 합니다).

```python
import io

df2 = pd.read_csv(io.BytesIO(uploaded['Filename.csv']))

# 데이터 세트는 Pandas Dataframe에 이제 저장됩니다.
```

## 3) PyDrive를 통한 Google Drive로 부터
이는 3가지 방법 중에 가장 복잡합니다. 워크 플로 제어를 위해 CSV 파일을 Google 드라이브에 업로드 하는 것을 보여 드리겠습니다. 먼저 다음 코드를 입력하십시오.

```python
# Colaboratory에서 csv 파일을 읽기 위한 코드:

!pip install -U -q PyDrive
from pydrive.auth import GoogleAuth
from pydrive.drive import GoogleDrive
from google.colab import auth
from oauth2client.client import GoogleCredentials

# PyDrive 클라이언트를 생성하고 인증하기
auth.authenticate_user()
gauth = GoogleAuth()
gauth.credentials = GoogleCredentials.get_application_default()
drive = GoogleDrive(gauth)
```

프롬프트가 표시되면 링크를 클릭하여 Google이 드라이브에 액세스 할 수 있도록 인증을 받습니다. 상단에 '**Google Cloud SDK가 Google 계정에 액세스 하려고 합니다**'라는 화면이 표시됩니다. 권한을 허용한 후 제공된 확인 코드를 복사하여 Colab의 상자에 붙여 넣습니다.

확인이 완료되면 Google 드라이브의 CSV 파일로 이동하여 마우스 오른쪽 버튼으로 클릭하고 "**공유 가능 링크 가져 오기**"를 선택하십시오. 링크가 클립 보드에 복사됩니다. 이 링크를 Colab의 문자열 변수에 붙여 넣습니다.

```python
link = 'https://drive.google.com/open?id=1DPZZQ43w8brRhbEMolgLqOWKbZbE-IQu' # 공유 가능 링크
```

원하는 것은 *등호 뒤의* ID 부분입니다. 해당 부분을 얻으려면 다음 코드를 입력하십시오.

```python
fluff, id = link.split('=')

print (id) # '=' 뒤에 모든 부분이 있는지 확인
```

마지막으로, 데이터프레임으로 파일을 얻기 위해 다음 코드를 타이핑 합니다.

```python
downloaded = drive.CreateFile({'id':id}) 
downloaded.GetContentFile('Filename.csv')  
df3 = pd.read_csv('Filename.csv')

# 데이터 세트는 Pandas Dataframe에 이제 저장됩니다.
```

## 마지막 의견
Colab에서 CSV 파일을 업로드하기 위한 3가지 접근이 있습니다. 각각 파일 크기와 워크 플로 구성 방법에 따라 장점이 있습니다. 데이터가 Pandas 데이터프레임처럼 더 좋은 포멧이라면, 작업할 준비가 된 것입니다. 

## 보너스 방법 - 내 드라이브

![](https://miro.medium.com/max/1400/1*99WL0KKd7x3d6U-0IBQ8WA.png)

당신의 지원에 매우 감사합니다. 이 글은 50,000 View와 25K의 읽기에 도달하는 영광이 있었고, 저는 Colab에서 CSV 파일을 얻기 위한 추가적인 방벙을 제공합니다. 이는 더 간단하고 분명합니다. Google Drive("**My Drive**")에서, 당신이 선택한 위치에서 **data**라 불리는 폴더를 만듭니다. 이는 당신의 데이터를 업로드할 곳이 될 것입니다.

Colab 노트북에서, 다음을 타이핑합니다.

```python
from google.colab import drive
drive.mount('/content/drive')
```


세 번째 방법과 마찬가지로 명령을 사용하면 Google 인증 단계로 이동합니다. **Google 드라이브 파일 스트림에서 Google 계정에 액세스하시오** 라는 화면이 표시됩니다. 권한을 허용한 후 제공된 확인 코드를 복사하여 Colab의 상자에 붙여 넣습니다.

노트북에서 노트북 왼쪽 상단에 >를 클릭하고 파일을 클릭하십시오. 앞에서 만든 **data** 폴더를 찾고 데이터를 찾으십시오. 데이터를 마우스 오른쪽 버튼으로 클릭하고 **경로 복사**를 선택하십시오. 이 복사된 경로를 변수에 저장하면 바로 사용할 수 있습니다.

```python
path = "copied path"
df_bonus = pd.read_csv(path)

# 데이터 세트는 Pandas Dataframe에 이제 저장됩니다.
```

이 방법의 장점은 세 번째 방법과 관련된 추가 단계없이 자체 Google 드라이브에서 생성한 별도의 데이터 세트 폴더에서 데이터 세트에 액세스 할 수 있다는 것입니다.
