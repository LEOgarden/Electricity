package android.leo.electricity.view;

import android.leo.electricity.bean.Bill;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface IBillView {
    void showBillList(List<Bill> bills);
}
