### pandas란?

표 형식의 데이터를 다루는 데 능하다.

DataFrame

2차원 형태(표 형식)의 데이터를 다루기 위한 자료형이다. 그럼 numpy의 2차원 배열과 똑같지 않느냐? pandas는 2차원 배열에서 부가적인 기능이 더해진 것이다. 행과 열의 이름이 숫자가 아니라 문자일 수도 있고, 다양한 자료형을 담을 수도 있다.



![image-20210111213222660](C:\Users\user\AppData\Roaming\Typora\typora-user-images\image-20210111213222660.png)

![image-20210111213319029](C:\Users\user\AppData\Roaming\Typora\typora-user-images\image-20210111213319029.png)

컬럼이랑 로우에 이름을 지어주지 않았기 때문에 숫자가 뜨는 것이다.

```python
mydf = pd.DataFrame(two_d_list, columns=['name', 'english_score','math_score'], index=['a','b','c','d'])
mydf # 하면 컬럼과 로우가 설정된다.
mydf.colume # 컬럼 출력됨
mydf.index # 인덱스 출력됨
mydf.dtypes # 각 컬럼의 자료형 object는 판다스의 문자열을 뜻한다.
```

DataFrame 만드는 여러 방법

1. numpy array

   ```python
   two_dimensional_list = [['dongwook', 50, 86], ['sineui', 89, 31], ['ikjoong', 68, 91], ['yoonsoo', 88, 75]]
   df1 = pd.DataFrame(two_dimensional_list)
   ```

   

2. Series

   ```python
   list_of_series = [
       pd.Series(['dongwook', 50, 86]), 
       pd.Series(['sineui', 89, 31]), 
       pd.Series(['ikjoong', 68, 91]), 
       pd.Series(['yoonsoo', 88, 75])
   ]
   df3 = pd.DataFrame(list_of_series)
   ```

   Series는 판다스의 1차원 배열이다.

3. dictionary

   ```python
   names = ['dongwook', 'sineui', 'ikjoong', 'yoonsoo']
   english_scores = [50, 89, 68, 88]
   math_scores = [86, 31, 91, 75]
   
   dict1 = pd.DataFrame{
       'name': names, 
       'english_score': english_scores, 
       'math_score': math_scores
   }
   #df1 = pd.DataFrame(dict1)
   ```


DataFrame.dtypes을 사용하면 DataFrame의 데이터 타입에 대해 알 수 있다.

| dtype      | 설명        |
| ---------- | ----------- |
| int64      | 정수        |
| float64    | 소수        |
| object     | 텍스트      |
| bool       | 불린        |
| datetime64 | 날짜와 시간 |
| category   | 카테고리    |



### CSV

Comma-separated values (값들이 쉰표로 나누어져 있다.)

pandas로 csv파일 읽는 법 `read_csv()`

```python
i = pandas.read_csv('파일 경로')
```

자동으로 csv 파일 첫 번째 줄이 헤더로 들어간다.  근데 만약 헤더 줄이 없다면 엉뚱한 값이 헤더 값으로 들어가 버린다. 그럴 경우에는

```python
i = pandas.read_csv('파일 경로', header=None)
```

이렇게 헤더가 없다는 표시를 해준다. 그러면 헤더가 0, 1, 2...이렇게 바뀐다.

```python
i = pandas.read_csv('파일 경로', index_col=0)
```

csv 파일에서 특정 컬럼을 row 이름으로 저장할 수 있다. 위처럼 하면 0번 컬림이 row 이름으로 설정된다.

**원하는 값만 불러오기 **(인덱싱) `loc`

```python
i.loc['row명',['컬럼명','컬럼명']]
i.loc[:, '컬럼명'] # 한 줄을 다 가져온다. 
```

데이터 타입을 확인해 보면 pandas.series가 나온다. series는 pandas의 1차원 배열이다.

**여러 컬럼/로우 불러오기**

```python
i.loc[['로우1','로우2']]
i[['컬럼1','컬럼2']]
```

> 왜 컬럼은 loc 안 쓸까?



**두 csv 파일 합쳐서 하나의 DataFrame 만들기**

```python
s = pd.read_csv('파일 경로')
h = pd.read_csv('파일 경로')

s['컬럼명 2']
h['컬럼명 3']
com = {
    '컬럼명1' : s['사용할 컬럼 1']
    '컬럼명2' : S['사용할 컬럼 2']
    '컬렴명3' : h['사용할 컬럼 3']
}
d = pd.DataFrame(com) # 컬렴1, 컬럼2, 컬럼3이 합쳐진 Df가 나온다.
```

**row로 슬라이싱 하기**

```python
i.loc['row명':'row명']
```

**컬럼으로 슬라이싱 하기**

```python
i.loc[:,'컬럼 a':'컬럼 d'] # 모든 로우로부터 컬럼a부터 컬럼d까지 가져온다.
```

**둘 다 슬라이싱 하기**

```python
i.loc['row a':'row b', '컬럼 a':'컬럼 d']
```

**인덱싱**

```python
nnmnn||o=n=o||
i.loc[[True, False, True, True, False, True]] #0, 2, 3, 4 인덱스의(True) 값만 나온다. 컬럼도 마찬가지이다.
```

필터링

```python
i['컬럼명']>5 # 를 인덱싱에 쓸 수 있다.
i.loc[i['컬럼명']>5] # 컬럼이 5보다 큰 조건만 필터링 돼서 나온다.
c = iphone['디스플레이']>5) & (iphone['Face ID']=='Yes') # 두가지조건을 bool값으로
i.loc[c] # 인덱싱
```

`ilovc`

인덱스로 값을 가져올 수 있다.

```python
i.iloc[2,4] # 2,4번에 있는 값
i.iloc[[1,3],[1,5]]
i.iloc[3:,1:4] # row 3번 인덱스부터 clunm 1~4번인덱스까지

```

DataFrame 인덱싱을 하는 방법과 종류



데이터 프레임에서 값을 받아오는것뿐만아니라 데이터 프레임에 값을 추가할 수도 있다.

```python
iphone.loc['row','clounm']='바꿀값' 
# 한 행을 한 번에 바꿀 경우
i.loc['row'] = ['바꿀값','바꿀값','바꿀값','바꿀값'] # 바꿀값 리스트를 넘겨준다. 컬럼도 똑같다.
# 모든 값을 똑같이 바꿀 경우 값 하나만 쓰면 된다.
i.loc['row또는컬럼']='바꿀값'
# 연속된 로우를 바꿀 경우
i.loc['row':'row']="바꿀값" # 슬라이싱을 하면 된다.
# 조건으로 바꾸자
i.loc[i['row명']>5] ='바꿀값' #row명이 5가 넘으면 바뀐다.
i.iloc[[1,2],[1,4]] = "바꿀값" #위치직접지정
```

새로운 로우나 새로운 컬럼 추가하기

