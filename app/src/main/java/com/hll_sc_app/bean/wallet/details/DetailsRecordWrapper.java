package com.hll_sc_app.bean.wallet.details;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public class DetailsRecordWrapper extends SectionEntity<DetailsRecord> {

    public DetailsRecordWrapper(DetailsRecord detailsRecord) {
        super(detailsRecord);
    }

    public DetailsRecordWrapper(boolean isHeader, String header) {
        super(isHeader, header);
    }
}
