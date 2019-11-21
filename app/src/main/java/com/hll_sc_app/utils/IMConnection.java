package com.hll_sc_app.utils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.citymall.util.LogUtil;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MucEnterConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 获取XMPP连接类
 *
 * @author zhuyingsong
 * @date 2019/3/21
 */
public class IMConnection {
    private static final String DOMAIN = "im.22city.cn";
    private static String employeeID;
    private static XMPPTCPConnection connection;
    private ConnectionListener connectionListener;

    private IMConnection() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            employeeID = userBean.getEmployeeID();
            openConnection();
        }
    }

    public synchronized static IMConnection getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * 登录
     *
     * @param account  登录帐号
     * @param password 登录密码
     * @return true登录成功
     */
    public boolean login(String account, String password) {
        try {
            if (getConnection() == null) {
                return false;
            }
            getConnection().login(account, password);
            LogUtil.d("ZYS", "login");
            return true;
        } catch (Exception xe) {
            xe.printStackTrace();
        }
        return false;
    }

    /**
     * 加入会议室
     *
     * @param user      员工ID
     * @param roomsName 会议室名
     */
    public MultiUserChat joinMultiUserChat(String user, String roomsName, MessageListener listener) {
        if (getConnection() == null) {
            return null;
        }
        try {
            // 使用XMPPConnection创建一个MultiUserChat窗口
            MultiUserChat muc = MultiUserChatManager.getInstanceFor(getConnection()).getMultiUserChat(
                    JidCreate.entityBareFrom(roomsName + "@conference." + DOMAIN + "/" + user));
            MucEnterConfiguration.Builder builder = muc.getEnterConfigurationBuilder(Resourcepart.from(employeeID));
            builder.requestMaxStanzasHistory(99);
            muc.join(builder.build());
            muc.addMessageListener(listener);
            return muc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建连接
     */
    private XMPPTCPConnection getConnection() {
        if (connection == null) {
            // 开线程打开连接，避免在主线程里面执行HTTP请求
            synchronized (this) {
                if (connection == null) {
                    openConnection();
                }
            }
        }
        return connection;
    }

    /**
     * 打开连接
     */
    private void openConnection() {
        if (null == connection || !connection.isAuthenticated()) {
            try {
                InetAddress address = InetAddress.getByName(HttpConfig.getIMHost());
                XMPPTCPConnectionConfiguration configuration;
                configuration = XMPPTCPConnectionConfiguration
                        .builder()
                        .setHostAddress(address)
                        .setXmppDomain(DOMAIN)
                        .setUsernameAndPassword(employeeID, employeeID)
                        .setSendPresence(true)
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        .setCompressionEnabled(true)
                        .build();
                // 配置授权信息
                SASLAuthentication.registerSASLMechanism(new SASLPlainMechanism());
                SASLMechanism mechanism = new SASLDigestMD5Mechanism();
                SASLAuthentication.registerSASLMechanism(mechanism);
                SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
                SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");
                connection = new XMPPTCPConnection(configuration);
                connectionListener = new ConnectionListener() {
                    @Override
                    public void connected(XMPPConnection connection) {
                        LogUtil.d("ZYS", "connected");
                        login(employeeID, employeeID);
                    }

                    @Override
                    public void authenticated(XMPPConnection connection, boolean resumed) {
                    }

                    @Override
                    public void connectionClosed() {
                    }

                    @Override
                    public void connectionClosedOnError(Exception e) {
                    }
                };
                connection.addConnectionListener(connectionListener);
                connection.connect();
            } catch (XmppStringprepException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     */
    public void closeConnection() {
        if (connection != null) {
            // 移除连接监听
            connection.removeConnectionListener(connectionListener);
            if (connection.isConnected()) {
                connection.disconnect();
            } else {
                connection.instantShutdown();
            }
            connection = null;
        }
    }

    private static class Instance {
        static final IMConnection INSTANCE = new IMConnection();
    }
}
