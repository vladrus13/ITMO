#include "ExpressionParser.h"

bool is_any()
{
	return isAny(parsed[iterator]);
}

void skip_spaces()
{
	while (iterator < parsed.size() && parsed[iterator] == " ")
	{
		iterator++;
	}
}

int parse_token()
{
	int returned;
	skip_spaces();
	if (parsed[iterator] == Int::OPEN_BRACKET[0])
	{
		iterator++;
		skip_spaces();
		returned = parse_implication();
		iterator++;
	}
	if (parsed[iterator] == Int::NOT[0])
	{
		iterator++;
		skip_spaces();
		returned = UnaryOperator(parse_token());
	}
	skip_spaces();
	return returned;
}

int parse_and()
{
	skip_spaces();
	int returned = parse_token();
	skip_spaces();
	while (parsed[iterator] == Int::AND[0])
	{
		iterator++;
		skip_spaces();
		returned = BinaryOperator(parse_token());
		skip_spaces();
	}
	return returned;
}

int parse_or()
{
	skip_spaces();
	int returned = parse_and();
	skip_spaces();
	while (parsed[iterator] == Int::OR[0])
	{
		iterator++;
		skip_spaces();
		returned = BinaryOperator(parse_and());
		skip_spaces();
	}
	return returned;
}

int parse_implication()
{
	skip_spaces();
	int returned = parse_or();
	skip_spaces();
	while (parsed[iterator] == Int::IMPLICATION[0] && parsed[iterator + 1] == Int::IMPLICATION[1])
	{
		iterator += 2;
		skip_spaces();
		returned = BinaryOperator(parse_implication());
		skip_spaces();
	}
	return returned;
}

int parse(string s)
{
	iterator = 0;
	parsed = move(s);
	int returned = parse_implication();
	return returned;
}

void add_pair(string s, int h)
{
	proofed[s] = h;
}

void inc_counter()
{
	counter++;
}

vector getProofed()
{
	return proofed;
}

int getCounter()
{
	return counter;
}

