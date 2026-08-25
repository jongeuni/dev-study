#include <iostream>

int main()
{
	std::cout << "Hello World!! \n"; //std에 속한 cout은 출력을 담당하는 객체. <<는 cout객체 안에 있는 (출력) 연산자 함수. cout을 쓰기 위해 iostream 인클루드 해야함.
	// cin >> a; 입력 받음 a 변수에.

	int a(1), d;
	//객체 형식을 갖는 변수를 인스턴스라고 함.
	std::cout << a << '\n' << "안녕 \n";
	std::cin >> d;
	std::cout << a+d << '\n' << "d의 값 " << d;
	return 0;
}