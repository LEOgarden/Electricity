package android.leo.electricity.view;

import android.leo.electricity.bean.ElectricPreMonth;

import java.util.List;

/**
 * Created by Leo on 2017/9/11.
 */

public interface IElectricUsedView {
    void showElectricUsed(List<ElectricPreMonth> electricPreMonths);
}
