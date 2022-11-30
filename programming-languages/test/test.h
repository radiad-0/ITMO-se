//
// Created by aidar on 16.11.22.
//

#ifndef LAB3_TEST_H
#define LAB3_TEST_H

#include <stdbool.h>
#include <stdio.h>

#define HEAP_BLOCKS 16
#define BLOCK_CAPACITY 1024

enum block_status { BLK_FREE = 0, BLK_ONE, BLK_FIRST, BLK_CONT, BLK_LAST };

struct heap {
    struct block {
        char contents[BLOCK_CAPACITY];
    } blocks[HEAP_BLOCKS];
    enum block_status status[HEAP_BLOCKS];
};

struct block_id {
    size_t       value;
    bool         valid;
    struct heap* heap;
};

struct block_id block_allocate(struct heap* heap, size_t size);
void block_free(struct block_id b);
void heap_debug_info(struct heap* h, FILE* f);

#endif //LAB3_TEST_H
