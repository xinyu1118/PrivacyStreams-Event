package io.github.privacystreamsevents.accessibility;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import io.github.privacystreamsevents.commons.comparison.Comparators;
import io.github.privacystreamsevents.commons.item.ItemOperators;
import io.github.privacystreamsevents.core.Callback;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamProvider;
import io.github.privacystreamsevents.core.purposes.Purpose;
import io.github.privacystreamsevents.utils.AccessibilityUtils;
import io.github.privacystreamsevents.utils.AppUtils;

import java.util.List;

/**
 * Provide a live stream of browser visit events.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
class BrowserVisitEventsProvider extends PStreamProvider {
    private static String lastSavedUrl = null;
    private static String lastSavedUrlTitle = null;

    @Override
    protected void provide() {
        getUQI().getData(AccEvent.asWindowChanges(), Purpose.LIB_INTERNAL("Event Triggers"))
                .filter(ItemOperators.isFieldIn(AccEvent.PACKAGE_NAME, new String[]{AppUtils.APP_PACKAGE_FIREFOX, AppUtils.APP_PACKAGE_OPERA, AppUtils.APP_PACKAGE_CHROME}))
                .filter(Comparators.eq(AccEvent.EVENT_TYPE, AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        AccessibilityNodeInfo rootNode = input.getValueByField(AccEvent.ROOT_NODE);
                        List<AccessibilityNodeInfo> nodeInfos = AccessibilityUtils.preOrderTraverse(rootNode);
                        String packageName = input.getValueByField(AccEvent.PACKAGE_NAME);
                        String url = AccessibilityUtils.getBrowserCurrentUrl(rootNode, packageName);
                        String title = AccessibilityUtils.getWebViewTitle(nodeInfos);
                        if(url!=null && title !=null
                                && !url.equals(lastSavedUrl)
                                && !title.equals(lastSavedUrlTitle)){
                            lastSavedUrl = url;
                            lastSavedUrlTitle = title;
                            output(new BrowserVisit(title, packageName, url));
                        }

                    }
                });
    }

}