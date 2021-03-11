package alex.learn.common.stmt.beans;

import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/6/21
 */
public class HisMaster  implements Serializable {

    private String graphId;
    private String grapcode;
    private String datasetId;
    private String dashId;

    public HisMaster() {
    }

    public HisMaster(String graphId, String grapcode, String datasetId, String dashId) {
        this.graphId = graphId;
        this.grapcode = grapcode;
        this.datasetId = datasetId;
        this.dashId = dashId;
    }

    public String getGraphId() {
        return graphId;
    }

    public void setGraphId(String graphId) {
        this.graphId = graphId;
    }

    public String getGrapcode() {
        return grapcode;
    }

    public void setGrapcode(String grapcode) {
        this.grapcode = grapcode;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getDashId() {
        return dashId;
    }

    public void setDashId(String dashId) {
        this.dashId = dashId;
    }
}
