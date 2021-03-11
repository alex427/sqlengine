package alex.learn.common.stmt.beans;

import java.io.Serializable;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class WhereTree  implements Serializable {
    // 根节点
    private WhereNode root = null;
    private StringBuffer sql = new StringBuffer();

    public WhereTree() { }

    //中序遍历: 无逻辑， 如果需要， 自行加入逻辑
    public  void inOrderTraverseWithoutLogic( WhereNode root){
        if(null != root){
            inOrderTraverseWithoutLogic(root.getLeftChild());
            inOrderTraverseWithoutLogic(root.getRightChild());
            if(null != root.getClauses()){
                for (WhereClause cluase : root.getClauses()){
                    //加入处
                }
            }
        }
    }

    //oracle:中序遍历
    public synchronized StringBuffer inOrderTraverseToracle( WhereNode root){
        if(null != root){
            inOrderTraverseToracle(root.getLeftChild());
            List<WhereClause> clauses = root.getClauses();
            if(null != clauses){
               //System.out.println(clauses.size());
                this.sql.append(GeneralComponets.LEFTBRAC);
                for (WhereClause cluase : clauses){
                    if (null != cluase.getTablename() && !"".equalsIgnoreCase(cluase.getTablename())){
                        //有表名
                        this.sql.append(cluase.getTablename()).append(GeneralComponets.COMMA).append(cluase.getColumnName()).append(cluase.getOperator()).append(cluase.getValue()).append(GeneralComponets.AND);
                    } else {
                        //无表名
                        this.sql.append(cluase.getColumnName()).append(cluase.getOperator()).append(cluase.getValue()).append(GeneralComponets.AND);
                    }
                }
                this.sql.append(GeneralComponets.RIGHTBRAC);
                this.sql.append(GeneralComponets.OR);
            }
            inOrderTraverseToracle(root.getRightChild());
            //System.out.println(sql);
        } else {
            return null;
        }
        return sql;
    }


    public WhereTree(WhereNode root) {
        this.root = root;
    }

    public WhereNode getRoot() {
        return root;
    }

    public void setRoot(WhereNode root) {
        this.root = root;
    }

    public StringBuffer getSql() {
        return sql;
    }

    public void setSql(StringBuffer sql) {
        this.sql = sql;
    }
}
