#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>

using namespace std;

char * swap_return_second(char *a1, char *a2)
{
    char *result; // rax
    char v3; // [rsp+1Ch] [rbp-4h]

    v3 = *a1;
    *a1 = *a2;
    result = a2;
    *a2 = v3;
    return result;
}

void write_cond(char temp[256], char input[264]) {
    cout << "----------------------\n";
    for (int i = 0; i < 256; i++) cout << (int) temp[i] << ' ';
    cout << endl;
    for (int i = 0; i < 264; i++) cout << (int) input[i] << ' ';
    cout << endl;
}

long long sub_1199(const char blood[6], char *a2)
{
    unsigned int v2; // eax
    int blood_len; // [rsp+10h] [rbp-10h]
    int j; // [rsp+14h] [rbp-Ch]
    int i; // [rsp+18h] [rbp-8h]
    int v7; // [rsp+1Ch] [rbp-4h]

    blood_len = strlen(blood);
    v7 = 0;
    for ( i = 0; i <= 255; ++i )
        a2[i] = i;
    for ( j = 0; j <= 255; ++j )
    {
        v2 = (unsigned int)(((unsigned char)a2[j] + v7 + blood[j % blood_len]) >> 31) >> 24;
        v7 = (unsigned char)(v2 + a2[j] + v7 + blood[j % blood_len]) - v2;
        swap_return_second(&a2[j], &a2[v7]);
    }
    return 0LL;
}

long long sub_1272(char temp[256], const char input[264], char output[256])
{
    unsigned int v3; // eax
    char *__attribute__((__org_arrdim(0,256))) print; // [rsp+8h] [rbp-38h]
    size_t input_len; // [rsp+28h] [rbp-18h]
    size_t it; // [rsp+30h] [rbp-10h]
    int v8; // [rsp+38h] [rbp-8h]
    unsigned int index; // [rsp+3Ch] [rbp-4h]

    print = output;
    index = 0;
    v8 = 0;
    it = 0LL;
    input_len = strlen(input);

    while ( it < input_len )
    {
        index = (unsigned char)(((unsigned int)((int)(index + 1) >> 31) >> 24) + index + 1)
                - ((unsigned int)((int)(index + 1) >> 31) >> 24);
        // equals (index + 1) % 255;
        v3 = (unsigned int)((v8 + (unsigned char)temp[index]) >> 31) >> 24;
        v8 = (unsigned char)(v3 + v8 + temp[index]) - v3;
        // v8 += current char
        swap_return_second(&temp[index], &temp[v8]);
        print[it] = temp[(unsigned char)(temp[index] + temp[v8])] ^ input[it];
        ++it;
    }
    return 0LL;
}

long long sub_138F(char blood[6], char input[264], char output[256])
{
    char *outputa; // [rsp+8h] [rbp-118h]
    char temp[256]; // [rsp+20h] [rbp-100h]

    outputa = output;
    write_cond(temp, input);
    sub_1199(blood, temp);
    write_cond(temp, input);
    sub_1272(temp, input, outputa);
    return 0LL;
}

void blood() {
    size_t v3; // rbx
    char output[256]; // [rsp+0h] [rbp-230h]
    char input[264]; // [rsp+100h] [rbp-130h]
    int v9; // [rsp+218h] [rbp-18h]
    unsigned int i; // [rsp+21Ch] [rbp-14h]

    puts("Give me the key: ");
    fgets(input, 255, stdin);
    for (int j = 0; j < 24; j++) {
        input[j] = 1;
    }
    v9 = strlen(input) - 1;
    input[v9] = 0;
    if ( strlen(input) != 24 )
    {
        puts("Wrong length");
        exit(-1);
    }
    string a1a = "blood";
    vector <int> v7{231, 235, 127, 104, 19, 254, 214, 64,
                    227, 209, 0, 20, 96, 240, 100, 154, 4, 184, 89, 114, 121, 56, 237, 24};
    sub_138F("blood", input, output);
    for ( i = 0; ; ++i )
    {
        v3 = i;
        if ( v3 >= strlen(input) )
            break;
        if ( v7[i] != output[i] )
        {
            cout << "Wrong on position: " << i << " Excepted: " << (int) v7[i] << " Actual: " << (int) output[i] << endl;
        }
    }
    puts("Correct!");
}

bool inc23(unsigned char a1, unsigned char a2) {
    return (unsigned char)(((unsigned int)((a1 + 23) >> 31) >> 24) + a1 + 23) - ((unsigned int)((a1 + 23) >> 31) >> 24) == a2;
}

bool dec17(unsigned char a1, unsigned char a2) {
    return (unsigned char)(((unsigned int)((a1 - 17) >> 31) >> 24) + a1 - 17) - ((unsigned int)((a1 - 17) >> 31) >> 24) == a2;
}

bool complete_combination(unsigned char a1, unsigned char a2) {
    return ((char) (a2 ^ a1)) == -1;
}

bool equals(char a1, char a2) {
    return a1 == a2;
}

void recurse() {
    vector <int> code{115, 143, 81, 122, 99, 153, 106, 127, 207, 119, 118, 100, 32, 155, 95, 144, 48, 138,
             78, 131, 32, 148, 34, 118, 139, 104, 75, 116, 108};
    vector <string> function_codes{"equals", "complete_combination",
                                   "dec17", "inc23", "dec17",
                                   "complete_combination", "dec17",
                                   "inc23", "complete_combination",
                                   "equals", "inc23", "equals", "dec17",
                                   "complete_combination", "equals", "inc23",
                                   "equals", "complete_combination", "dec17",
                                   "inc23", "dec17", "complete_combination",
                                   "dec17", "inc23", "complete_combination",
                                   "equals", "inc23", "equals", "dec17"};
    for (int i = 0; i < code.size(); i++) {
        bool is_end = false;
        for (char j = 0; j < 127; j++) {
            bool done =
                    (function_codes[i] == "equals" && equals(j, code[i])) ||
                    (function_codes[i] == "complete_combination" && complete_combination(j, code[i])) ||
                    (function_codes[i] == "dec17" && dec17(j, code[i])) ||
                    (function_codes[i] == "inc23" && inc23(j, code[i]));
            if (done) {
                cout << (char) j;
                is_end = true;
                break;
            }
        }
        if (!is_end) cout << "W";
    }
}

int main() {
    // blood();
    recurse();
    return 0;
}

// 123456789123456789123456
// FLAG{aaaaaaaaaaaaaaaaaa}
