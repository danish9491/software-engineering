#include <stdio.h>
#include <stdlib.h>

struct Node
{
    int data;
    struct Node *next;
};

// Function to insert a node at the beginning of the linked list
void insertNode(struct Node **head, int data)
{
    struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));
    newNode->data = data;
    newNode->next = *head;
    *head = newNode;
}

// Function to delete the last node of the linked list
void deleteLastNode(struct Node **head)
{
    if (*head == NULL)
    {
        return;
    }
    if ((*head)->next == NULL)
    {
        free(*head);
        *head = NULL;
        return;
    }

    struct Node *secondLast = *head;
    while (secondLast->next->next != NULL)
    {
        secondLast = secondLast->next;
    }
    free(secondLast->next);
    secondLast->next = NULL;
}

// Function to display the linked list
void displayList(struct Node *head)
{
    struct Node *current = head;
    while (current != NULL)
    {
        printf("%d -> ", current->data);
        current = current->next;
    }
    printf("NULL\n");
}

int main()
{
    struct Node *head = NULL;

    // Insert nodes at the beginning
    insertNode(&head, 3);
    insertNode(&head, 2);
    insertNode(&head, 1);

    printf("Initial List: ");
    displayList(head);

    // Insert element at the beginning
    int newElement = 4;
    insertNode(&head, newElement);
    printf("After Insertion: ");
    displayList(head);

    // Delete element at the end
    deleteLastNode(&head);
    printf("After Deletion: ");
    displayList(head);

    return 0;
}