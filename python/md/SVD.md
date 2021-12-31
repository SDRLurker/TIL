**출처**

[https://stackoverflow.com/questions/21827594/raise-linalgerrorsvd-did-not-converge-linalgerror-svd-did-not-converge-in-m](https://stackoverflow.com/questions/21827594/raise-linalgerrorsvd-did-not-converge-linalgerror-svd-did-not-converge-in-m)

# raise LinAlgError("SVD가 수렴하지 않았습니다.") LinAlgError: SVD가 matplotlib pca 결정에서 수렴하지 않았습니다.

코드

```python
import numpy
from matplotlib.mlab import PCA
file_name = "store1_pca_matrix.txt"
ori_data = numpy.loadtxt(file_name,dtype='float', comments='#', delimiter=None, converters=None, skiprows=0, usecols=None, unpack=False, ndmin=0)
result = PCA(ori_data)
```

저의 코드입니다. 내 입력 행렬에 nan 및 inf가 없지만 아래에 설명된 오류가 발생합니다.

---

## 9 개의 답변 중 1 개의 답변

이는 데이터에 inf 또는 nan 값이 있을 때 발생할 수 있습니다.

nan 값을 제거하려면 다음을 사용하십시오.

```python
ori_data.dropna(inplace=True)
```
