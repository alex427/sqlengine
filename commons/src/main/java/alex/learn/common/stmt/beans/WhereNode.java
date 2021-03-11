package alex.learn.common.stmt.beans;

import java.io.Serializable;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class WhereNode  implements Serializable {

    List<WhereClause> clauses = null;
    WhereNode leftChild;
    WhereNode rightChild;

    public WhereNode() {
    }

    public List<WhereClause> getClauses() {
        return clauses;
    }

    public void setClauses(List<WhereClause> clauses) {
        this.clauses = clauses;
    }

    public WhereNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(WhereNode leftChild) {
        this.leftChild = leftChild;
    }

    public WhereNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(WhereNode rightChild) {
        this.rightChild = rightChild;
    }
}
