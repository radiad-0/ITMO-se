/* heap-1.c */

#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#define HEAP_BLOCKS 16
#define BLOCK_CAPACITY 1024

enum block_status { BLK_FREE = 0, BLK_ONE, BLK_FIRST, BLK_CONT, BLK_LAST };

struct heap {
	struct block {
		char contents[BLOCK_CAPACITY];
	} blocks[HEAP_BLOCKS];
	enum block_status status[HEAP_BLOCKS];
} global_heap = {0};

struct block_id {
	size_t       value;
	bool         valid;
	struct heap* heap;
};

struct block_id block_id_new(size_t value, struct heap* from) {
	return (struct block_id){.valid = true, .value = value, .heap = from};
}
struct block_id block_id_invalid() {
	return (struct block_id){.valid = false};
}

bool block_id_is_valid(struct block_id bid) {
	return bid.valid && bid.value < HEAP_BLOCKS;
}

/* Find block */
bool block_is_free(struct block_id bid) {
	if (!block_id_is_valid(bid))
		return false;
	return bid.heap->status[bid.value] == BLK_FREE;
}

/* allocate one block */
/* find a free block, reserve it and return its id */
struct block_id one_block_allocate(struct heap* heap) {
	for (size_t i = 0; i < HEAP_BLOCKS; i++)
		if (block_is_free(block_id_new(i, heap))) {
			heap->status[i] = BLK_ONE;
			return block_id_new(i, heap);
		}
	return block_id_invalid();
}

/* Allocate */
//? ? ?
struct block_id block_allocate(struct heap* heap, size_t size) {
	if (size == 1) return one_block_allocate(heap);

	size_t count = 0;
	for (size_t i = 0; i < HEAP_BLOCKS; i++) {
		if (block_is_free(block_id_new(i, heap))) count++;
		else count = 0;

		if (count == size) {
			heap->status[i-size+1] = BLK_FIRST;
			heap->status[i] = BLK_LAST;
			for (size_t j = i-size+2; j < i; j++)
				heap->status[j] = BLK_CONT;
			return block_id_new(i - size + 1, heap);
		}
	}
	return block_id_invalid();
}

/* Free */
/* mark block(s) as 'free' */
/* освобождает блоки начиная с BLK_FIRST */
void block_free(struct block_id b) {
	if (block_is_free(b)) return;
	if (b.heap->status[b.value] == BLK_ONE)
		b.heap->status[b.value] = BLK_FREE;

	if (b.heap->status[b.value] == BLK_FIRST) {
		b.heap->status[b.value] = BLK_FREE;
		for (size_t i = b.value + 1; i < HEAP_BLOCKS; i++) {
			if (b.heap->status[i] == BLK_LAST) {
				b.heap->status[i] = BLK_FREE;
				return;
			}
			if (b.heap->status[i] == BLK_CONT)
				b.heap->status[i] = BLK_FREE;
			else return;
		}
	}
}

/* Printer */
const char* block_repr(struct block_id b) {
	static const char* const repr[] = {[BLK_FREE] = " .",
										[BLK_ONE] = " *",
										[BLK_FIRST] = "[=",
										[BLK_LAST] = "=]",
										[BLK_CONT] = " ="};
	if (b.valid)
		return repr[b.heap->status[b.value]];
	else
		return "INVALID";
}

void block_debug_info(struct block_id b, FILE* f) {
	fprintf(f, "%s", block_repr(b));
}

void block_foreach_printer(struct heap* h, size_t count,
													 void printer(struct block_id, FILE* f), FILE* f) {
	for (size_t c = 0; c < count; c++)
		printer(block_id_new(c, h), f);
}

void heap_debug_info(struct heap* h, FILE* f) {
	block_foreach_printer(h, HEAP_BLOCKS, block_debug_info, f);
	fprintf(f, "\n");
}
/*  -------- */

int main() {
	block_allocate(&global_heap, 5);
	struct block_id b = block_allocate(&global_heap, 5);
	block_allocate(&global_heap, 5);
	block_allocate(&global_heap, 5);
	block_free(b);
	heap_debug_info(&global_heap, stdout);
	return 0;
}
