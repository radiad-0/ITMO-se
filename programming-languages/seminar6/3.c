/* 3.c */

#include <inttypes.h>
#include <malloc.h>
#include <stdio.h>

struct vector {
	int64_t* array;
	size_t capacity;
	size_t count;
};

int64_t get_elem(struct vector v, size_t i) {
	return v.array[i];
}

void set_elem(struct vector v, size_t i, int64_t value) {
	if (i <= v.count) v.array[i] = value;
}

size_t get_count(struct vector v) {
	return v.count;
}

size_t get_capacity(struct vector v) {
	return v.capacity;
}

void add_elem(struct vector v, int64_t value) {
	if (v.capacity < 0) {
		v.array = malloc(sizeof(int64_t) * 10);
		v.count = 0;
	}
	if (v.capacity == v.count) {
		v.array = realloc(v.array, sizeof(int64_t) * v.capacity * 2);
		v.capacity = v.capacity * 2;
	}
	v.array[v.count++] = value;
	printf("%" PRId64 " ", v.array[v.count-1]);
	printf("%" PRId64 " ", get_count(v));
	printf("%" PRId64, get_capacity(v));
	printf("\n");
}

void add_array(struct vector v, int64_t* arr, size_t size) {
	for (size_t i = 0; i < size; i++) 
		add_elem(v, arr[i]);
}

int main() {
	struct vector v = {malloc(sizeof(int64_t) * 5), 5, 0};
	// заполните массив квадратами чисел от 0 до 100
	// если не хватает места, расширяем в два раза
	for (size_t i = 0; i <= 100; i++) {
		add_elem(v, i * i);
	}

	for (size_t i = 0; i < 100; i++) {
		printf("%" PRId64 " ", get_elem(v, i));
	}
	return 0;
}
 
