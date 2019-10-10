package com.hll_sc_app.app.rank;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/10
 */

public class RankHelper {
    public static int getMedal(int rank) {
        switch (rank) {
            case 1:
                return R.drawable.ic_medal_gold;
            case 2:
                return R.drawable.ic_medal_silver;
            case 3:
                return R.drawable.ic_medal_copper;
        }
        return 0;
    }
}
