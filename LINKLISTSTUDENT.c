#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Student {
    int roll_no;
    char name[50];
    int age;
    struct Student* next;
};

struct Student* create_student(int roll_no, char* name, int age) {
    struct Student* new_student = (struct Student*)malloc(sizeof(struct Student));
    new_student->roll_no = roll_no;
    strcpy(new_student->name, name);
    new_student->age = age;
    new_student->next = NULL;
    return new_student;
}

void insert_student(struct Student** head, int roll_no, char* name, int age) {
    struct Student* new_student = create_student(roll_no, name, age);
    struct Student* current = *head;

    if (current == NULL) {
        *head = new_student;
    } else {
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = new_student;
    }
}

void print_students(struct Student* head) {
    struct Student* current = head;

    while (current != NULL) {
        printf("Roll No: %d, Name: %s, Age: %d\n", current->roll_no, current->name, current->age);
        current = current->next;
    }
}

void delete_student(struct Student** head, int roll_no) {
    struct Student* current = *head;
    struct Student* previous = NULL;

    if (current != NULL && current->roll_no == roll_no) {
        *head = current->next;
        free(current);
        return;
    }

    while (current != NULL && current->roll_no != roll_no) {
        previous = current;
        current = current->next;
    }

    if (current == NULL) {
        printf("Roll No %d not found.\n", roll_no);
        return;
    }

    previous->next = current->next;
    free(current);
}

int main() {
    struct Student* head = NULL;

    // Create students
    insert_student(&head, 1, "Atulya", 19);
    insert_student(&head, 2, "Danish", 19);
    insert_student(&head, 3, "Yash", 19);

    // Traverse and print students
    printf("Student Details:\n");
    print_students(head);
    printf("\n");

    // Insert a new student
    insert_student(&head, 4, "Vikas", 19);

    // Print students after insertion
    printf("Student Details after Insertion:\n");
    print_students(head);
    printf("\n");

    // Delete a student
    delete_student(&head, 2);

    // Print students after deletion
    printf("Student Details after Deletion:\n");
    print_students(head);
    printf("\n");

    return 0;
}