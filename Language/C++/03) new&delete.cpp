#include <iostream>

int main()
{
	int* pData = new int; //인스턴스 동적 생성

	int* pNewData = new int[10]; //배열 동적 생성
	pNewData[0] = 1;

	int* pNewData_t = new int(10);

	*pData = 5;
	std::cout << *pData << std::endl; //5
	std::cout << *pNewData_t << std::endl; //10
	std::cout << pNewData[0] << std::endl; //1

	delete pData;
	delete[] pNewData;

	return 0;
}