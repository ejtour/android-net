package com.hll_sc_app.app.goodsdemand;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandHelper {
    public static String getType(int type) {
        switch (type) {
            case 1:
                return "商品外形";
            case 2:
                return "储存要求";
            case 3:
                return "分拣要求";
            case 4:
                return "配送要求";
            case 5:
                return "其他要求";
            default:
                return "";
        }
    }

    public static String getStatus(int status) {
        switch (status) {
            case 1:
                return "待回复";
            case 2:
                return "已回复";
            case 3:
                return "已上架";
            default:
                return "已取消";
        }
    }

    public static int getStatusColor(int status){
        switch (status) {
            case 1:
                return R.color.color_f6bb42;
            case 2:
                return R.color.color_7ed321;
            case 3:
                return R.color.colorPrimary;
            default:
                return R.color.color_999999;
        }
    }

    public static int getIcon(int status) {
        switch (status) {
            case 1:
                return R.drawable.ic_warn;
            case 2:
                return R.drawable.ic_yellow_ok;
            case 3:
                return R.drawable.ic_blue_ok;
            default:
                return R.drawable.ic_ban;
        }
    }

    public static int getColor(int type) {
        switch (type) {
            case 1:
                return 0xff379FF1;
            case 2:
                return 0xff73D13D;
            case 3:
                return 0xffFFA940;
            case 4:
                return 0xff5CDBD3;
            case 5:
                return 0xffFFA39E;
            default:
                return 0;
        }
    }
}
