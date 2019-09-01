# [지식in] 허수 i의 i제곱은 몇일까?
[https://www.youtube.com/watch?v=Tk3PIpcppV0](https://www.youtube.com/watch?v=Tk3PIpcppV0)

$$e^{i\pi} = -1$$

```python
>>> math.exp(1)**(complex(0,1)*math.pi)
(-1+1.2246467991473532e-16j)
```

$$i^{i} = \frac{1}{e^{\frac{\pi}{2}}}$$

```python
>>> complex(0,1)**complex(0,1)
(0.20787957635076193+0j)
```
