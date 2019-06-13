package com.hll_sc_app.bean.goods;

/**
 * 规格的 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class NicknamesBean {
    /**
     * 类别（1-商品名称，2-昵称）
     */
    private String nicknameType;
    /**
     * 商品别称
     */
    private String nickname;

    public String getNicknameType() {
        return nicknameType;
    }

    public void setNicknameType(String nicknameType) {
        this.nicknameType = nicknameType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
