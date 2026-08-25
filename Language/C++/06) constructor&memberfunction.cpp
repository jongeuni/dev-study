#include <iostream>
#include <string>
using namespace std;

class Test {
public:
    Test():data(17),data2("멤버 함수 정의\n"){} // 생성자 초기화 목록
    int data; 
    string data2;

    void Print(); //멤버 함수 선언
};

void Test::Print() {
    cout << "클래스 외부에 분리된 ";
    cout << data2;
}

int main()
{
    Test test;
    test.Print();
    return 0;
}
