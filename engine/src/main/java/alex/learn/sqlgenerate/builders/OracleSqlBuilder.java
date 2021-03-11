package alex.learn.sqlgenerate.builders;

import alex.learn.sqlgenerate.interfaces.Buildable;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 * 行为算法实现类：Oracle
 */
public class OracleSqlBuilder implements Buildable<MetadataHolder> {

    private List<Column> tmplist = new ArrayList<>();

    @Override
    public String generateSql(MetadataHolder holder) throws IlegalColumException, IlegalFunctionException, DBinfoException {
        InternalBuilder builder = new InternalBuilder(holder);

        StringBuffer select = builder.select();
        StringBuffer func = builder.func();
        StringBuffer from = builder.from();
        StringBuffer join = builder.join();
        StringBuffer group = builder.group();
        StringBuffer orderby = builder.orderby();
        StringBuffer where = builder.where();

        //适配无聚合函数的情况
        if (null == func || "".equalsIgnoreCase(func.toString())) {
            select.deleteCharAt(select.length() - 2);
        }
        StringBuffer inner = select.append(func).append(from).append(join).append(where).append(group).append(orderby);

        StringBuffer outter = builder.limit(inner);
        String sql = outter.toString();
        sql = sql.replaceAll("  ", " ");
        sql = sql.replaceAll(" \\)", "\\)");
        sql = sql.replaceAll("\\)\\)", "\\) \\)");
        System.out.println(sql);
        return sql;
    }

    @Override
    public void persistSql() {

    }

    //内置Builder进行构建
    public class InternalBuilder {
        private MetadataHolder holder;

        public InternalBuilder(MetadataHolder holder) {
            this.holder = holder;
        }

        public StringBuffer select() throws IlegalColumException {
            StringBuffer sql = new StringBuffer();
            sql.append(GeneralComponets.SELECT);
            List<Column> list = this.holder.getSelectdims();
            if (list.size() == 0) {
                throw new IlegalColumException("There are no columns in SELECT clause.");
            } else if (list.contains(null)) {
                throw new IlegalColumException("Null column is found in SELECT clause.");
            } else {
                for (Column column : list) {
                    String funcname = column.getFuncname();
                    if (null != funcname && !"".equalsIgnoreCase(funcname)) {
                        sql.append(funcname).append(GeneralComponets.LEFTBRAC).append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName());
                        //加入参数
                        FuncParam[] arr = column.getFuncParams();
                        StringBuffer fp = new StringBuffer();
                        if (arr != null && arr.length > 0) {
                            for (int i = 0; i < arr.length; i++) {
                                fp.append(GeneralComponets.COLON);
                                String pvalue = arr[i].getPvalue();
                                String ptype = arr[i].getPtype();
                                if ("string".equalsIgnoreCase(ptype)) {
                                    pvalue = pvalue;
                                    fp.append(GeneralComponets.SREF).append(pvalue).append(GeneralComponets.SREF);
                                } else if ("int".equalsIgnoreCase(ptype)) {
                                    int a = Integer.parseInt(pvalue);
                                    fp.append(a);
                                } else if ("double".equalsIgnoreCase(ptype)) {
                                    double d = Double.parseDouble(ptype);
                                    fp.append(d);
                                } else if ("long".equalsIgnoreCase(ptype)) {
                                    long g = Long.parseLong(ptype);
                                    fp.append(g);
                                }
                            }
                        }
                        sql.append(fp);
                        sql.append(GeneralComponets.RIGHTBRAC).append(GeneralComponets.AS).append(column.getTalias()).append(GeneralComponets.COLON);
                    } else {
                        sql.append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName()).append(GeneralComponets.COLON);
                    }
                }
            }
            return sql;
        }


        public StringBuffer func() throws IlegalFunctionException {
            StringBuffer sql = new StringBuffer();
            List<Func> list = this.holder.getFunclist();
            if (null == list || list.size() == 0) {
                System.out.println("msg : funclist is empty.");
                return sql;
            } else {
                for (Func func : list) {
                    //String func_ = FunctionContainer.getFunc(func.getFuncname().toUpperCase());
                    String func_ = FunctionContainer.getFunc(func.getFuncname());
                    if (null == func_) {
                        //支持空函数，该需求比较奇葩，此处有风险
                        sql.append(func.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(func.getMeasurename()).append(GeneralComponets.COLON);
                        tmplist.add(new Column(func.getTableName(), func.getMeasurename()));
                    } else {
                        sql.append(func_).append(GeneralComponets.LEFTBRAC).append(func.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(func.getMeasurename());
                        //加入参数
                        FuncParam[] arr = func.getFuncParams();
                        StringBuffer fp = new StringBuffer();
                        if (arr != null && arr.length > 0) {
                            for (int i = 0; i < arr.length; i++) {
                                fp.append(GeneralComponets.COLON);
                                String pvalue = arr[i].getPvalue();
                                String ptype = arr[i].getPtype();
                                if ("string".equalsIgnoreCase(ptype)) {
                                    pvalue = pvalue;
                                    fp.append(GeneralComponets.SREF).append(pvalue).append(GeneralComponets.SREF);
                                } else if ("int".equalsIgnoreCase(ptype)) {
                                    int a = Integer.parseInt(pvalue);
                                    fp.append(a);
                                } else if ("double".equalsIgnoreCase(ptype)) {
                                    double d = Double.parseDouble(ptype);
                                    fp.append(d);
                                } else if ("long".equalsIgnoreCase(ptype)) {
                                    long g = Long.parseLong(ptype);
                                    fp.append(g);
                                }
                            }
                        }
                        sql.append(fp);
                        sql.append(GeneralComponets.RIGHTBRAC).append(GeneralComponets.AS).append(func.getTalias()).append(GeneralComponets.COLON);
                    }
                }
            }
            sql.deleteCharAt(sql.length() - 2);
            return sql;
        }


        public StringBuffer from() throws DBinfoException {
            StringBuffer sql = new StringBuffer();
            if (null == this.holder.getMaintable()) {
                throw new DBinfoException("DB OR table is not found.");
            } else {
                sql.append(GeneralComponets.FROM).append(this.holder.getMaintable()).append(GeneralComponets.NBSPACE).append(this.holder.getMaintable().toLowerCase()).append(GeneralComponets.NBSPACE);
            }
            return sql;
        }


        public StringBuffer join() {
            StringBuffer sql = new StringBuffer();
            //合法性判断
            if (null == this.holder.getJoinlist() || 0 == this.holder.getJoinlist().size()) {
                return sql;
            } else {
                for (Join join : this.holder.getJoinlist()) {
                    if (!join.verify()) {
                        return sql;
                    } else {
                        sql.append(join.getJointype()).append(join.getJoinTable()).append(GeneralComponets.NBSPACE).append(join.getJoinTable().toLowerCase()).append(" ON ").append(GeneralComponets.LEFTBRAC);
                        for (String col : join.getJoinColumns()) {
                            sql.append(join.getMainTable()).append(GeneralComponets.COMMA).append(col).append(" = ").append(join.getJoinTable().toLowerCase()).append(GeneralComponets.COMMA).append(col).append(GeneralComponets.NBSPACE).append(GeneralComponets.AND);
                        }
                        sql.delete(sql.length() - 4, sql.length());
                        sql.append(GeneralComponets.RIGHTBRAC);
                    }
                }
                return sql;
            }
        }


        public StringBuffer where() {
            StringBuffer where_ = new StringBuffer();
            WhereTree tree = this.holder.getWheretree();
            //三种情况：1.手写where;2.筛选器;3.无where---》三种情况互斥
            //手写where条件的优先级高于筛选器，按完全覆盖处理
            if (null != this.holder.getHandwriteWhere() && !"".equalsIgnoreCase(this.holder.getHandwriteWhere())) {
                String where = this.holder.getHandwriteWhere();
                where = where.trim();
                if (where.startsWith("WHERE") || where.startsWith("WHERE")) {
                    where_.append(where);
                } else {
                    where_.insert(0, " WHERE ");
                    where_.append(where);
                }
            } else if (null != this.holder.getWheretree()) {
                if (null != tree.getRoot()) {
                    StringBuffer where = tree.inOrderTraverseToracle(tree.getRoot());
                    String sql_ = where.toString().replaceAll("AND \\)", ")");
                    where_.append(sql_);
                    where_.insert(0, " WHERE ");
                    where_.delete((where_.length() - 4), (where_.length())); //清除末尾的OR
                }
            } else {
                //支持无where条件
                //throw new IlegalColumException(" whereclause is missed. ");
                where_.append(" WHERE 1=1 ");
            }
            return where_;
        }


        public StringBuffer group() throws IlegalColumException {
            StringBuffer sql = new StringBuffer();
            List<Column> list = this.holder.getGroupdims();
            if (null == list || list.size() < 1) {
                return sql;
            } else if (list.contains(null)) {
                throw new IlegalColumException("Null column is found in GROUP clause.");
            } else {
                sql.append(GeneralComponets.GROUPBY);
                for (Column column : list) {
                    String funcname = column.getFuncname();
                    if (null != funcname && !"".equalsIgnoreCase(funcname)) {
                        sql.append(funcname).append(GeneralComponets.LEFTBRAC).append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName());
                        //加入参数
                        FuncParam[] arr = column.getFuncParams();
                        StringBuffer fp = new StringBuffer();
                        if (arr != null && arr.length > 0) {
                            for (int i = 0; i < arr.length; i++) {
                                fp.append(GeneralComponets.COLON);
                                String pvalue = arr[i].getPvalue();
                                String ptype = arr[i].getPtype();
                                if ("string".equalsIgnoreCase(ptype)) {
                                    pvalue = pvalue;
                                    fp.append(GeneralComponets.SREF).append(pvalue).append(GeneralComponets.SREF);
                                } else if ("int".equalsIgnoreCase(ptype)) {
                                    int a = Integer.parseInt(pvalue);
                                    fp.append(a);
                                } else if ("double".equalsIgnoreCase(ptype)) {
                                    double d = Double.parseDouble(ptype);
                                    fp.append(d);
                                } else if ("long".equalsIgnoreCase(ptype)) {
                                    long g = Long.parseLong(ptype);
                                    fp.append(g);
                                }
                            }
                        }
                        sql.append(fp);
                        sql.append(GeneralComponets.RIGHTBRAC).append(GeneralComponets.COLON);
                    } else {
                        sql.append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName()).append(GeneralComponets.COLON);
                    }
                }
            }
            sql.deleteCharAt(sql.length() - 2);
            return sql;
        }


        public StringBuffer group2() {
            StringBuffer sql = new StringBuffer();
            List<Column> list = this.holder.getGroupdims();
            if (list.size() == 0) {
                return sql;
            } else {
                sql.append(GeneralComponets.GROUPBY);
                for (Column column : list) {
                    sql.append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName()).append(GeneralComponets.COLON);
                }
                for (Column column : tmplist) {
                    sql.append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName()).append(GeneralComponets.COLON);
                }
                sql.deleteCharAt(sql.length() - 2);
            }
            return sql;
        }


        public StringBuffer orderby() {
            StringBuffer sql = new StringBuffer();
            List<OrderByy> list = this.holder.getOrders();
            if (null == list || list.size() == 0) {
                return sql;
            } else {
                sql.append(GeneralComponets.ORDERBY);
                for (OrderByy orderByy : list) {
                    if (orderByy.getFlag() == 1) {
                        Column column = orderByy.getColumn();
                        String funcname = column.getFuncname();
                        if (null != funcname && !"".equalsIgnoreCase(funcname)) {
                            sql.append(funcname).append(GeneralComponets.LEFTBRAC).append(column.getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(column.getColumnName());
                            FuncParam[] arr = column.getFuncParams();
                            StringBuffer fp = new StringBuffer();
                            if (arr != null && arr.length > 0) {
                                for (int i = 0; i < arr.length; i++) {
                                    fp.append(GeneralComponets.COLON);
                                    String pvalue = arr[i].getPvalue();
                                    String ptype = arr[i].getPtype();
                                    if ("string".equalsIgnoreCase(ptype)) {
                                        pvalue = pvalue;
                                        fp.append(GeneralComponets.SREF).append(pvalue).append(GeneralComponets.SREF);
                                    } else if ("int".equalsIgnoreCase(ptype)) {
                                        int a = Integer.parseInt(pvalue);
                                        fp.append(a);
                                    } else if ("double".equalsIgnoreCase(ptype)) {
                                        double d = Double.parseDouble(ptype);
                                        fp.append(d);
                                    } else if ("long".equalsIgnoreCase(ptype)) {
                                        long g = Long.parseLong(ptype);
                                        fp.append(g);
                                    }
                                }
                            }
                            sql.append(fp);
                            sql.append(GeneralComponets.RIGHTBRAC).append(GeneralComponets.NBSPACE).append(GeneralComponets.NBSPACE).append(orderByy.getDirecton()).append(GeneralComponets.COLON);
                        } else {
                            //原始分支
                            sql.append(orderByy.getColumn().getTableName().toLowerCase()).append(GeneralComponets.COMMA).append(orderByy.getColumn().getColumnName()).append(GeneralComponets.NBSPACE).append(orderByy.getDirecton()).append(GeneralComponets.COLON);
                        }
                    }
                }

                if (sql.toString().equals(GeneralComponets.ORDERBY)) {
                    return new StringBuffer();
                } else {
                    sql.deleteCharAt(sql.length() - 2);
                }
            }
            return sql;
        }


        public StringBuffer limit(StringBuffer sql) throws IlegalColumException {
            if (!(this.holder.getLimitamount() > 0)) {
                //支持无limit的情况
                //throw new IlegalColumException("limit is not found.");
                StringBuffer outter = new StringBuffer();
                outter.append("SELECT * FROM ( ");
                outter.append(sql);
                outter.append(" ) t ");
                return outter;
            } else {
                StringBuffer outter = new StringBuffer();
                outter.append("SELECT * FROM ( ");
                outter.append(sql);
                outter.append(" ) t WHERE ROWNUM < ");
                outter.append(this.holder.getLimitamount());
                return outter;
            }
        }

    }


}
