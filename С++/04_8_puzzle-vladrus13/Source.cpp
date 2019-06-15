#include "board.cpp"
#include "solver.cpp"
#include <time.h>

void solve_random() {
	unsigned long long timer = time(nullptr);
	srand(time(NULL));
	int size = rand() % 10;
	board x = board(size);
	std::cout << "Testing random board. Size is " << size << '\n' << x << '\n';
	solver y = solver(x);
	std::cout << "Moves: " << y.moves() << '\n';
	for (auto it = y.begin(); it != y.end(); it++) {
		std::cout << *it << '\n' << '\n';
	}
	std::cout << "Time: " << timer - time(nullptr) << '\n';
	system("pause");
}

vector <vector <vector <unsigned char> > > tests = {
{
	{1, 2},
	{3, 0} },
{
	{0, 3},
	{2, 1} },
{
	{0, 1, 2},
	{3, 4, 5},
	{6, 7, 8} },
{
	{1, 2, 3},
	{4, 5, 6},
	{7, 8, 0} },
{
	{1, 2},
	{0, 3} }
};

void solve_real() {
	for (int i = 0; i < tests.size(); i++) {
		unsigned long long timer = time(nullptr);
		srand(time(NULL));
		board test(tests[i]);
		std::cout << "Testing board number " << i << ".\n" << test << '\n';
		solver y = solver(test);
		std::cout << "Moves: " << y.moves() << '\n';
		for (auto it = y.begin(); it != y.end(); it++) {
			std::cout << *it << '\n' << '\n';
		}
		std::cout << "Time: " << timer - time(nullptr) << "\n\n\n\n\n";
		// system("pause");
	}
}

int main() {
	solve_real();
	solve_random();
}