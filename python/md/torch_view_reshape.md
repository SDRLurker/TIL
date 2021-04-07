참고주소 : [https://stackoverflow.com/questions/49643225/whats-the-difference-between-reshape-and-view-in-pytorch](https://stackoverflow.com/questions/49643225/whats-the-difference-between-reshape-and-view-in-pytorch)

# pytorch에서 reshape와 view 간에 차이점은 무엇입니까?

numpy에서 배열을 하기 위해 `ndarray.reshape()`를 사용합니다.
저는 pytorch에서 사람들이 `torch.view(...)`를 같은 목적으로 사용한다는 것을 알았지만 동시에 도 존재한다는 점입니다.

그리하여 저는 이들의 차이점이 무엇인지와 이들 중 하나를 언제 사용해야 되는지 궁금합니다.

---

## 3개 답변 중 1개만 추려냄

`torch.view`와 `torch.reshape`가 모두 텐서의 모양을 변경하는 데 사용되지만 여기에 두 가지 차이점이 있습니다.

1. 이름에서 알 수 있듯이 `torch.view`는 원래 텐서의 *뷰*를 생성 할뿐입니다. 새 텐서는 *항상* 데이터를 원래 텐서와 공유합니다. 즉, 원래 텐서를 변경하면 재구성 된 텐서가 변경되고 그 반대도 마찬가지입니다.

```python
>>> z = torch.zeros(3, 2)
>>> x = z.view(2, 3)
>>> z.fill_(1)
>>> x
tensor([[1., 1., 1.],
        [1., 1., 1.]])
```

2. 새 텐서가 항상 원본과 데이터를 공유하도록 하기 위해`torch.view`는 두 텐서의 모양에 몇 가지 연속성 제약 조건을 적용합니다 [docs](https://pytorch.org/docs/master/tensors.html#torch.Tensor.view). 종종 이것은 문제가 되지 않지만 때때로 `torch.view`는 두 텐서의 모양이 호환되는 경우에도 오류를 발생시킵니다. 여기에 유명한 반례가 있습니다.

```python
>>> z = torch.zeros(3, 2)
>>> y = z.t()
>>> y.size()
torch.Size([2, 3])
>>> y.view(6)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
RuntimeError: invalid argument 2: view size is not compatible with input tensor's
size and stride (at least one dimension spans across two contiguous subspaces).
Call .contiguous() before .view().
```

3. `torch.reshape`는 연속성 제약을 부과하지 않지만 데이터 공유를 보장 하지도 않습니다. 새로운 텐서는 원래 텐서의 view 일 수도 있고, 완전히 새로운 텐서일 수도 있습니다.

```python
>>> z = torch.zeros(3, 2)
>>> y = z.reshape(6)
>>> x = z.t().reshape(6)
>>> z.fill_(1)
tensor([[1., 1.],
        [1., 1.],
        [1., 1.]])
>>> y
tensor([1., 1., 1., 1., 1., 1.])
>>> x
tensor([0., 0., 0., 0., 0., 0.])
```

너무 길면 안 읽어도 됩니다 (TL;DR :)

텐서의 모양을 변경하려면 `torch.reshape`를 사용하십시오. 메모리 사용량도 염려하고 두 텐서가 동일한 데이터를 공유하도록 하려면 `torch.view`를 사용하세요.
