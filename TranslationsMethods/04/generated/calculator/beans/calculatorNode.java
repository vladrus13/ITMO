package calculator.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all tree for all
 */
public abstract class calculatorNode {
    public final List<calculatorNode> children = new ArrayList<>();

    public Integer val = null;
}