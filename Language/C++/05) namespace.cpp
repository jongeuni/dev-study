
#include <string>
#include <iostream>
using namespace std;

namespace TEST {
    int data = 10;
    int add(int a, int b) {
        return a + b;
    }
}

// 중첩 namespace
namespace OVERLAPPING {
    int n_data = 100;
    namespace DEV {
        int n_data = 200;
        namespace WIN {
            int n_data = 300;
        }
    }
    int add(int a, int b) { //namespace 다중정의
        return a * b;
    }
}

using namespace TEST;
using namespace OVERLAPPING;
int main() {
    int sum = TEST::add(2, 3);
    TEST::data = 30;
    cout << TEST::data<<endl; // 30

    cout << OVERLAPPING::DEV::WIN::n_data; //300


    //add(3, 4); 오류가 난다 
}
