#include <iostream>
#include <string>
#include <vector>
#include <stack>

using namespace std;

bool isAnimal(char c) {return c > 95;}

bool trapAndAnimalMatch(char c1, char c2) {
    return (c1 - c2 == 32 || c1 - c2 == -32);
}

int main() {
    stack<char> mainStack;
    stack<int> indexesStack;
    int trapsCount = 0, animalsCount = 0;
    vector<int> answer;
    string s;
    cin >> s;
    answer.resize(s.length() / 2);

    for (char c : s) {
        if (isAnimal(c)) animalsCount++;
        else trapsCount++;

        if (!mainStack.empty() && trapAndAnimalMatch(mainStack.top(), c)) {
            if (isAnimal(c)) answer[indexesStack.top()-1] = animalsCount;
            else answer[trapsCount-1] = indexesStack.top();
            indexesStack.pop();
            mainStack.pop();
        } else {
            mainStack.push(c);
            if (isAnimal(c)) indexesStack.push(animalsCount);
            else indexesStack.push(trapsCount);
        }
    }

    if (mainStack.empty()) {
        cout << "Possible" << endl;
        for (int i : answer) cout << i << ' ';
    } else cout << "Impossible";

    return 0;
}