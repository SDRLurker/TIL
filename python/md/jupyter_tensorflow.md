출처 : [http://stackoverflow.com/questions/37061089/trouble-with-tensorflow-in-jupyter-notebook](http://stackoverflow.com/questions/37061089/trouble-with-tensorflow-in-jupyter-notebook)

# Jupyter Notebook에서 TensorFlow 사용 문제

저는 Ubuntu 14.04에서 Anaconda를 통해 Jupyter Notebook을 설치했고 바로 TensorFlow를 설치 하였습니다. 저는 TensorFlow가 Notebook에서나 간단히 스트립트를 작성하는 것과 상관 없이 작동하게 하고 싶습니다. 이를 이루기 위한 저의 시도에서, 저는 TensorFlow를 두 번 설치를 했는데 첫 번째는 Anaconda를 사용하였고 그 다음은 pip를 사용하였습니다. Anaconda 설치가 잘 되었지만 "source activate tensorflow"를 python에서 먼저 실행할 필요가 있었습니다. 만약 python을 (터미널에서) 일반적인 방법으로 시작하면 pip 설치는 작 작동하였고 tensorflow도 잘 불러 왔습니다.

저의 질문은 어떻게 Jupyter notebook에서 이를 잘 실행할 수 있을까요? 입니다.

이는 저를 더 일반적인 질문으로 이끌었습니다. 저의 Jupyter/Anaconda에서 python kernel은 시스템에서 널리 사용되는 python kernel(또는 환경? 여기서 용어는 확실하지 않습니다)과 분리되어 있는 거 같습니다. 이 커널들을 동시에 제어한다면 좋겠습니다. 이게 된다면 제가 새로운 python 라이브러리를 설치할 때 python을 실행하는 모든 다양한 방법으로 접근할 수 있기 때문입니다.

---

## 4 개의 답변 중 1 개의 답변만 추려냄.

Ipython 또는 Jupyter(Ipython) Notebook에서 TensorFlow를 사용하려면 tensorflow가 활성화된 환경에서 (tensorflow를 설치한 뒤에) Jupyter와 Ipython을 설치해야 합니다.

tensorflow 환경에서 Ipython과 Jupyter를 설치하기 전에 터미널에서 다음 명령어를 실행합니다.

```shell
username$ source activate tensorflow

(tensorflow)username$ which ipython
(tensorflow)username$ /Users/username/anaconda/bin/ipython

(tensorflow)username$ which jupyter
(tensorflow)username$ /Users/username/anaconda/bin/jupyter

(tensorflow)username$ which python
(tensorflow)username$ /User/username//anaconda/envs/tensorflow/bin/python
```

이는 당신이 터미널에서 python을 열었을 때, tensorflow가 설치된 "환경"에서 설치된 것들을 이렇게 사용중이라고 당신에게 말해줍니다. 그러므로 당신은 실제 import tensorflow가 성공적으로 가능합니다. 하지만, ipython 또는 jupyter notebook을 실행한다면, 이들은 tensorflow에 장착된 "환경"에서 설치되지 않았기 때문에 tensorflow 모듈이 없는 일반적인 환경을 사용하기 때문에 import 오류가 나오게 됩니다.

당신은 envs/tensorflow/bin 디렉터리에서 파일 목록을 봄으로서 이를 검증할 수 있습니다.

```shell
(tensorflow) username$ ls /User/username/anaconda/envs/tensorflow/bin/
```

당신은 "ipython"이나 "jupyter" 목록이 없음을 알 수 있습니다.

Ipython 또는 Jupyter Notebook에서 tensorflow를 사용하려면, 간단하게 tensorflow 환경에서 Notebook을 설치하면 됩니다.

```python
(tensorflow) username$ conda install ipython
(tensorflow) username$ pip install jupyter #(use pip3 for python3)
```

이들을 설치한 후, "jupyter"와 "ipython"은 envs/tensorflow/bin 디렉터리에서 보일 것입니다.

알아두세요: jupyter notebook에서 tensorflow 모듈을 import하기 전에, notebook을 먼저 닫으세요. 그리고 "source deactivate tensorflow"를 먼저 하고 "같은 페이지"에서 이들의 실행을 보장하기 위해 다시 그것을 ("source activate tensorflow") 활성화 하세요. 그리고 notebook을 다시 열어 import tensorflow 해보세요. 성공적으로 import 될 것입니다. (적어도 저는 작동했습니다.)



