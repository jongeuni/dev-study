#include	<iostream>

// 함수 다중 정의
int Sum(int n1, int n2) {
	return n1 + n2;
}
/*double Sum(int n1, int n2) { //오류
	return n1 * n2;
}*/
double Sum(double n1, int n2) {
	return n1 + n2;
}
int Sum(int n1, int n2, int n3) {
	return n1 + n2 + n3;
}

// 함수 템플릿
template	<typename	T>
T	Sub(T	a, T	b)
{
	T j = a;
	return	j - b;
}

//인라인 함수
inline int avg(int a, int b) {
	return a * b;
}

int	main()
{
	// 함수 템플릿 사용
	std::cout << Sub(3, 4) << std::endl;
	std::cout << Sub(3.3, 4.4) << std::endl;
	// 함수 다중정의 사용
	std::cout << Sum(2.5, 1)<<std::endl;
	std::cout << Sum(1, 2, 3)<<std::endl;
	// 인라인 함수
	std::cout << avg(2, 4);
	return	0;
}