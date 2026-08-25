

### numpy 모듈이란?

`import numpy`

계산 작업을 편하게 해줄 도구이다. numpy는 numpy list를 제공해주는데 여러값을 효율적으로 다룰수있도록 되어있기 때문에 매우 유용하다.

```python
array1 = numpy.array([1, 2, 3, 5, 6, 9, 12 ,134, 15])
array1.shape # (11, )
array2 = numpy.array([[1, 2, 3], [5, 6, 9], [12, 134, 15]])
array2.shape # (3, 4)
```

shape는 자료형을 볼 수 있게 해준다. array2.shape는 (3, 4)가 나오는데, **2차원 배열**이다. 세로 길이가 3, 가로 길이가 4인 것이다. 출력될 때도

array([[1, 2, 3],

​			[5, 6, 9],

​			[12, 134, 15]]) 이렇게 띄워져서 나온다. 

**`len`() vs `size`**

len(array)는 리스트의 길이를 알려 준다. 리스트가 중첩 리스트일 경우 가장 **바깥 리스트**의 길이를 알려 준다. array.size의 경우 array에 몇 개의 요소가 들어있는지를 알려준다.



#### numpy method

**`array()`**

파라미터로 파이썬 리스트를 넘겨주면 numpy array가 리턴된다.

**`full()`**

```python
array = numpy.full(6, 7) # [7 7 7 7 7 7]
```

모든 값이 동일하게 numpy array가 생성된다.

**`zeros()` `ones()`**

```python
array1 = numpy.zeros(6, dtype=int) # [0 0 0 0 0 0]
array2 = numpy.ones(6, dtype=int) # [1 1 1 1 1 1]
```

모든 값이 0/1인 numpy array를 생성하는 방법이다. 물론 full 메소드를 써도 된다.

**`random()`**

랜덤한 값들로 배열을 생성시키고 싶을 땐 numpy의 random 모듈의 random 함수를 사용하면 된다. numpy 모듈 안 -> random 모듈 안 -> random 함수.

```python
array1 = numpy.random.random(6)
```

**`arange()`**

```python
array1 = numpy.arange(6) # [0 1 2 3 4 5]
array2 = numpy.arange(2, 7) # [2 3 4 5 6] 
array3 = numpy.arange(3, 17, 3) # [3 6 9 12 15]
```

**arange(n)** : 0부터 n-1까지 값들이 담긴 numpy array가 리턴된다.

**arrange(n, m)** :  n부터 m-1까지 값들이 담긴 numpy array가 리턴된다.

**arrange(n, m, s)** :  n부터 m-1까지 값들 중 간격이 s인 값들이 담긴 numpy array가 리턴된다.

**`max()` `min()`** **`mean()`**

```python
array0 = np.array([1, 3, 4, 5, 2, 9])
array0.max() # 9
array0.mean() # 평균값을 구해준다. 6
```

최댓값과 최솟값을 구해준다.

**`median`**

numpy array의 메소드가 아니라 numpy의 메소드이다. 정렬했을 때 중앙값을 구해준다.

```python
array0 = np.array([8, 12, 9, 15, 16])
np.median(array0) # 12.0
```

**`std()` `var()`**

std는 표준편차를 구해주고 var은 분산을 구해준다. 



#### 인덱싱

numpyarray[-1]은 python 리스트와 같이 배열의 끝을 가르킨다. -2는 끝에서 두 번째이다. 

숫자로 인덱싱하는 방법(`arr[1]`) 외에도 list로 인덱싱을 할 수도 있다. `array([1, 3, 4])`를 하면 list 1번 인덱스 값, 3번 인덱스 값, 4번 인덱스 값들이 합쳐져서 만들어진다. 

```python
array1[1, 9, 4, 3, 5, 1, 2, 41, 7]
array2 = np.array([2, 1, 3])
array1[array2] # array([4, 9, 3])
```



#### 슬라이싱

```python
array = [1, 2, 3, 4, 5, 6, 7, 8]
array[1:4] # [2,3,4]
array[1:8:2] # [2,4,6,8] 1번 인덱스부터 7번인덱스까지 2씩 띄워서
```



#### numpy 기본연산

```python
array1 = numpy.arange(10)
array2 = numpy.arange(10, 20)
array1 * 2 #모든 값에 곱하기 (나누기, 더하기, 제곱) 2가 돼서 출력된다. 저장은 따로 해줘야 한다.
array1 + array2 # 같은 인덱스에 있는 값이 더해진(나누기, 곱하기) 결과가 나온다.
```

**boolean 연산도 가능하다.**

```python
booleans = np.array([True, True, False, True, Treu, False])
numpy.where(booleans) # array([0, 1, 3, 4]) True인 인덱스만 알려준다.

array = numpy.arange(10)
array > 4 # [False, False, True, True ...] boolean 연산도 가능하다.
filter = numpy.where(array>4) # array>4가 ture인 인덱스들만 저장한다.
# 그럼 이 filter된 값을 인덱싱하는 데 쓸 수 있다.
array[filter] # [5, 6, 7, 8, 9] 조건을 충족하는 것만 필터링 가능하다.
```

`numpy.where(조건)` 하면 조건에 True인 인덱스들만 알려준다.