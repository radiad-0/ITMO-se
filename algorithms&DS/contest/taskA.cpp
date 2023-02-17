#include <iostream>
#include <vector>

using namespace std;

int main() {
    vector<int> a;
    int n, start = 1, end = -1, len = 0, count = 1;
    cin >> n;
    a.resize(n);

    for (int i = 0; i < n; i++) {
        cin >> a[i];
        len++;
        if (i && a[i] == a[i - 1]) count++;
        else count = 1;

        if (count >= 3) {
            if (len > end - start + 1) {
                start = i - len + 2;
                end = i;
            }
            len = 2;
        }
    }

    if (len > end - start + 1) {
        start = n - len + 1;
        end = n;
    }

    cout << start << ' ' << end;

    return 0;
}