package com.hll_sc_app.bean.aptitude;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeInfoKV {
    private String inputKey;
    private String inputValue;

    public AptitudeInfoKV(String inputKey, String inputValue) {
        this.inputKey = inputKey;
        this.inputValue = inputValue;
    }

    public String getInputKey() {
        return inputKey;
    }

    public void setInputKey(String inputKey) {
        this.inputKey = inputKey;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }
}
