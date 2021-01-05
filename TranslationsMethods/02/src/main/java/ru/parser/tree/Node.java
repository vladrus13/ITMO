package ru.parser.tree;


import guru.nidi.graphviz.model.MutableNode;

/**
 * Abstract class for all tree for {@link ru.parser.Parser}
 */
public abstract class Node {
    /**
     * Return struct in string by {@link StringBuilder}
     * @return string-view
     */
    public abstract StringBuilder toStringBuilder();

    /**
     * Return struct in string
     * @return string-view
     */
    public String toString() {
        return toStringBuilder().toString();
    }

    /**
     * Get node for graph
     * @return {@link MutableNode}
     */
    public abstract MutableNode getNode();
}
