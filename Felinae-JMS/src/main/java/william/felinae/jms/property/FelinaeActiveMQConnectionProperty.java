package william.felinae.jms.property;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/11/8 13:43
 * @Description:
 */
public class FelinaeActiveMQConnectionProperty {
    private String brokerURL;
    private String adminUsername;
    private String adminPassword;
    private long recoveryIntervalMillis;

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public long getRecoveryIntervalMillis() {
        return recoveryIntervalMillis;
    }

    public void setRecoveryIntervalMillis(long recoveryIntervalMillis) {
        this.recoveryIntervalMillis = recoveryIntervalMillis;
    }
}
