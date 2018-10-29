package view.enums;

public enum Node {
    ONE(1,"一个拐点"),
    TWO(2,"两个拐点")
    ;
    private Integer code;
    private String msg;

    Node(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
