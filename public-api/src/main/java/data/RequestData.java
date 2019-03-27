package data;

import java.io.Serializable;

/**
 * 请求数据包 存放参数
 * 因为要放在网络中传输，所以要序列化
 */
public class RequestData implements Serializable {

    private String interfaceName;//接口名称

    private String methodName;//方法名称

    private Class<?>[] parameterTypes;//参数类型列表

    private Object[] parameter;//参数对应的值列表

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }
}
