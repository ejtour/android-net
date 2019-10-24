package com.hll_sc_app.widget.goodsdemand;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.NoCopySpan;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandDetailFooter extends LinearLayout {
    @BindView(R.id.ddf_label)
    TextView mLabel;
    @BindView(R.id.ddf_content)
    TextView mContent;

    public GoodsDemandDetailFooter(Context context) {
        this(context, null);
    }

    public GoodsDemandDetailFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsDemandDetailFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_goods_demand_detail_footer, this);
        ButterKnife.bind(this, view);
        setPadding(0, 0, 0, UIUtils.dip2px(5));
        mContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setData(GoodsDemandBean bean) {
        if (UserConfig.crm()) {
            mLabel.setText("联系方式");
            mContent.setText(PhoneUtil.formatPhoneNum(GreenDaoUtils.getUser().getLoginPhone()));
        } else {
            String source = String.format("%s / %s", bean.getCreateBy(),
                    PhoneUtil.formatPhoneNum(bean.getSupplyPhone()));
            mContent.setTag(bean.getSupplyPhone());
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new MyClickableSpan(), source.indexOf("/") + 2, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mContent.setText(ss);
        }
    }

    private static class MyClickableSpan extends ClickableSpan implements NoCopySpan {

        @Override
        public void onClick(@NonNull View widget) {
            if (widget.getTag() == null) return;
            UIUtils.callPhone(widget.getContext(), widget.getTag().toString());
        }
    }
}
