#include <iostream>

using namespace std;

int main()
{
	int x;
	int pre_read_size = 2000;
	int m = 10000 - pre_read_size;
	string s;
	int ii = pre_read_size;
	vector numbers;
	while (ii--)
	{
		cin >> x;
		cout << x << endl;
		cin >> s;
		numbers.push_back(s == "YES");
	}
	int real_cyclic = 0;
	for (int length_cycle = 1; length_cycle <= 1000; length_cycle++)
	{
		bool is_cycle = true;
		for (int i = length_cycle; i < pre_read_size; i++)
		{
			if (number[i] != numbers[i - length_cycle])
			{
				is_cycle = false;
				break;
			}
		}
		if (is_cycle)
		{
			real_cyclic = length_cycle;
			break;
		}
	}
	int number = pre_read_size;
	while (m--)
	{
		cin >> x;
		cout << (numbers[number % real_cyclic] ^ x) << endl;
		cin >> s;
		number++;
	}
	return 0;
}

