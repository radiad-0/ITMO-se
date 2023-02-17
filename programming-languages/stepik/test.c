#include <stdio.h>
#include <inttypes.h>

struct mystruct {
  int64_t x;
  int64_t y;
};

void mystruct_print(const struct mystruct* p) {
  printf("%" PRId64 " %" PRId64 "\n", p->x, p->y);
}

int main() {

  // Выделяем в стеке 16 байт
  // ВСЕ ПОЛЯ СТРУКТУРЫ ХРАНЯТ МУСОР
  struct mystruct var1;

  // Выделяем в стеке ещё 16 байт
  // Поля инициализированы нулями
  struct mystruct var2 = { 0 };

  mystruct_print( &var1) ;
  mystruct_print( &var2) ;
  return 0;
} 
