#include <iostream>
#include <vector>

using namespace std;

// a * b - c
int findInVector(const vector<int>& v, int val) {
    for (int i = 0; i < v.size(); i++)
        if (v[i] == val) return i;
    return -1;
}

int main() {
    vector<int> days;
    int a, b, c, d, index;
    long long k;
    cin >> a >> b >> c >> d >> k;

    for (int i = 0; i < k; i++) {
        a *= b;
        if (a <= c) {
            a = 0;
            break;
        }
        a -= c;
        if (a > d) a = d;
        index = findInVector(days, a);
        if (index != -1) {
            k -= index;
            index += k % (i - index);
            if (index > i) index -= i;
            a = days[index];
            break;
        }
        days.push_back(a);
    }

    cout << a;
    return 0;
}