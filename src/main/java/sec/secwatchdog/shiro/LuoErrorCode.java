package sec.secwatchdog.shiro;

public enum LuoErrorCode {
	
	NULL_OBJ("LUO001","����Ϊ��"),
	ERROR_ADD_USER("LUO002","����û�ʧ��"),
	LOGIN_VERIFY_FAILURE("LUO003","��¼��֤ʧ�ܣ������û��������Ƿ���ȷ"),
	UNKNOWN_ERROR("LUO999","ϵͳ��æ�����Ժ�����....");
	
	
    private String value;
    private String desc;

    private LuoErrorCode(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String toString() {
    	return "[" + this.value + "]" + this.desc;
    }
}
