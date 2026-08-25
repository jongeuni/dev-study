#include <iostream>
//#include <String>
int main()
{
	int age;
	//auto 컴파일러가 자동으로 형식을 규정하는 자료형. auto a=10;
	std::string name, job; 
	//문자열 입력은 std::string을 이용.
	//decltype(name) 변수명 name과 같은 타입의 변수.
	std::cout <<"나이를 입력하세요 : ";
	std::cin >> age;
	std::cout <<"직업을 입력하세요 : ";
	std::cin >> job;
	std::cout <<"이름을 입력하세요 : ";
	std::cin >> name;
	std::cout << "\n당신의 이름은 " << name << "이고, 나이는 " << age << "살이며, 직업은 " << job << "입니다.";
}