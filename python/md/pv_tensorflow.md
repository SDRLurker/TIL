* 출처 : [https://stackoverflow.com/questions/36693740/whats-the-difference-between-tf-placeholder-and-tf-variable](https://stackoverflow.com/questions/36693740/whats-the-difference-between-tf-placeholder-and-tf-variable)

# tf.placeholder와 tf.Variable의 차이점은 무엇입니까?

저는 텐서플로우 뉴비입니다. 저는 `tf.placeholder`와 `tf.Variable`의 차이점이 해깔립니다. 제 관점에서 `tf.placeholder`는 입력 데이터를 위해 사용되고 `tf.Variable`은 데이터의 상태를 저장하는 데 사용됩니다. 이것이 제가 알고 있는 것의 전부입니다.

이 둘의 차이점을 더 자세하게 설명해 주실 분이 있으신가요? 특별히 언제 `tf.placeholder`를 사용하고 또한 언제 `tf.Variable`을 사용하나요?

------

## 14개의 답변 중 1 개의 답변

간단히, 당신의 모델에서 가중치(W, weights)와 편향(B, Bias) 처럼 학습 가능한 변수를 위해 `tf.Variable`를 사용합니다.

```python
weights = tf.Variable(
    tf.truncated_normal([IMAGE_PIXELS, hidden1_units],
                    stddev=1.0 / math.sqrt(float(IMAGE_PIXELS))), name='weights')

biases = tf.Variable(tf.zeros([hidden1_units]), name='biases')
```

`tf.placeholder`는 실제 학습 예시를 공급(feed)하는 데 사용됩니다.

```python
images_placeholder = tf.placeholder(tf.float32, shape=(batch_size, IMAGE_PIXELS))
labels_placeholder = tf.placeholder(tf.int32, shape=(batch_size))
```

다음은 학습되는 동안 학습 예시를 공급(feed)하는 방법입니다.

```python
for step in xrange(FLAGS.max_steps):
    feed_dict = {
       images_placeholder: images_feed,
       labels_placeholder: labels_feed,
     }
    _, loss_value = sess.run([train_op, loss], feed_dict=feed_dict)
```    

당신의 `tf.variables`는 이 학습의 결과로서 (수정되고) 학습될 것입니다.

더 자세한 내용은 [https://www.tensorflow.org/versions/r0.7/tutorials/mnist/tf/index.html](https://www.tensorflow.org/versions/r0.7/tutorials/mnist/tf/index.html)에서 확인하세요. (예시는 웹 페이지에서 가져왔습니다.)
