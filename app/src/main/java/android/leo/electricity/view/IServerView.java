package android.leo.electricity.view;

import android.leo.electricity.bean.ServicePoint;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public interface IServerView {
    void showServerPointView(List<ServicePoint> servicePointList);
}
