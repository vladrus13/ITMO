all: test

randomized_queue_test: randomized_queue.hpp randomized_queue_test.cpp
	$(CXX) -g -Wall -Wextra -Werror -std=c++17 $^ -o randomized_queue_test

randomized_deque_test: randomized_deque.hpp randomized_deque_test.cpp
	$(CXX) -g -Wall -Wextra -Werror -std=c++17 $^ -o randomized_deque_test

subset: subset.cpp subset.hpp
	$(CXX) -g -Wall -Wextra -Werror -std=c++17 $^ -o subset

subset_test: subset_test.cpp subset
	$(CXX) -g -Wall -Wextra -Werror -std=c++17 subset_test.cpp -o subset_test

test: randomized_queue_test randomized_deque_test subset_test
	./randomized_queue_test 
	./randomized_deque_test
	./subset_test	

clean:
	rm randomized_queue_test
	rm randomized_deque_test
	rm subset_test
	rm subset

.PHONY: all test randomized_queue_test randomized_deque_test subset_test subset clean
