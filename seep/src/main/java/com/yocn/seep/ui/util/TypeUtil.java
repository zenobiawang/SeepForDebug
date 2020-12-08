package com.yocn.seep.ui.util;

import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;

import com.yocn.seep.ui.bean.SeepResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * @author yocn
 */
public class TypeUtil {

    public static List<SeepResult> getChildSeepList(ViewGroup viewGroup, List<Fragment> fragmentList) {
        List<SeepResult> seepResultList = new ArrayList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            RectF childRectF = ViewUtil.getViewBounds(view);
            SeepResult childSeepResult = new SeepResult();
            Fragment fragment = null;
            if (TypeUtil.isRecyclerView(view)) {
                childSeepResult.setType(SeepResult.SeepType.TYPE_RECYCLERVIEW);
                childSeepResult.setName(view.getClass().getSimpleName());
                childSeepResult.setRectF(childRectF);
                childSeepResult.setWeakReference(new WeakReference<>(view));
            } else if ((fragment = TypeUtil.isFragment(view, fragmentList)) != null) {
                childSeepResult.setType(SeepResult.SeepType.TYPE_FRAGMENT);
                childSeepResult.setName(fragment.getClass().getSimpleName());
                childSeepResult.setRectF(childRectF);
                childSeepResult.setWeakReference(new WeakReference<>(fragment));
            } else {
                childSeepResult.setType(SeepResult.SeepType.TYPE_VIEW);
                childSeepResult.setName(view.getClass().getSimpleName());
                childSeepResult.setRectF(childRectF);
                childSeepResult.setWeakReference(new WeakReference<>(view));
            }
            SeepLogger.d("fragment::" + fragment);
            seepResultList.add(childSeepResult);
        }
        return seepResultList;
    }

    public static boolean isRecyclerView(View view) {
        return view.getClass().toString().contains("RecyclerView");
    }

    public static Fragment isFragment(View view, List<Fragment> fragmentList) {
        RectF targetRectF = ViewUtil.getViewBounds(view);
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            View fragmentView = fragment.getView();
            if (fragmentView == null) {
                return null;
            }
            RectF fragmentRectF = ViewUtil.getViewBounds(fragmentView);
            if (inRectF(targetRectF, fragmentRectF)) {
                return fragment;
            }
        }
        return null;
    }

    /**
     * 四个角或中心点在目标内
     *
     * @param src 目标
     * @param tar 对象
     * @return 是否在范围内
     */
    private static boolean inRectF(RectF src, RectF tar) {
        boolean leftTop = pointInRectF(src.left, src.top, tar);
        boolean rightTop = pointInRectF(src.right, src.top, tar);
        boolean leftBottom = pointInRectF(src.left, src.bottom, tar);
        boolean rightBottom = pointInRectF(src.right, src.bottom, tar);
        boolean center = pointInRectF(src.left + (src.right - src.left) / 2, src.bottom + (src.top - src.bottom) / 2, tar);
        return leftTop | rightTop | leftBottom | rightBottom | center;
    }

    private static boolean pointInRectF(float x, float y, RectF target) {
        return x > target.left && x < target.right && y > target.top && y < target.bottom;
    }
}
