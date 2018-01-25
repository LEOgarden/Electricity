package android.leo.electricity.view;

import android.leo.electricity.bean.Department;
import android.leo.electricity.bean.ServicePoint;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public interface IDepartmentView {
    void showDepartmentView(List<Department> departmentList);
}
