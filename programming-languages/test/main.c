#include "test.h"
#include <stdio.h>

int main() {
    struct heap global_heap = {0};
    block_allocate(&global_heap, 5);
    struct block_id b = block_allocate(&global_heap, 5);
    block_allocate(&global_heap, 5);
    block_allocate(&global_heap, 5);
    block_free(b);
    heap_debug_info(&global_heap, stdout);
    return 0;
}
