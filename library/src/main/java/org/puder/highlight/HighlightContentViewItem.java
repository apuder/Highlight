package org.puder.highlight;

public class HighlightContentViewItem extends HighlightItem {

    private int contentViewId;

    public HighlightContentViewItem(int contentViewId) {
        this.contentViewId = contentViewId;
    }

    public int getContentViewId() {
        return contentViewId;
    }
}
