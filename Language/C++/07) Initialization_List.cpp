#include	<iostream>
using	namespace	std;

class A
{
public:
    int x, y;
    A(int a, int b) { x = a; y = b; };
};

class B
{
public:
    A a;
    B(int x, int y) : a(x, y) {}
    void PrintValue()
    {
        std::cout << a.x << a.y;
    }
};

class	CTest
{
    int	m_nData;
public:
    CTest() : m_nData(0) //	디폴트	생성자
    {
        cout << "CTest::CTest()	" << endl;	//	생성자	호출	확인
    }
    CTest(int	nParam) :m_nData(nParam) //	생성자
    {
        cout << "CTest::CTest(int	nParam)	" << nParam<<endl;	//	생성자	호출	확인
    }
    ~CTest() //	소멸자
    {
        //	멤버변수의	값을	함께	출력해본다.
        cout << "CTest::~CTest()	" << m_nData << endl;	//	소멸자	호출	확인
    }
};

int main()
{    
    int a = 10;
    CTest ct(a);
    cout << "Begin	" << endl;
    CTest* pData[10]; // 객체 생성 아님
    cout << "Before	b" << endl;
    for (int i = 0; i < 10; i++) 
        pData[i] = new	CTest(i); // 동적 객체 생성 
    for (int i = 0; i < 10; i++)
        delete	pData[i];	// 배열에 동적으로 생성된 객체들을 삭제, delete [] pData; 이거 x
    cout << "End	" << endl;

    // 범위 기반 for문 이용
    cout << "Begin	" << endl;
    CTest* ppData[10];
    cout << "Before	b" << endl;
    for (auto& n : ppData)
        n = new	CTest(10);	// 동적 객체 생성
    for (auto& n : ppData)
        delete	n; 
    cout << "End	" << endl;


    int test = 0;
    CTest a(test);
    cout << "안녕";
    test = 1;
    return	0;
}