#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
    char* argument_list[] = {
        "mvn gluonfx:build -e && mvn gluonfx:package -e",
        "mvn gluonfx:install -e && mvn gluonfx:nativerun -e"
    };

    int n = sizeof(argument_list) / sizeof(argument_list[0]);

    if (argc < 2) {
        fprintf(stderr, "Use: %s -b|-i|-a\n", argv[0]);
        exit(1);
    }
    else {
        setenv("GRAALVM_HOME", "/home/renan/Downloads/graalvm17", 1);

        if (strcmp("-b", argv[1]) == 0){
            system(argument_list[0]); // build
        }
        else if (strcmp("-i", argv[1]) == 0) {
            system(argument_list[1]); // install
        }
        else if (strcmp("-a", argv[1]) == 0) {
            for (int i = 0; i < n; i++) {
                system(argument_list[i]); // build and install
            }
        }
        else {
            fprintf(stderr, "Comando invalido: %s\nOpções validas: -b (build), -i (install), -a (all)\n", argv[1]);
            exit(1);
        }
    }
}