#include <fstream>
#include <string>
#include <map>
#include <vector>

using namespace std;

bool intSigned(string s) {
    size_t offset = 0;
    if (s[offset] == '-') offset++;
    return s.find_first_not_of("0123456789", offset) == string::npos;
}

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");
    vector<map<string, string>> varsMaps(1);
    vector<string> allVars;
    bool blockIsChange = false;
    int blocksCount = 0, blockIndex;
    string line, val, var;
    size_t eIndex;

    allVars.emplace_back("B0");
    while (getline(fin, line)) {
        if (line[0] == '{') {
            blocksCount++;
            blockIsChange = true;
            varsMaps.emplace_back();
        } else if (line[0] == '}') {
            blocksCount--;
            blockIsChange = true;
            varsMaps.pop_back();
            while (true) {
                if (allVars.back()[0] == 'B') {
                    allVars.pop_back();
                    break;
                }
                allVars.pop_back();
            }
        } else {
            eIndex = line.find('=');
            var = line.substr(0, eIndex);
            val = line.substr(eIndex + 1, line.length() - eIndex + 1);

            blockIndex = -1;
            if (!intSigned(val)) {
                for (int i = allVars.size() - 1; i >= 0; i--) {
                    if (allVars[i] == val) {
                        for (int j = i - 1; j >= 0; j--) {
                            if (allVars[j][0] == 'B') {
                                blockIndex = stoi(allVars[j].substr(1, allVars[j].length()));
                                break;
                            }
                        }
                        break;
                    }
                }

                if (blockIndex == -1) val = "0";
                else val = varsMaps[blockIndex][val];
                fout << val << endl;
            }
            varsMaps.back()[var] = val;
            if (blockIsChange) {
                allVars.push_back("B" + to_string(blocksCount));
                blockIsChange = false;
            }
            allVars.push_back(var);
        }
    }

    return 0;
}