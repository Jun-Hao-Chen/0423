#ifndef GRAPH_H
#define GRAPH_H

#define MAX_NODES 100

typedef struct Node {
    char name[50];
} Node;

typedef struct Edge {
    Node* from;
    Node* to;
    struct Edge* next;
} Edge;

typedef struct Graph {
    Node* nodes[MAX_NODES];
    int nodeCount;
    Edge* edges;
} Graph;

void addNode(Graph* graph, const char* nodeName);
void addEdge(Graph* graph, const char* fromName, const char* toName);
void displayGraph(Graph* graph);

#endif
