package cn.edu.qzu.ynhelper.entity;

/**
 * Created by Jaren on 2016/8/16.
 */
public class DataReturn {

    private int errCode;
    private String errMsg;
    private Object data;

    public DataReturn() {
    }

    public DataReturn(int errCode, String errMsg, Object data) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
