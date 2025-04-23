#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "graph.h"

void addNode(Graph* graph, const char* nodeName) {
    if (graph->nodeCount < MAX_NODES) {
        Node* newNode = (Node*)malloc(sizeof(Node));
        strcpy(newNode->name, nodeName);
        graph->nodes[graph->nodeCount++] = newNode;
        printf("Node added: %s\n", nodeName);
    } else {
        printf("Graph is full, cannot add node.\n");
    }
}

void addEdge(Graph* graph, const char* fromName, const char* toName) {
    Node* fromNode = NULL;
    Node* toNode = NULL;

    // 找到對應的節點
    for (int i = 0; i < graph->nodeCount; i++) {
        if (graph->nodes[i] != NULL) {
            if (strcmp(graph->nodes[i]->name, fromName) == 0) {
                fromNode = graph->nodes[i];
            }
            if (strcmp(graph->nodes[i]->name, toName) == 0) {
                toNode = graph->nodes[i];
            }
        }
    }

    if (fromNode && toNode) {
        printf("Adding edge from %s to %s\n", fromNode->name, toNode->name);
        // 創建邊並添加到邊的列表中
        Edge* newEdge = (Edge*)malloc(sizeof(Edge));
        newEdge->from = fromNode;
        newEdge->to = toNode;
        newEdge->next = graph->edges;
        graph->edges = newEdge;
    } else {
        printf("Error: Nodes not found.\n");
    }
}

void displayGraph(Graph* graph) {
    printf("Graph Nodes and Edges:\n");
    for (int i = 0; i < graph->nodeCount; i++) {
        printf("Node %s\n", graph->nodes[i]->name);
    }
    Edge* edge = graph->edges;
    while (edge != NULL) {
        printf("Edge from %s to %s\n", edge->from->name, edge->to->name);
        edge = edge->next;
    }
}
